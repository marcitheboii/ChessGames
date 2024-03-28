package theBodyguard.state;

import org.tinylog.Logger;
import startApp.GameModel;
import startApp.Position;

import java.util.ArrayList;
import java.util.Arrays;

public class GameState extends GameModel {
    public final int rowBorder = 8;

    public final int colBorder = 8;

    public State[][] board;

    public State nextPlayer = State.KNIGHT;

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

        board[5][1]= State.KING;
        board[5][2]= State.KNIGHT;
        board[rowBorder-1][colBorder-2]= State.FINISH;

        calculateBoard();
    }
    private void clearOutPreviousCalculations(){
        for (int row = 0; row < rowBorder; row++) {
            for (int col = 0; col < colBorder; col++) {
                if(board[row][col] == State.KING_HIT || board[row][col] == State.KNIGHT_HIT){
                    board[row][col] = State.EMPTY;
                }
            }
        }
    }

    private void calculateBoard(){
        clearOutPreviousCalculations();

        setNextPlayer();

        if(nextPlayer == State.KNIGHT){
            for (Position pos : legitKnightMoves() ){
                if (board[pos.getRow()][pos.getCol()] == State.KING  || board[pos.getRow()][pos.getCol()] == State.FINISH){
                    continue;
                }
                board[pos.getRow()][pos.getCol()] = State.KNIGHT_HIT;
            }
        } else if (nextPlayer == State.KING) {
            for (Position pos : legitKingMoves() ){
                if (board[pos.getRow()][pos.getCol()] == State.KNIGHT  || board[pos.getRow()][pos.getCol()] == State.FINISH){
                    continue;
                }
                board[pos.getRow()][pos.getCol()] = State.KING_HIT;
            }
        }

    }

    private boolean isOnBoard(Position position){
        return position.getRow() >= 0 &&
                position.getRow() < rowBorder &&
                position.getCol() >= 0 &&
                position.getCol() < colBorder;
    }

    public Position getKnightPos(){
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

    public Position getKingPos(){
        Position pos = null;

        for (int row = 0; row < rowBorder; row++) {
            for (int col = 0; col < colBorder; col++) {
                if(board[row][col] == State.KING){
                    pos = new Position(row,col);
                }
            }
        }
        return pos;
    }

    public ArrayList<Position> allKnightMovesOnBoard(Position KnightPos){

        if(KnightPos == null){
            KnightPos = getKnightPos();
        }

        ArrayList<Position> allMoves = new ArrayList<>();

        int[] allMovesY = { 1, 2, 2, 1, -1, -2, -2, -1 };
        int[] allMovesX = { 2, 1, -1, -2, -2, -1, 1, 2 };

        for (int i = 0; i < allMovesX.length; i++) {

            Position futurePosition = new Position(KnightPos.getRow() + allMovesX[i],KnightPos.getCol() + allMovesY[i]);

            if(isOnBoard(futurePosition)) {
                allMoves.add(futurePosition);
            }
        }
        return allMoves;
    }

    public ArrayList<Position> legitKnightMoves(){
        clearOutPreviousCalculations();

        ArrayList<Position> moves = new ArrayList<>();

        for (Position position : allKingMovesOnBoard(getKingPos())) {
            if(board[position.getRow()][position.getCol()] == State.KNIGHT || board[position.getRow()][position.getCol()] == State.FINISH){
                continue;
            }
            board[position.getRow()][position.getCol()] = State.KING_HIT;
        }

        for (Position pos : allKnightMovesOnBoard(getKnightPos())) {
            if (board[pos.getRow()][pos.getCol()] == State.KING_HIT || board[pos.getRow()][pos.getCol()] == State.FINISH) {
                moves.add(pos);
            }
        }

        for (Position pos : allKnightMovesOnBoard(getKnightPos())){
            ArrayList<Position> FutureHits = allKnightMovesOnBoard(pos);
            for (Position pos2 : FutureHits){
                if(board[pos2.getRow()][pos2.getCol()] == State.KING){
                    moves.add(pos);
                }
            }
        }
        return moves;
    }

    public ArrayList<Position> allKingMovesOnBoard(Position KingPos){

        if(KingPos == null){
            KingPos = getKnightPos();
        }

        ArrayList<Position> allMoves = new ArrayList<>();

        int[] allMovesX = { -1, -1, 0, 1, 1,  1,  0, -1 };
        int[] allMovesY = {  0,  1, 1, 1, 0, -1, -1, -1 };


        for (int i = 0; i < allMovesX.length; i++) {

            Position futurePosition = new Position(KingPos.getRow() + allMovesX[i],KingPos.getCol() + allMovesY[i]);

            if(isOnBoard(futurePosition)) {
                allMoves.add(futurePosition);
            }
        }
        return allMoves;
    }

    public ArrayList<Position> legitKingMoves(){

        clearOutPreviousCalculations();

        ArrayList<Position> moves = new ArrayList<>();

        for (Position position : allKnightMovesOnBoard(getKnightPos())) {
            if(board[position.getRow()][position.getCol()] == State.KING || board[position.getRow()][position.getCol()] == State.FINISH){
                continue;
            }
            board[position.getRow()][position.getCol()] = State.KNIGHT_HIT;
        }

        for (Position pos : allKingMovesOnBoard(getKingPos())) {
            if (board[pos.getRow()][pos.getCol()] == State.KNIGHT_HIT || board[pos.getRow()][pos.getCol()] == State.FINISH) {
                moves.add(pos);
            }
        }

        for (Position pos : allKingMovesOnBoard(getKingPos())){
            ArrayList<Position> FutureHits = allKingMovesOnBoard(pos);
            for (Position pos2 : FutureHits){
                if(board[pos2.getRow()][pos2.getCol()] == State.KNIGHT){
                    moves.add(pos);
                }
            }
        }
        return moves;
    }

    public void movePiece(Position from,Position to){
        if(!isOnBoard(from) || !isOnBoard(to)){
            Logger.error("CANT MOVE PIECE THERE CUZ ITS NOT ON THE BOARD");
            return;
        }
        State piece = board[from.getRow()][from.getCol()];
        if(piece == State.KING){
            board[from.getRow()][from.getCol()] = State.KING_HIT;
            board[to.getRow()][to.getCol()] = State.KING;

        } else if (piece == State.KNIGHT) {
            board[from.getRow()][from.getCol()] = State.KNIGHT_HIT;
            board[to.getRow()][to.getCol()] = State.KNIGHT;
        }

        calculateBoard();
    }

    private  void setNextPlayer(){

        ArrayList<Position> allKingHits = allKingMovesOnBoard(getKingPos());
        ArrayList<Position> allKnightHits = allKnightMovesOnBoard(getKnightPos());

        for (Position pos : allKingHits){
            if(board[pos.getRow()][pos.getCol()] == State.KNIGHT){
                nextPlayer = State.KNIGHT;
                return;
            }
        }

        for (Position pos : allKnightHits){
            if(board[pos.getRow()][pos.getCol()] == State.KING){
                nextPlayer = State.KING;
                return;
            }
        }

        Logger.error("CANT SET NEW NEXT PLAYER!!");
    }

    public boolean isOver(){
        if(board[rowBorder-1][colBorder-2] == State.KNIGHT || board[rowBorder-1][colBorder-2] == State.KING){
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
