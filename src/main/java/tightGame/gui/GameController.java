package tightGame.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.tinylog.Logger;
import startApp.Position;
import tightGame.state.GameState;
import tightGame.state.State;

import java.io.IOException;

public class GameController {
    @FXML
    private GridPane grid;

    private GameState state = new GameState();

    private Position selected;

    @FXML
    private void initialize(){
        state = new GameState();
        printBoard();
    }

    private Node lastSelected;
    private Node lastValid;

    public void resetGame(){
        grid.setDisable(false);
        initialize();
    }

    private void printBoard(){
        grid.getChildren().clear();

        colorChessBoard();

        for (var row = 0; row < grid.getRowCount(); row++) {
            for (var col = 0; col < grid.getColumnCount(); col++) {
                if(state.board[row][col] == State.BISHOP) {
                    addBishop(row,col);
                    continue;
                }
                if (state.board[row][col] == State.KING) {
                    addKing(row,col);
                    continue;
                }
                if (state.board[row][col] == State.ROOK) {
                    addRook(row,col);
                }
            }
        }

    }

    private void addBishop(int row,int col){

        ImageView bishop = new ImageView("/tightGame/images/bishop.png");
        bishop.setFitHeight(100);
        bishop.setFitWidth(100);
        grid.add(bishop,col,row);

        Node bishopNode = new StackPane();
        bishopNode.setOnMouseClicked(this::selectClick);
        grid.add(bishopNode,col,row);
    }

    private void addRook(int row, int col){

        ImageView rook = new ImageView("/tightGame/images/rook.png");
        rook.setFitHeight(100);
        rook.setFitWidth(100);
        grid.add(rook,col,row);

        Node rookNode = new StackPane();
        rookNode.setOnMouseClicked(this::selectClick);
        grid.add(rookNode,col,row);
    }

    private void addKing(int row, int col){

        ImageView king = new ImageView("/tightGame/images/king.png");
        king.setFitHeight(100);
        king.setFitWidth(100);
        grid.add(king,col,row);


        Node kingNode = new StackPane();
        kingNode.setOnMouseClicked(this::selectClick);
        grid.add(kingNode,col,row);
    }

    private void moveOnClick(javafx.scene.input.MouseEvent event){
        var source = (Node) event.getSource();
        var row = GridPane.getRowIndex(source);
        var col = GridPane.getColumnIndex(source);

        state.movePiece(selected,new Position(row,col));

        isGameOver();
        printBoard();
    }

    private void selectClick(javafx.scene.input.MouseEvent event){

        var source = (Node)event.getSource();
        var row = GridPane.getRowIndex(source);
        var col = GridPane.getColumnIndex(source);
        selected = new Position(row,col);

        manageSelectedPlayer();

        Position legitMove = state.showMovesForSelected(selected);
        if (legitMove == null){
            grid.getChildren().remove(lastValid);
            return;
        }
        setValidNode(legitMove);
    }

    private void manageSelectedPlayer(){
        grid.getChildren().remove(lastSelected);
        Node next = new StackPane();
        next.getStyleClass().add("nextPlayer");
        lastSelected = next;
        grid.add(next,selected.getCol(),selected.getRow());
    }

    private void setValidNode(Position legitMove){
        grid.getChildren().remove(lastValid);
        Node valid = new StackPane();
        valid.getStyleClass().add("legal");
        valid.setOnMouseClicked(this::moveOnClick);
        lastValid = valid;
        grid.add(valid,legitMove.getCol(),legitMove.getRow());

    }

    private void colorChessBoard(){
        for (var row = 0; row < grid.getRowCount(); row++) {
            for (var col = 0; col < grid.getColumnCount(); col++) {
                var square = new StackPane();
                square.getStyleClass().add("square");
                square.getStyleClass().add((row + col) % 2 == 0 ? "light" : "dark");
                grid.add(square, col, row);
            }
        }
    }

    private boolean isGameOver() {

        if(state.isOver()){
            grid.setDisable(true);
            Logger.trace("CONGRATULATIONS!!");
            return true;
        }
        return false;
    }
    public void backToMenu(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/landingPage/ui.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
