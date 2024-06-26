package games.eightBishops.gui;

import games.eightBishops.state.GameState;
import games.eightBishops.state.State;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.tinylog.Logger;
import settings.SettingsModel;
import position.Position;
import stopwatch.Stopwatch;

import java.util.ArrayList;
import java.util.List;

public class GameController extends games.GameController {


    @FXML
    private GridPane grid;

    private GameState state = new GameState();

    private Node[][] board;

    private Position selected;
    private final List<Node> validNodes = new ArrayList<>();

    private Stopwatch stopwatch = new Stopwatch();
    @FXML
    private Label feedBackLabel;

    @FXML
    private Label stopWatch;

    @FXML
    private javafx.scene.control.Label stepLabel;

    private boolean solved;

    private final IntegerProperty steps = new SimpleIntegerProperty();

    private SettingsModel settings = new SettingsModel();

    @FXML
    private void initialize(){
        solved = false;
        setFeedBackLabel("Start by moving a bishop or by starting the timer!!");
        stepLabel.textProperty().bind(steps.asString());
        updateTimer();
        state = new GameState();
        printBoard();
    }

    public void saveData() {
        state.saveData("eightBishops",stopwatch.getElapsedTimeFormatted(),steps.getValue(),solved);
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

    public void openScoreboard(MouseEvent event) {
        openThis("/eightBishops/scoreboard.fxml",event);
    }

    public void openHelp(MouseEvent event) {
        openThis("/eightBishops/help.fxml",event);
    }

    public void resetGame(){
        steps.set(0);
        stopwatch.reset();
        updateElapsedTimeLabel();
        grid.setDisable(false);
        initialize();
    }

    private void printBoard(){
        grid.getChildren().clear();
        board = new Node[grid.getRowCount()][grid.getColumnCount()];

        setFeedBackLabel("Next player: "+ state.nextPlayer);

        colorChessBoard();

        for (var row = 0; row < grid.getRowCount(); row++) {
            for (var col = 0; col < grid.getColumnCount(); col++) {
                if(state.board[row][col] == State.WHITE) {
                    addWhiteBishop(row,col);
                } else if (state.board[row][col] == State.BLACK) {
                    addBlackBishop(row,col);
                }
            }
        }

    }

    private void addWhiteBishop(int row,int col){
        Node whiteBishopNode = board[row][col];
        ImageView whiteBishop = new ImageView("/images/"+settings.getWhiteBishop());
        whiteBishop.setFitHeight(100);
        whiteBishop.setFitWidth(100);
        if(state.nextPlayer == State.WHITE) {
            whiteBishopNode.getStyleClass().add("nextPlayer");
            whiteBishop.setOnMouseClicked(this::selectClick);
            whiteBishopNode.setOnMouseClicked(this::selectClick);
        }
        grid.add(whiteBishop,col,row);
        board[row][col] = whiteBishop;
    }

    private void addBlackBishop(int row, int col){
        Node blackBishopNode = board[row][col];
        ImageView blackBishop = new ImageView("/images/"+settings.getDarkBishop());
        blackBishop.setFitHeight(100);
        blackBishop.setFitWidth(100);
        if(state.nextPlayer == State.BLACK) {
            blackBishopNode.getStyleClass().add("nextPlayer");
            blackBishop.setOnMouseClicked(this::selectClick);
            blackBishopNode.setOnMouseClicked(this::selectClick);
        }
        grid.add(blackBishop,col,row);
        board[row][col] = blackBishop;
    }

    private void moveOnClick(javafx.scene.input.MouseEvent event){
        clearValidNodes();
        var source = (Node) event.getSource();
        var row = GridPane.getRowIndex(source);
        var col = GridPane.getColumnIndex(source);
        state.moveBishop(selected,new Position(row,col));

        stopwatch.start();
        steps.set(steps.get() + 1 );


        printBoard();
        isGameover();
    }

    private void selectClick(javafx.scene.input.MouseEvent event){
        var source = (Node)event.getSource();
        var row = GridPane.getRowIndex(source);
        var col = GridPane.getColumnIndex(source);
        selected = new Position(row,col);
        ArrayList<Position> legitMoves=state.setStepsForBishop(selected);
        clearValidNodes();
        for (var pos : legitMoves){
            Node validNode = board[pos.getRow()][pos.getCol()];
            validNodes.add(validNode);
            validNode.setStyle("-fx-background-color: "+toCssColor(settings.getLegalColor())+";");
            validNode.setOnMouseClicked(this::moveOnClick);
        }
    }

    private void clearValidNodes() {
        validNodes.forEach(node -> {
            node.setStyle("");
            node.setOnMouseClicked(event1 -> {});
        });
    }

    private void colorChessBoard(){
        for (var row = 0; row < grid.getRowCount(); row++) {
            for (var col = 0; col < grid.getColumnCount(); col++) {
                var square = new StackPane();
                square.getStyleClass().add("square");
                square.getStyleClass().add((row + col) % 2 == 0 ? "light" : "dark");
                grid.add(square, col, row);
                board[row][col] = square;
            }
        }
    }

    private boolean isGameover() {
        int counter = 0;

        if(state.goalTest()){
            solved = true;
            stopwatch.stop();
            setFeedBackLabel("CONGRATULATIONS! You beat the game!");
            Logger.trace("CONGRATULATIONS!!");
            saveData();
            return true;
        }

        if (state.nextPlayer == State.WHITE) {
            for (var pos : state.getWhiteBishopsPos()) {
                if (state.setStepsForBishop(pos).isEmpty())
                    counter++;
            }
        } else if (state.nextPlayer == State.BLACK) {
            for (var pos : state.getBlackBishopsPos()) {
                if (state.setStepsForBishop(pos).isEmpty())
                    counter++;
            }
        }
        if(counter == 4){
            grid.setDisable(true);
            stopwatch.stop();
            setFeedBackLabel("No more moves for: " +state.nextPlayer + " !");
            Logger.trace("GAME OVER!\n No more moves for: " +state.nextPlayer+" !");
            saveData();
            return true;
        }
        return false;
    }
    public void backToMenu(MouseEvent event) {
        openThis("/landingPage/ui.fxml",event);
    }
}
