package theBodyguard.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.tinylog.Logger;
import startApp.Position;
import theBodyguard.state.GameState;
import theBodyguard.state.State;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    @FXML
    private GridPane grid;

    private final GameState state = new GameState();

    private Node[][] board;
    private final List<Node> validNodes = new ArrayList<>();

    @FXML
    private void initialize(){
        printBoard();
    }

    private void printBoard(){
        isGameOver();
        System.out.println(state);
        board = new Node[grid.getRowCount()][grid.getColumnCount()];

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

    private void drawLegalMovesForKnight(){
        clearValidNodes();
        ArrayList<Position> legitMoves = state.legitKnightMoves();
        for (var pos : legitMoves){
            Node validNode = board[pos.getRow()][pos.getCol()];
            validNodes.add(validNode);
            validNode.getStyleClass().add("legal");
            validNode.setOnMouseClicked(this::moveKnight);
        }
    }

    private void drawLegalMovesForKing(){
        clearValidNodes();
        ArrayList<Position> legitMoves = state.legitKingMoves();
        for (var pos : legitMoves){
            Node validNode = board[pos.getRow()][pos.getCol()];
            validNodes.add(validNode);
            validNode.getStyleClass().add("legal");
            validNode.setOnMouseClicked(this::moveKing);
        }
    }

    private void moveKnight(MouseEvent event) {
        clearValidNodes();
        var source = (Node) event.getSource();
        var row = GridPane.getRowIndex(source);
        var col = GridPane.getColumnIndex(source);
        state.movePiece(state.getKnightPos(),new Position(row,col));

        printBoard();
    }

    private void moveKing(MouseEvent event) {
        clearValidNodes();
        var source = (Node) event.getSource();
        var row = GridPane.getRowIndex(source);
        var col = GridPane.getColumnIndex(source);
        Logger.trace("Move to (" + row + "," + col + ")");
        state.movePiece(state.getKingPos(),new Position(row,col));

        printBoard();
    }

    private void clearValidNodes() {
        validNodes.forEach(node -> {
            node.getStyleClass().remove("legal");
            node.setOnMouseClicked(event1 -> {});
        });
    }

    private void drawKnight(){

        Position knightPos = state.getKnightPos();
        System.out.println("IDE RAJZOLOM A HUSZART: "+ knightPos);

        ImageView knight = new ImageView("/theBodyguard/images/blackKnight.png");
        knight.setFitHeight(70);
        knight.setFitWidth(70);
        grid.add(knight,knightPos.getCol(),knightPos.getRow());
        board[knightPos.getRow()][knightPos.getCol()] = knight;
    }

    private void drawKing(){
        Position KingPos = state.getKingPos();

        ImageView king = new ImageView("/theBodyguard/images/king.png");
        king.setFitHeight(70);
        king.setFitWidth(70);
        grid.add(king,KingPos.getCol(),KingPos.getRow());
        board[KingPos.getRow()][KingPos.getCol()] = king;
    }

    private void drawFinish(){
        Position finishPos = new Position(state.rowBorder-1, state.colBorder-2);

        ImageView finish = new ImageView("/theBodyguard/images/finish.png");
        finish.setFitHeight(70);
        finish.setFitWidth(70);
        grid.add(finish,finishPos.getCol(),finishPos.getRow());
        board[finishPos.getRow()][finishPos.getCol()] = finish;
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

    private boolean isGameOver(){

        if(state.isOver()){
            grid.setDisable(true);
            Logger.error("GAME OVER!");
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
