package lonelyKnight.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import lonelyKnight.state.GameState;
import org.tinylog.Logger;
import startApp.Position;

import java.util.ArrayList;

public class GameController {

    @FXML
    private GridPane grid;

    private final GameState state = new GameState();

    @FXML
    private void initialize(){
        printBoard();
    }

    private void printBoard(){

        colorChessBoard();
        styleLegalFields();
        setFinish();
        drawKnight();


        Logger.info("Board printed");
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

    private void drawKnight(){
        Position knightPos = state.getKnightsPos();

        ImageView knight = new ImageView("/lonelyKnight/images/black_knight.png");
        knight.setFitHeight(70);
        knight.setFitWidth(70);
        grid.add(knight,knightPos.getCol(),knightPos.getRow());
    }

    private void styleLegalFields(){
        ArrayList<Position> allMoves = state.allMovesArray();

        for (var pos : allMoves){
            var square = new StackPane();
            square.getStyleClass().add("legal");
            square.setOnMouseClicked(this::moveKnight);
            grid.add(square, pos.getCol(), pos.getRow());
        }
    }

    private void setFinish(){
        ImageView finish = new ImageView("/lonelyKnight/images/finish.png");
        finish.setFitHeight(70);
        finish.setFitWidth(70);
        finish.setOnMouseClicked(this::moveKnight);
        grid.add(finish,state.colBorder-1,state.rowBorder-1);
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
        if(state.isOver()){
            Logger.error("VEGEEE");
        }
    }

}
