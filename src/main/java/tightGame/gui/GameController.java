package tightGame.gui;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;
import org.tinylog.Logger;
import startApp.Position;
import startApp.Stopwatch;
import tightGame.state.GameState;
import tightGame.state.State;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GameController {
    @FXML
    private GridPane grid;

    private GameState state = new GameState();

    private Position selected;
    private Stopwatch stopwatch = new Stopwatch();

    @FXML
    private Label feedBackLabel;

    @FXML
    private Label stopWatch;

    @FXML
    private javafx.scene.control.Label stepLabel;

    private final IntegerProperty steps = new SimpleIntegerProperty();

    private boolean solved;

    @FXML
    private void initialize(){
        solved = false;
        setFeedBackLabel("Start by moving a piece or by starting the timer!!");
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
    public void saveData() {
        try {

            File myObj = new File("src/main/resources/tightGame/scoreboard.json");

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            if (myObj.exists()) {
                Scanner scanner = new Scanner(myObj);
                JSONArray scoreboard = new JSONArray(scanner.nextLine());

                JSONObject newScore = new JSONObject();
                newScore.put("Time", stopwatch.getElapsedTimeFormatted());
                newScore.put("Steps", steps.getValue());
                newScore.put("Date", formattedDateTime);
                newScore.put("Solved", solved);

                scoreboard.put(newScore);

                FileWriter fileWriter = new FileWriter(myObj);
                fileWriter.write(scoreboard.toString());
                fileWriter.close();
            } else {
                FileWriter fileWriter = new FileWriter(myObj);
                JSONArray scoreBoard = new JSONArray();

                JSONObject newScore = new JSONObject();
                newScore.put("Time", stopwatch.getElapsedTimeFormatted());
                newScore.put("Steps", steps.getValue());
                newScore.put("Date", formattedDateTime);
                newScore.put("Solved", solved);

                scoreBoard.put(newScore);

                fileWriter.write(scoreBoard.toString());
                fileWriter.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void setFeedBackLabel(String text){
        feedBackLabel.setText(text);
    }

    private void updateElapsedTimeLabel() {
        Platform.runLater(() -> stopWatch.setText(String.format(stopwatch.getElapsedTimeFormatted())));
    }

    public void openScoreboard(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tightGame/scoreboard.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void openHelp(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tightGame/help.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private Node lastSelected;
    private Node lastValid;

    public void resetGame(){
        steps.set(0);
        stopwatch.reset();
        updateElapsedTimeLabel();
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

        stopwatch.start();
        steps.set(steps.get()+1);
        setFeedBackLabel("Keep going, you got this!");

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
            solved = true;
            grid.setDisable(true);
            stopwatch.stop();
            setFeedBackLabel("CONGRATULATIONS! You beat the game!");
            Logger.trace("CONGRATULATIONS!!");
            saveData();
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
