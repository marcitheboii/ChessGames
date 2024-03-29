package eightBishops.state;

import startApp.GameModel;
import startApp.Position;

import java.util.ArrayList;
import java.util.Arrays;

public class GameState extends GameModel {

    public final int rowBorder = 5;

    public final int colBorder = 4;

    public State[][] board;

    public State nextPlayer;


    public GameState(){


        board = new State[rowBorder][colBorder];

        nextPlayer = State.WHITE;

        populateBoard();

    }

    private void populateBoard(){
        for(int i=0; i < colBorder; i++ ) {
            board[0][i] = State.BLACK;
            board[1][i] = State.SAFE;
            board[2][i] = State.SAFE;
            board[3][i] = State.SAFE;
            board[4][i] = State.WHITE;
        }

        calculateBoard();
    }

    private void clearOutPrevCalc(){

        for (int row = 0; row < rowBorder; row++) {
            for (int col = 0; col < colBorder; col++) {
                if (board[row][col] == State.WHITE || board[row][col] == State.BLACK){
                    continue;
                }
                board[row][col] = State.SAFE;

            }
        }
    }
    private void calculateBoard(){
        clearOutPrevCalc();
        ArrayList<Position> allBishopsPos = getAllBishopsPos();

        for (var bishop : allBishopsPos){
            setStepsForBishop(bishop);
        }
    }
    public ArrayList<Position> setStepsForBishop(Position bishopPos){

        String[] directions = {"upRight","upLeft","DownRight","DownLeft"};

        ArrayList<Position> upRight = new ArrayList<>();
        ArrayList<Position> upLeft = new ArrayList<>();
        ArrayList<Position> downRight = new ArrayList<>();
        ArrayList<Position> downLeft = new ArrayList<>();

        for (var direction : directions){
            if(direction.equals("upRight")){
                int[] cord = {-1,1};
                upRight = directedBishopSteps(bishopPos,cord,upRight);
            }
            if(direction.equals("upLeft")){
                int[] cord = {-1,-1};
                upLeft = directedBishopSteps(bishopPos,cord,upLeft);
            }
            if(direction.equals("DownRight")){
                int[] cord = {1,1};
                downRight = directedBishopSteps(bishopPos,cord,downRight);
            }
            if(direction.equals("DownLeft")){
                int[] cord = {1,-1};
                downLeft = directedBishopSteps(bishopPos,cord,downLeft);
            }
        }

        ArrayList<Position> AllPossibleMoves = new ArrayList<>();

        AllPossibleMoves.addAll(upRight);
        AllPossibleMoves.addAll(upLeft);
        AllPossibleMoves.addAll(downLeft);
        AllPossibleMoves.addAll(downRight);

        return AllPossibleMoves;
    }


    private ArrayList<Position> directedBishopSteps(Position bishopPos,int[] cord,ArrayList<Position> moves){

        State colorOfBishop = board[bishopPos.getRow()][bishopPos.getCol()];


        if(colorOfBishop != State.WHITE && colorOfBishop != State.BLACK){
            colorOfBishop = findPrevColor(bishopPos,cord);
        }
        State enemyOfBishop = (colorOfBishop == State.WHITE) ? State.BLACK : State.WHITE;
        State hitOfBishop = (colorOfBishop == State.WHITE) ? State.WHITE_HIT : State.BLACK_HIT;
        State dieOfBishop = (colorOfBishop == State.WHITE) ? State.BLACK_HIT : State.WHITE_HIT;


        Position futurePos = new Position(bishopPos.getRow() + cord[0],bishopPos.getCol() + cord[1]);

        if(!isOnBoard(futurePos)) {
            return moves;
        } else if (board[futurePos.getRow()][futurePos.getCol()] == colorOfBishop) {
            return moves;
        } else if (board[futurePos.getRow()][futurePos.getCol()] == hitOfBishop || board[futurePos.getRow()][futurePos.getCol()] == State.SAFE) {
            board[futurePos.getRow()][futurePos.getCol()] = hitOfBishop;
            moves.add(futurePos);
            return directedBishopSteps(futurePos,cord,moves);
        }else if (board[futurePos.getRow()][futurePos.getCol()] == dieOfBishop || board[futurePos.getRow()][futurePos.getCol()] == State.DEAD) {
            if(board[futurePos.getRow()][futurePos.getCol()] == dieOfBishop){
                board[futurePos.getRow()][futurePos.getCol()] = State.DEAD;
            }
            directedBishopSteps(futurePos,cord,moves);
        }

        return moves;
    }

    private State findPrevColor(Position bishopPos,int[] cord){


        Position futurePos = new Position(bishopPos.getRow() - cord[0],bishopPos.getCol() - cord[1]);

        if(board[futurePos.getRow()][futurePos.getCol()] == State.WHITE || board[futurePos.getRow()][futurePos.getCol()] == State.BLACK){
            return board[futurePos.getRow()][futurePos.getCol()];
        }else{
            return findPrevColor(futurePos,cord);
        }
    }

    public void moveBishop(Position from, Position to){
        if(isOnBoard(from) && isOnBoard(to)){
            if (board[from.getRow()][from.getCol()] == State.BLACK){

                board[from.getRow()][from.getCol()] = State.SAFE;
                board[to.getRow()][to.getCol()] = State.BLACK;

            } else if (board[from.getRow()][from.getCol()] == State.WHITE){

                board[from.getRow()][from.getCol()] = State.SAFE;
                board[to.getRow()][to.getCol()] = State.WHITE;
            }
        }

        changeNextPlayer();
        calculateBoard();
    }

    private ArrayList<Position> getAllBishopsPos(){

        ArrayList<Position> pos = new ArrayList<>();

        for (int row = 0; row < rowBorder; row++) {
            for (int col = 0; col < colBorder; col++) {
                if(board[row][col] == State.WHITE || board[row][col] == State.BLACK){
                    pos.add(new Position(row,col));
                }
            }
        }

        return pos;
    }

    public ArrayList<Position> getWhiteBishopsPos(){

        ArrayList<Position> pos = new ArrayList<>();

        for (int row = 0; row < rowBorder; row++) {
            for (int col = 0; col < colBorder; col++) {
                if(board[row][col] == State.WHITE){
                    pos.add(new Position(row,col));
                }
            }
        }

        return pos;
    }

    public boolean goalTest() {
        int counter = 0;
        for(int i=0; i < colBorder; i++ ){
            if (board[0][i] == State.WHITE && board[4][i] == State.BLACK) {
                counter++;
            }
        }
        return counter == colBorder;
    }

    public ArrayList<Position> getBlackBishopsPos(){

        ArrayList<Position> pos = new ArrayList<>();

        for (int row = 0; row < rowBorder; row++) {
            for (int col = 0; col < colBorder; col++) {
                if(board[row][col] == State.BLACK){
                    pos.add(new Position(row,col));
                }
            }
        }

        return pos;
    }


    private void changeNextPlayer(){
        if(nextPlayer == State.WHITE){
            nextPlayer = State.BLACK;
        } else if (nextPlayer == State.BLACK) {
            nextPlayer = State.WHITE;
        }
    }

    private boolean isOnBoard(Position position){
        return position.getRow() >= 0 &&
                position.getRow() < rowBorder &&
                position.getCol() >= 0 &&
                position.getCol() < colBorder;
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
