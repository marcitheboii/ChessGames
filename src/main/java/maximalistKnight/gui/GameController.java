package maximalistKnight.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import maximalistKnight.state.GameState;
import maximalistKnight.state.State;
import org.tinylog.Logger;
import startApp.Position;

public class GameController {
    @FXML
    private GridPane grid;

    private final GameState state = new GameState();

    private Node[][] board;

    @FXML
    private void initialize(){
        printBoard();
    }

    private void printBoard(){
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
                    has_been.setFitHeight(100);
                    has_been.setFitWidth(100);
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

        ImageView knight = new ImageView("/maximalistKnight/images/black_knight.png");
        knight.setFitHeight(100);
        knight.setFitWidth(100);
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

        isGameOver();
        printBoard();
    }

    private void isGameOver(){
        switch(state.isOver()){
            case 0:
                break;
            case 1:
                Logger.error("GAME OVER, YOU LOST");
                break;
            case 2:
                Logger.error("GAME OVER, YOU WIN");
                break;
        }
    }

}
