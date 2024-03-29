package lonelyKnight.gui;

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
import lonelyKnight.state.GameState;
import lonelyKnight.state.State;
import startApp.Position;
import settings.SettingsModel;
import startApp.Stopwatch;

import java.io.IOException;
import java.util.ArrayList;

public class GameController {

    @FXML
    private GridPane grid;

    private GameState state = new GameState();

    @FXML
    private javafx.scene.control.Label stopWatch;

    private final Stopwatch stopwatch = new Stopwatch();

    @FXML
    private javafx.scene.control.Label feedBackLabel;

    @FXML
    private javafx.scene.control.Label stepLabel;

    private final IntegerProperty steps = new SimpleIntegerProperty();

    private Boolean solved;

    private final SettingsModel settings = new SettingsModel();



    @FXML
    private void initialize() {
        solved = false;
        stepLabel.textProperty().bind(steps.asString());
        updateTimer();
        setFeedBackLabel("Start by moving the knight or by starting the timer!!");
        state = new GameState();
        printBoard();
    }

    public void saveData() {
        state.saveData("lonelyKnight",stopwatch.getElapsedTimeFormatted(),steps.getValue(),solved);
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

    private void setFeedBackLabel(String text) {
        feedBackLabel.setText(text);
    }

    private void updateElapsedTimeLabel() {
        Platform.runLater(() -> stopWatch.setText(String.format(stopwatch.getElapsedTimeFormatted())));
    }

    public void resetGame() {
        steps.set(0);
        grid.setDisable(false);
        stopwatch.reset();
        updateElapsedTimeLabel();
        setFeedBackLabel("Board back to original state! Good Luck!");
        initialize();
    }

    private void printBoard() {
        grid.getChildren().clear();

        colorChessBoard();
        setFinish();
        styleLegalFields();
        drawKnight();
    }

    public void openScoreboard(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lonelyKnight/scoreboard.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void openHelp(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lonelyKnight/help.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void colorChessBoard() {
        for (var row = 0; row < grid.getRowCount(); row++) {
            for (var col = 0; col < grid.getColumnCount(); col++) {
                var square = new StackPane();
                square.getStyleClass().add("square");
                square.getStyleClass().add((row + col) % 2 == 0 ? "light" : "dark");
                grid.add(square, col, row);
            }
        }
    }

    private void drawKnight() {
        Position knightPos = state.getKnightsPos();

        ImageView knight = new ImageView("/images/"+settings.getDarkKnight());
        knight.setFitHeight(60);
        knight.setFitWidth(60);
        grid.add(knight, knightPos.getCol(), knightPos.getRow());
    }

    private void styleLegalFields() {
        if (state.isOver()) {
            return;
        }
        ArrayList<Position> allMoves = state.allMovesArray();

        for (var pos : allMoves) {
            var square = new StackPane();
            square.getStyleClass().add("legal");
            square.setOnMouseClicked(this::moveKnight);
            grid.add(square, pos.getCol(), pos.getRow());
        }
    }

    private void setFinish() {
        var url = "/images/finish.png";
        if (state.board[state.rowBorder - 1][state.colBorder - 1] == State.KNIGHT) {
            url = "/images/finishBorder.png";
        }
        ImageView finish = new ImageView(url);
        finish.setFitHeight(responsiveSize("height"));
        finish.setFitWidth(responsiveSize("width"));
        finish.setOnMouseClicked(this::moveKnight);
        grid.add(finish, state.colBorder - 1, state.rowBorder - 1);
    }

    private int responsiveSize(String whatToCalc) {

        int defVal = 75;

        if (whatToCalc.equals("width")) {
            if (grid.getWidth() == 0) {
                return defVal;
            }
            return (int) grid.getWidth() / grid.getRowCount();
        }

        if (whatToCalc.equals("height")) {
            if (grid.getHeight() == 0) {
                return defVal;
            }
            return (int) grid.getHeight() / grid.getColumnCount();
        }

        return defVal;
    }


    private void moveKnight(javafx.scene.input.MouseEvent event) {
        var source = (Node) event.getSource();
        var row = GridPane.getRowIndex(source);
        var col = GridPane.getColumnIndex(source);
        state.moveKnight(new Position(row, col));

        stopwatch.start();
        setFeedBackLabel("Keep going you got this!");

        steps.set(steps.get() + 1);

        isGameOver();
        printBoard();
    }

    private void isGameOver() {
        if (state.isOver()) {
            grid.setDisable(true);
            stopwatch.stop();
            setFeedBackLabel("Congratulations! You beat the game!");
            solved = true;
            saveData();
        }
    }

    public void backToMenu(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/landingPage/ui.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
