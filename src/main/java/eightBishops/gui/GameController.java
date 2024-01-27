package eightBishops.gui;

import eightBishops.state.GameState;
import eightBishops.state.State;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.tinylog.Logger;
import startApp.Position;

import java.util.ArrayList;
import java.util.List;

public class GameController {


    @FXML
    private GridPane grid;

    private final GameState state = new GameState();

    private Node[][] board;

    private Position selected;
    private final List<Node> validNodes = new ArrayList<>();

    @FXML
    private void initialize(){
        printBoard();
    }

    private void printBoard(){
        board = new Node[grid.getRowCount()][grid.getColumnCount()];

        colorChessBoard();

        for (var row = 0; row < grid.getRowCount(); row++) {
            for (var col = 0; col < grid.getColumnCount(); col++) {
                if(state.board[row][col] == State.WHITE) {
                    ImageView whiteBishop = new ImageView("/eightBishops/images/whiteBishop.png");
                    whiteBishop.setFitHeight(100);
                    whiteBishop.setFitWidth(100);
                    if(state.nextPlayer == State.WHITE) {
                        whiteBishop.setOnMouseClicked(this::selectClick);
                    }
                    grid.add(whiteBishop,col,row);
                    board[row][col] = whiteBishop;
                } else if (state.board[row][col] == State.BLACK) {
                    ImageView blackBishop = new ImageView("/eightBishops/images/blackBishop.png");
                    blackBishop.setFitHeight(100);
                    blackBishop.setFitWidth(100);
                    if(state.nextPlayer == State.BLACK) {
                        blackBishop.setOnMouseClicked(this::selectClick);
                    }
                    grid.add(blackBishop,col,row);
                    board[row][col] = blackBishop;
                }
            }
        }

    }

    private void moveOnClick(javafx.scene.input.MouseEvent event){
        clearValidNodes();
        var source = (Node) event.getSource();
        var row = GridPane.getRowIndex(source);
        var col = GridPane.getColumnIndex(source);
        state.moveBishop(selected,new Position(row,col));

        isGameover();
        printBoard();
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
            validNode.getStyleClass().add("legal");
            validNode.setOnMouseClicked(this::moveOnClick);
        }
    }

    private void clearValidNodes() {
        validNodes.forEach(node -> {
            node.getStyleClass().remove("legal");
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
            Logger.trace("CONGRATULATIONS!!");
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
        if(counter == 3){
            Logger.trace("GAME OVER!\n No more moves for: " +state.nextPlayer+" !");
            return true;
        }
        return false;
    }

}
