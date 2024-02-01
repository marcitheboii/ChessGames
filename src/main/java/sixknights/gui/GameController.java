package sixknights.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.tinylog.Logger;
import sixknights.state.State;
import sixknights.state.GameState;
import startApp.Position;
import startApp.Stopwatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    @FXML
    private GridPane grid;
    private GameState state = new GameState();
    private Position selected;
    private Node[][] board;
    private final List<Node> validNodes = new ArrayList<>();

    private static final int nodeHeight = 110;
    private static final int nodeWidth = 110;

    private Stopwatch stopwatch = new Stopwatch();
    @FXML
    private Label feedBackLabel;
    @FXML
    private Label stopWatch;

    /**
     *  Fxml által meghívásra kerülő kezdő függvény.
     */
    @FXML
    private javafx.scene.control.Label stepLabel;

    private final IntegerProperty steps = new SimpleIntegerProperty();

    @FXML
    private void initialize(){
        stepLabel.textProperty().bind(steps.asString());
        updateTimer();
        state = new GameState();
        printBoard();
    }

    private void updateTimer() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            if (stopwatch.isRunning()) {
                updateElapsedTimeLabel();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void setFeedBackLabel(String text){
        feedBackLabel.setText(text);
    }

    private void updateElapsedTimeLabel() {
        Platform.runLater(() -> stopWatch.setText(String.format(stopwatch.getElapsedTimeFormatted())));
    }

    public void startTimer(){
        stopwatch.start();
    }

    public void openHelp(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sixKnights/help.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void resetGame(){
        steps.set(0);
        stopwatch.reset();
        updateElapsedTimeLabel();
        grid.setDisable(false);
        initialize();
    }

    /**
     * A játéktáblát benépesítő függvény, újrarajzolja az eltárolt állapot alapján a huszárok helyét.
     */
    private void printBoard(){
        isGameOver();
        grid.getChildren().clear();

        setFeedBackLabel("Next player: "+state.nextPlayer);

        board = new Node[grid.getRowCount()][grid.getColumnCount()];

        for (var row = 0; row < grid.getRowCount(); row++) {
            for (var col = 0; col < grid.getColumnCount(); col++) {
                var square = new StackPane();
                square.getStyleClass().add("square");
                square.getStyleClass().add((row + col) % 2 == 0 ? "light": "dark");
                grid.add(square, col, row);
                board[row][col] = square;

                if(state.board[row][col] == State.WHITE) {
                    addWhiteKnight(row,col);
                } else if (state.board[row][col] == State.BLACK) {
                    addBlackKnight(row,col);
                }
            }
        }
        Logger.info("Board printed");
    }

    private void addWhiteKnight(int row, int col){
        Node whiteBishopNode = board[row][col];
        ImageView white_k = new ImageView("/sixKnights/images/whiteKnight.png");
        white_k.setFitHeight(nodeHeight);
        white_k.setFitWidth(nodeWidth);
        if(state.nextPlayer == State.WHITE) {
            whiteBishopNode.getStyleClass().add("nextPlayer");
            white_k.setOnMouseClicked(this::selectClick);
            whiteBishopNode.setOnMouseClicked(this::selectClick);
        }
        grid.add(white_k,col,row);
        board[row][col] = white_k;
    }

    private void addBlackKnight(int row, int col){
        Node blackBishopNode = board[row][col];
        ImageView black_k = new ImageView("/sixKnights/images/blackKnight.png");
        black_k.setFitHeight(nodeHeight);
        black_k.setFitWidth(nodeWidth);
        if(state.nextPlayer == State.BLACK) {
            blackBishopNode.getStyleClass().add("nextPlayer");
            black_k.setOnMouseClicked(this::selectClick);
            blackBishopNode.setOnMouseClicked(this::selectClick);
        }
        grid.add(black_k,col,row);
        board[row][col] = black_k;
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

        stopwatch.start();
        steps.set(steps.get()+1);
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
            stopwatch.stop();
            setFeedBackLabel("YOU LOST! No more moves for: " +state.nextPlayer+ " !");
            Logger.trace("GAME OVER!\n No more moves for: " +state.nextPlayer+" !");
            grid.setDisable(true);
            return true;
        }

        if (state.goalTest()){
            setFeedBackLabel("CONGRATULATIONS! You beat the game! ");
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
