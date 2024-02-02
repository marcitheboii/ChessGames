package maximalistKnight.gui;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import maximalistKnight.state.GameState;
import maximalistKnight.state.State;
import org.json.JSONArray;
import org.json.JSONObject;
import org.tinylog.Logger;
import startApp.Position;
import startApp.Stopwatch;

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

    private Node[][] board;

    @FXML
    private javafx.scene.control.Label stopWatch;

    private final Stopwatch stopwatch = new Stopwatch();

    @FXML
    private javafx.scene.control.Label feedBackLabel;

    @FXML
    private javafx.scene.control.Label stepLabel;

    private final IntegerProperty steps = new SimpleIntegerProperty();

    private boolean solved;

    @FXML
    private void initialize(){
        solved = false;
        stepLabel.textProperty().bind(steps.asString());
        setFeedBackLabel("Start by moving the knight or by starting the timer!!");
        updateTimer();
        state = new GameState();
        printBoard();
    }
    public void saveData() {
        try {

            File myObj = new File("src/main/resources/maximalistKnight/scoreboard.json");

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
                scanner.close();
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

    public void openScoreboard(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/maximalistKnight/scoreboard.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void openHelp(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/maximalistKnight/help.fxml"));
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

    private void printBoard(){
        grid.getChildren().clear();

        board = new Node[grid.getRowCount()][grid.getColumnCount()];

        colorChessBoard();
        markHasBeenFields();
        styleLegalFields();
        drawKnight();


        Logger.info("Board printed");
    }

    private void markHasBeenFields(){
        for (var row = 0; row < grid.getRowCount(); row++) {
            for (var col = 0; col < grid.getColumnCount(); col++) {
                if(state.board[row][col] == State.HAS_BEEN){
                    ImageView has_been = new ImageView("/maximalistKnight/images/has_been.png");
                    has_been.setFitHeight(responsiveSize("height")-30);
                    has_been.setFitWidth(responsiveSize("width")-30);
                    grid.add(has_been, col, row);
                    board[row][col] = has_been;
                }
            }
        }
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

    private void drawKnight(){
        Position knightPos = state.getKnightsPos();

        ImageView knight = new ImageView("/maximalistKnight/images/blackKnight.png");
        knight.setFitHeight(responsiveSize("height")-30);
        knight.setFitWidth(responsiveSize("size")-30);
        grid.add(knight,knightPos.getCol(),knightPos.getRow());
        board[knightPos.getRow()][knightPos.getCol()] = knight;
    }

    private void styleLegalFields(){

        for (var row = 0; row < grid.getRowCount(); row++) {
            for (var col = 0; col < grid.getColumnCount(); col++) {
                if(state.board[row][col] == State.CAN_GO){
                    var square = new StackPane();
                    square.getStyleClass().add("legal");
                    square.setOnMouseClicked(this::moveKnight);
                    grid.add(square, col, row);
                    board[row][col] = square;
                }
            }
        }
    }

    private void moveKnight(javafx.scene.input.MouseEvent event){
        var source = (Node)event.getSource();
        var row = GridPane.getRowIndex(source);
        var col = GridPane.getColumnIndex(source);
        state.moveKnight(new Position(row,col));

        stopwatch.start();
        steps.set(steps.get()+1);
        setFeedBackLabel("Keep going you got this!");

        isGameOver();
        printBoard();
    }

    private void isGameOver(){
        switch(state.isOver()){
            case 0:
                break;
            case 1:
                grid.setDisable(true);
                stopwatch.stop();
                setFeedBackLabel("YOU LOST! You have ran out of valid moves!");
                Logger.error("GAME OVER, YOU LOST");
                saveData();
                break;
            case 2:
                solved = true;
                stopwatch.stop();
                grid.setDisable(true);
                setFeedBackLabel("CONGRATULATIONS! You beat the game!");
                Logger.error("GAME OVER, YOU WIN");
                saveData();
                break;
        }
    }

    public void backToMenu(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/landingPage/ui.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private int responsiveSize(String whatToCalc){

        int defVal = 100;

        if(whatToCalc.equals("width")){
            if(grid.getWidth() == 0){
                return defVal;
            }
            return (int)grid.getWidth()/grid.getRowCount();
        }

        if (whatToCalc.equals("height")) {
            if(grid.getHeight() == 0){
                return defVal;
            }
            return (int)grid.getHeight()/grid.getColumnCount();
        }

        return defVal;
    }

}
