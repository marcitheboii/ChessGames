package games.tightGame.state;

import org.tinylog.Logger;
import games.GameModel;
import position.Position;

import java.util.Arrays;

public class GameState extends GameModel {

    public final int rowBorder = 2;

    public final int colBorder = 3;

    public State[][] board;

    public GameState(){

        board = new State[rowBorder][colBorder];

        populateBoard();
    }

    private void populateBoard(){

        board[0][0]= State.KING;
        board[0][1]= State.BISHOP;
        board[0][2]= State.BISHOP;
        board[1][0]= State.ROOK;
        board[1][1]= State.ROOK;
        board[1][2]= State.EMPTY;
    }
    public void movePiece(Position from, Position to){
        if(!isOnBoard(from) || !isOnBoard(to)){
            Logger.error("CANT MOVE PIECE THERE CUZ ITS NOT ON THE BOARD");
            return;
        }

        State piece = board[from.getRow()][from.getCol()];

        switch (piece){
            case KING -> {
                board[from.getRow()][from.getCol()] = State.EMPTY;
                board[to.getRow()][to.getCol()] = State.KING;
            }
            case ROOK -> {
                board[from.getRow()][from.getCol()] = State.EMPTY;
                board[to.getRow()][to.getCol()] = State.ROOK;
            }
            case BISHOP -> {
                board[from.getRow()][from.getCol()] = State.EMPTY;
                board[to.getRow()][to.getCol()] = State.BISHOP;
            }
        }
    }

    public Position showMovesForSelected(Position pos){
        State piece = board[pos.getRow()][pos.getCol()];

        switch (piece){
            case KING -> {
                return getKingMoves(pos);
            }
            case ROOK -> {
                return getRookMoves(pos);
            }
            case BISHOP -> {
                return getBishopMoves(pos);
            }
            default -> {
                return null;
            }
        }
    }

    private Position getKingMoves(Position pos){

        int[] allMovesX = { -1, -1, 0, 1, 1,  1,  0, -1 };
        int[] allMovesY = {  0,  1, 1, 1, 0, -1, -1, -1 };


        for (int i = 0; i < allMovesX.length; i++) {

            Position futurePosition = new Position(pos.getRow() + allMovesX[i],pos.getCol() + allMovesY[i]);

            if(isOnBoard(futurePosition) && board[futurePosition.getRow()][futurePosition.getCol()] == State.EMPTY) {
                return futurePosition;
            }
        }
        return null;
    }

    private Position getBishopMoves(Position pos){

        int[] allMovesX = { -1, -1, 1,  1};
        int[] allMovesY = {  1, -1, 1, -1};


        for (int i = 0; i < allMovesX.length; i++) {

            Position futurePosition = new Position(pos.getRow() + allMovesX[i],pos.getCol() + allMovesY[i]);

            if(isOnBoard(futurePosition) && board[futurePosition.getRow()][futurePosition.getCol()] == State.EMPTY) {
                return futurePosition;
            }
        }
        return null;
    }

    private Position getRookMoves(Position pos){

        int[] allMovesX = { -1, 1, 0, 0};
        int[] allMovesY = {  0, 0, 1, -1};


        for (int i = 0; i < allMovesX.length; i++) {

            Position futurePosition = new Position(pos.getRow() + allMovesX[i],pos.getCol() + allMovesY[i]);

            if(isOnBoard(futurePosition) && board[futurePosition.getRow()][futurePosition.getCol()] == State.EMPTY) {
                return futurePosition;
            }
        }
        return null;
    }

    private boolean isOnBoard(Position position){
        return position.getRow() >= 0 &&
                position.getRow() < rowBorder &&
                position.getCol() >= 0 &&
                position.getCol() < colBorder;
    }

    public boolean isOver(){
        if(
                board[0][0] == State.BISHOP &&
                board[0][1] == State.BISHOP &&
                board[0][2] == State.EMPTY &&
                board[1][0] == State.ROOK &&
                board[1][1] == State.ROOK &&
                board[1][2] == State.KING
        ){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        sb.append("\n");
        for (int row = 0; row < rowBorder; row++) {
            for (int col = 0; col < colBorder; col++) {
                sb.append(board[row][col]).append(' ');
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameState state)) return false;
        return Arrays.deepEquals(board, state.board);
    }
}
