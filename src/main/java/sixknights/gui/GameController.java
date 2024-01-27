package sixknights.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.tinylog.Logger;
import sixknights.state.sixKnightsGameState;
import startApp.Position;
import sixknights.state.State;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    @FXML
    private GridPane grid;
    @FXML
    private Label topLabel;
    private final sixKnightsGameState state = new sixKnightsGameState();
    private Position selected;
    private Node[][] board;
    private final List<Node> validNodes = new ArrayList<>();

    /**
     *  Fxml által meghívásra kerülő kezdő függvény.
     */
    @FXML
    private void initialize(){
        printBoard();
    }

    /**
     * A játéktáblát benépesítő függvény, újrarajzolja az eltárolt állapot alapján a huszárok helyét.
     */
    private void printBoard(){

        board = new Node[grid.getRowCount()][grid.getColumnCount()];

        for (var row = 0; row < grid.getRowCount(); row++) {
            for (var col = 0; col < grid.getColumnCount(); col++) {
                if(state.board[row][col] == State.WHITE) {
                    ImageView white_k = new ImageView("/sixKnights/images/white_knight.png");
                    white_k.setFitHeight(120);
                    white_k.setFitWidth(120);
                    if(state.nextPlayer == State.WHITE) {
                        white_k.setOnMouseClicked(this::selectClick);
                    }
                    grid.add(white_k,col,row);
                    board[row][col] = white_k;
                } else if (state.board[row][col] == State.BLACK) {
                    ImageView black_k = new ImageView("/sixKnights/images/black_knight.png");
                    black_k.setFitHeight(120);
                    black_k.setFitWidth(120);
                    if(state.nextPlayer == State.BLACK) {
                        black_k.setOnMouseClicked(this::selectClick);
                    }
                    grid.add(black_k,col,row);
                    board[row][col] = black_k;
                }else {
                    var square = new StackPane();
                    square.getStyleClass().add("square");
                    square.getStyleClass().add((row + col) % 2 == 0 ? "light": "dark");
                    grid.add(square, col, row);
                    board[row][col] = square;
                }
            }
        }
        Logger.info("Board printed");
        labelHandler();
    }

    /**
     *  Első kattintás amivel kijelölönk egy huszárt amivel lépni szeretnénk.
     * @param event egér kattintást kapja el
     */
    private void selectClick(MouseEvent event){
        var source = (Node)event.getSource();
        var row = GridPane.getRowIndex(source);
        var col = GridPane.getColumnIndex(source);
        selected = new Position(row,col);
        Logger.trace("Selected Knight (" + row + ", " + col + ")");
        ArrayList<Position> legitMoves=state.legalMovesArray(state.board,row,col,state.nextPlayer);
        clearValidNodes();
        for (var pos : legitMoves){
            Node validNode = board[pos.getRow()][pos.getCol()];
            validNodes.add(validNode);
            validNode.getStyleClass().add("legal");
            validNode.setOnMouseClicked(this::moveOnClick);
        }
    }

    /**
     * Második kattintás amivel már lépünk a megengedett mezőre.
     * @param event egér kattintást kapja el
     */
    private void moveOnClick(MouseEvent event) {
        clearValidNodes();
        var source = (Node) event.getSource();
        var row = GridPane.getRowIndex(source);
        var col = GridPane.getColumnIndex(source);
        Logger.trace("Move to (" + row + "," + col + ")");
        state.movePiece(selected,new Position(row,col));
        printBoard();
    }

    /**
     * Léphető lépések listájának kiürítése.
     */
    private void clearValidNodes() {
        validNodes.forEach(node -> {
            node.getStyleClass().remove("legal");
            node.setOnMouseClicked(event1 -> {});
        });
    }

    /**
     *  Egy kiválaszott szín után megadja az azonos színű huszárok pozícióját.
     * @param color választott szín white/black
     * @return Listaként adja vissza a pozíciókat
     */
    private ArrayList<Position> getPositions(State color){

        ArrayList<Position> list = new ArrayList<>();

        for (var row = 0; row < grid.getRowCount(); row++) {
            for (var col = 0; col < grid.getColumnCount(); col++) {
                if(state.board[row][col] == color)
                    list.add(new Position(row,col));
            }
        }
        return list;
    }

    /**
     * Játéktábla feletti üzeneteket kezelő föggvény.
     */
    private void labelHandler(){
        if(isGameOver()){
            Platform.runLater(() -> topLabel.setText(String.format("GAME OVER!\n No more moves for : " + state.nextPlayer + " !")));
        }else {
            if(state.goalTest()){
                Platform.runLater(() -> topLabel.setText("Congratulations!!"));
                Logger.trace("Goal state reached!");
            }else {
                Platform.runLater(() -> topLabel.setText(String.format("Next side : " + state.nextPlayer)));
                Logger.trace("Next side: " + state.nextPlayer);
            }

        }
    }

    /**
     * Lépések hiánya miatt véget érő játékállapotok tesztelése.
     * @return Igaz ha nincs több lépése az adott oldalnak
     */
    private boolean isGameOver() {
        int counter = 0;

        if (state.nextPlayer == State.WHITE) {
            for (var pos : getPositions(State.WHITE)) {
                if (state.legalMovesArray(state.board, pos.getRow(), pos.getCol(), State.WHITE).isEmpty())
                    counter++;
            }
        } else if (state.nextPlayer == State.BLACK) {
            for (var pos : getPositions(State.BLACK)) {
                if (state.legalMovesArray(state.board, pos.getRow(), pos.getCol(), State.BLACK).isEmpty())
                    counter++;
            }
        }
        if(counter == 3){
            Logger.trace("GAME OVER!\n No more moves for: " +state.nextPlayer+" !");
            return true;
        }
        return false;
    }
}
