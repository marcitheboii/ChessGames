package maximalistKnight.state;

import org.json.JSONArray;
import org.json.JSONObject;
import org.tinylog.Logger;
import startApp.GameModel;
import startApp.Position;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameState extends GameModel {

        public final int rowBorder = 6;

        public final int colBorder = 6;

        public State[][] board;

        public GameState(){

            board = new State[rowBorder][colBorder];

            populateBoard();
        }

        private void populateBoard(){

            for (int row = 0; row < rowBorder; row++) {
                for (int col = 0; col < colBorder; col++) {
                    board[row][col] = State.NOT_YET;
                }
            }

            board[0][0]=State.KNIGHT;

            calculateBoard();
        }


        private void clearOutPreviousCalculations(){
            for (int row = 0; row < rowBorder; row++) {
                for (int col = 0; col < colBorder; col++) {
                    if(board[row][col] == State.CAN_GO){
                        board[row][col] = State.NOT_YET;
                    }
                }
            }
        }


    private void calculateBoard(){
        ArrayList<Position> allMoves = allMovesArray();

        clearOutPreviousCalculations();

        for (var pos : allMoves){
            if(board[pos.getRow()][pos.getCol()] == State.HAS_BEEN){
                continue;
            }
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
            board[prevPos.getRow()][prevPos.getCol()] = State.HAS_BEEN;
        }else {
            Logger.error("Knights can only move in the shape of an L!");
        }
        calculateBoard();

    }

    public int isOver(){

        for (int row = 0; row < rowBorder; row++) {
            for (int col = 0; col < colBorder; col++) {
                if(board[row][col] == State.CAN_GO){
                    return 0;
                }
            }
        }
        return thereIsNoCanGo();
    }

    private int thereIsNoCanGo(){
        for (int row = 0; row < rowBorder; row++) {
            for (int col = 0; col < colBorder; col++) {
                if(board[row][col] == State.NOT_YET){
                    return 1;
                }
            }
        }
            return 2;
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
