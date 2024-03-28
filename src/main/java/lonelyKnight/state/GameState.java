package lonelyKnight.state;

import org.tinylog.Logger;
import startApp.GameModel;
import startApp.Position;

import java.util.ArrayList;
import java.util.Arrays;

public class GameState extends GameModel {

    public final int rowBorder = 8;

    public final int colBorder = 8;

    public State[][] board;

    public GameState(){

        board = new State[rowBorder][colBorder];

        populateBoard();
    }

    private void populateBoard(){

        for (int row = 0; row < rowBorder; row++) {
            for (int col = 0; col < colBorder; col++) {
                board[row][col] = State.EMPTY;
            }
        }

        board[0][0]=State.KNIGHT;
        board[rowBorder-1][colBorder-1]=State.FINISH;

        calculateBoard();
    }
    private void clearOutPreviousCalculations(){
        for (int row = 0; row < rowBorder; row++) {
            for (int col = 0; col < colBorder; col++) {
                if(board[row][col] == State.CAN_GO){
                    board[row][col] = State.EMPTY;
                }
            }
        }
    }

    private void calculateBoard(){
        ArrayList<Position> allMoves = allMovesArray();

        clearOutPreviousCalculations();

        //SET REACHABLE POSITIONS TO CAN_GO
        for (var pos : allMoves){
            board[pos.getRow()][pos.getCol()] = State.CAN_GO;
        }
    }

    private boolean isOnBoard(Position position){
        return position.getRow() >= 0 &&
                position.getRow() < rowBorder &&
                position.getCol() >= 0 &&
                position.getCol() < colBorder;
    }

    public Position getKnightsPos(){
        Position pos = null;

        for (int row = 0; row < rowBorder; row++) {
            for (int col = 0; col < colBorder; col++) {
                if(board[row][col] == State.KNIGHT){
                    pos = new Position(row,col);
                }
            }
        }
        return pos;
    }

    public ArrayList<Position> allMovesArray(){

        Position standingOn = getKnightsPos();

        ArrayList<Position> allMoves = new ArrayList<>();

        int[] allMovesY = { 1, 2, 2, 1, -1, -2, -2, -1 };
        int[] allMovesX = { 2, 1, -1, -2, -2, -1, 1, 2 };

        for (int i = 0; i < allMovesX.length; i++) {

            Position futurePosition = new Position(standingOn.getRow() + allMovesX[i],standingOn.getCol() + allMovesY[i]);

            if(isOnBoard(futurePosition))
                allMoves.add(futurePosition);
        }
        return allMoves;
    }

    public void moveKnight(Position newPos){
        if(board[newPos.getRow()][newPos.getCol()] == State.CAN_GO){
            Position prevPos = getKnightsPos();

            board[newPos.getRow()][newPos.getCol()] = State.KNIGHT;
            board[prevPos.getRow()][prevPos.getCol()] = State.EMPTY;
        }else {
            Logger.error("Knights can only move in the shape of an L!");
        }

        calculateBoard();
    }

    public boolean isOver(){
        Position knightPos = getKnightsPos();
        if(knightPos.getRow() == rowBorder-1 && knightPos.getCol() == colBorder-1){
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
