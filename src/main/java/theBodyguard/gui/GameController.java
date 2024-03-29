package theBodyguard.gui;

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
import settings.SettingsModel;
import startApp.Position;
import startApp.Stopwatch;
import theBodyguard.state.GameState;
import theBodyguard.state.State;

import java.io.IOException;
import java.util.ArrayList;

public class GameController extends startApp.GameController {
    @FXML
    private GridPane grid;

    private GameState state = new GameState();

    private Stopwatch stopwatch = new Stopwatch();
    @FXML
    private Label feedBackLabel;
    @FXML
    private Label stopWatch;

    @FXML
    private javafx.scene.control.Label stepLabel;

    private final IntegerProperty steps = new SimpleIntegerProperty();

    private boolean solved;

    private SettingsModel settings = new SettingsModel();

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
        state.saveData("theBodyguard",stopwatch.getElapsedTimeFormatted(),steps.getValue(),solved);
    }

    private void setFeedBackLabel(String text){
        feedBackLabel.setText(text);
    }

    private void updateElapsedTimeLabel() {
        Platform.runLater(() -> stopWatch.setText(String.format(stopwatch.getElapsedTimeFormatted())));
    }

    public void openScoreboard(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/theBodyguard/scoreboard.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void openHelp(final ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/theBodyguard/help.fxml"));
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
        setFeedBackLabel("Next Player: "+state.nextPlayer);
        grid.getChildren().clear();
        isGameOver();

        colorChessBoard();
        drawFinish();
        drawKnight();
        drawKing();

        if (state.nextPlayer == State.KNIGHT){
            drawLegalMovesForKnight();
        } else if (state.nextPlayer == State.KING) {
            drawLegalMovesForKing();
        }
    }
    private void drawLegalMovesForKnight() {
        if (state.isOver()) {
            return;
        }else {
            Position knight = state.getKnightPos();
            StackPane next = new StackPane();
            next.getStyleClass().add("nextPlayer");
            grid.add(next,knight.getCol(),knight.getRow());
        }
        ArrayList<Position> legitMoves = state.legitKnightMoves();
        for (var pos : legitMoves) {
            StackPane overlayPane = new StackPane();
            overlayPane.setStyle("-fx-background-color: "+toCssColor(settings.getLegalColor())+";");
            overlayPane.setOnMouseClicked(this::moveKnight);
            grid.add(overlayPane, pos.getCol(), pos.getRow());
        }
    }

    private void drawLegalMovesForKing() {
        if (state.isOver()) {
            return;
        }else {
            Position knight = state.getKingPos();
            StackPane next = new StackPane();
            next.getStyleClass().add("nextPlayer");
            grid.add(next,knight.getCol(),knight.getRow());
        }
        ArrayList<Position> legitMoves = state.legitKingMoves();
        for (var pos : legitMoves) {
            StackPane overlayPane = new StackPane();
            overlayPane.setStyle("-fx-background-color: "+toCssColor(settings.getLegalColor())+";");
            overlayPane.setOnMouseClicked(this::moveKing);
            grid.add(overlayPane, pos.getCol(), pos.getRow());
        }
    }

    private void moveKnight(MouseEvent event) {
        var source = (Node) event.getSource();
        var row = GridPane.getRowIndex(source);
        var col = GridPane.getColumnIndex(source);
        state.movePiece(state.getKnightPos(),new Position(row,col));

        steps.set(steps.get()+1);

        stopwatch.start();

        printBoard();
    }

    private void moveKing(MouseEvent event) {
        var source = (Node) event.getSource();
        var row = GridPane.getRowIndex(source);
        var col = GridPane.getColumnIndex(source);
        Logger.trace("Move to (" + row + "," + col + ")");
        state.movePiece(state.getKingPos(),new Position(row,col));

        steps.set(steps.get()+1);

        printBoard();
    }

    private void drawKnight(){
        Position knightPos = state.getKnightPos();

        ImageView knight = new ImageView("/images/"+settings.getDarkKnight());
        knight.setFitHeight(60);
        knight.setFitWidth(60);
        grid.add(knight,knightPos.getCol(),knightPos.getRow());
    }

    private void drawKing(){
        Position KingPos = state.getKingPos();
        ImageView king = new ImageView("/images/"+settings.getKing());
        king.setFitHeight(60);
        king.setFitWidth(60);
        grid.add(king,KingPos.getCol(),KingPos.getRow());
    }

    private void drawFinish(){
        var url = "/images/finish.png";

        Position king = state.getKingPos();
        Position knight = state.getKnightPos();
        Position finishPos = new Position(state.rowBorder-1, state.colBorder-2);
        if(finishPos.equals(king) ||finishPos.equals(knight)) {
            url = "/images/finishBorder.png";
        }

        ImageView finish = new ImageView(url);
        finish.setFitHeight(75);
        finish.setFitWidth(75);
        finish.toBack();
        grid.add(finish,finishPos.getCol(),finishPos.getRow());
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

    private boolean isGameOver(){

        if(state.isOver()){
            solved = true;
            grid.setDisable(true);
            stopwatch.stop();
            setFeedBackLabel("CONGRATULATIONS! You beat the game!");
            Logger.error("GAME OVER!");
            saveData();
            return  true;
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
