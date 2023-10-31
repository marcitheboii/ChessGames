package sixknights.state;

import java.util.ArrayList;
import java.util.Arrays;

import static sixknights.state.State.*;

public class ChessState {

    /**
     *  A sakktábla sorainak száma.
     */
    public final int rowBorder = 4;

    /**
     *  A sakktábla oszlopainak száma.
     */
    public final int colBorder = 3;

    /**
     *  A sakktábát reprezentáló 2dimenziós tömb.
     */
    public State[][] board;

    /**
     * A játékban következő oldalt tárolja.
     */
    public State nextPlayer;

    /**
     *  Argumentum nélküli konstruktor.
     */
    public ChessState(){
        this.board = new State[rowBorder][colBorder];
        this.nextPlayer = WHITE;

        setStartBoard();
    }

    /**
     * Több argumentummal rendelkező konstruktor, főként egységteszteléshez lett használva.
     *
     * @param b1 első fekete huszár
     * @param b2 második fekete huszár
     * @param b3 harmadik fekete huszár
     * @param w1 első fehér huszár
     * @param w2 második fehér huszár
     * @param w3 harmadik fehér huszár
     * @param color játékban következő oldal
     */
    public ChessState(Position b1,Position b2,Position b3,Position w1,Position w2,Position w3, State color){
        this.board = new State[rowBorder][colBorder];
        this.nextPlayer = color;

        board[b1.getRow()][b1.getCol()] = BLACK;
        board[b2.getRow()][b2.getCol()] = BLACK;
        board[b3.getRow()][b3.getCol()] = BLACK;

        board[w1.getRow()][w1.getCol()] = WHITE;
        board[w2.getRow()][w2.getCol()] = WHITE;
        board[w3.getRow()][w3.getCol()] = WHITE;
    }

    /**
     *  A játéktábla (board) érékeit állítja alap állásba, a szabályok szerint.
     */
    public void setStartBoard(){
        for(int i=0; i < colBorder; i++ ) {
            board[0][i] = BLACK;
            board[1][i] = SAFE;
            board[2][i] = SAFE;
            board[3][i] = WHITE;
        }
        nextPlayer = WHITE;
        calculateBoard();
    }

    /**
     * Végállapotot ellenörző függvény.
     * @return igaz, a végállapotban van a játék
     */
    public boolean goalTest() {
        int counter = 0;
        for(int i=0; i < colBorder; i++ ){
            if (this.board[0][i] == WHITE && this.board[3][i] == BLACK)
                counter++;
        }
        return counter == colBorder;
    }

    /**
     *  Egy huszárt mozgat a megadott helyre.
     * @param from A huszár eredeti pozíciója
     * @param to Ahová a huszárt mozdítani szeretnénk
     */
    public void movePiece(Position from, Position to){
        if(board[from.getRow()][from.getCol()] == BLACK) {
            board[to.getRow()][to.getCol()] =  BLACK;
            board[from.getRow()][from.getCol()] = BLACK_HIT;
        }else if(board[from.getRow()][from.getCol()] == WHITE) {
            board[to.getRow()][to.getCol()] = WHITE;
            board[from.getRow()][from.getCol()] = WHITE_HIT;
        }
        calculateBoard();
        changeNextPlayer();
    }

    /**
     *  Következő oldal/szín megválzotzatása.
     */
    public void changeNextPlayer(){
        if(nextPlayer == BLACK) {
            nextPlayer = WHITE;
        } else if (nextPlayer ==WHITE) {
            nextPlayer = BLACK;
        }
    }

    /**
     *  Az osztály board változója alapján átmásolja csak azokat a poziciókat ahol huszár található, minden máshova SAFE állapotot állít be.
     * @return Csak SAFE,WHITE,BACK-ből álló State[][] objektumot ad vissza
     */
    public State[][] prepBoardForCalculation(){
        State[][] newBoard = new State[rowBorder][colBorder];

        for(int row = 0; row < rowBorder; row++){
            for(int col = 0; col < colBorder; col++){
                newBoard[row][col] = SAFE;
                if(this.board[row][col] == BLACK) {
                    newBoard[row][col] = BLACK;
                    }
                else if(this.board[row][col] == WHITE) {
                    newBoard[row][col] = WHITE;
                    }
                }
            }
        return newBoard;
    }

    /**
     * Csakis prepBoardForCalculation függvény által előkészített táblára számolja ki a huszárok által ütésben lévő mezőket.
     */
    public void calculateBoard(){
        this.board = prepBoardForCalculation();
        for(int row = 0; row < rowBorder; row++){
            for(int col = 0; col < colBorder; col++){
                switch (board[row][col]) {
                    case BLACK -> calculateBoardHelper(new Position(row, col), BLACK);
                    case WHITE -> calculateBoardHelper(new Position(row, col), WHITE);
                }
                }
            }

    }

    /**
     * Segédfüggvény a calculateBoard-nak ami konkrétan, egy pozicióból, számolja az ütéseket.
     * @param position huszár pozicója
     * @param nextPlayer huszár színe
     */
    public void calculateBoardHelper(Position position, State nextPlayer){

        if(nextPlayer == BLACK){
            for(Position pos : allMovesArray(position.getRow(), position.getCol())){
                switch (board[pos.getRow()][pos.getCol()]) {
                    case SAFE -> board[pos.getRow()][pos.getCol()] = BLACK_HIT;
                    case WHITE_HIT -> board[pos.getRow()][pos.getCol()] = DEAD;
                }
            }
        }
        else if(nextPlayer == WHITE){
            for(Position pos : allMovesArray(position.getRow(), position.getCol())){
                switch (board[pos.getRow()][pos.getCol()]) {
                    case SAFE -> board[pos.getRow()][pos.getCol()] = WHITE_HIT;
                    case BLACK_HIT -> board[pos.getRow()][pos.getCol()] = DEAD;
                }
            }
        }

    }

    /**
     *  Megmondja hogy a táblán van-e a pozíció, vagy azon kívül.
     * @param position kérdéses pozíció
     * @return igaz ha a táblán van a pozíció
     */
    public boolean isOnBoard(Position position){
        return position.getRow() >= 0 &&
                position.getRow() < rowBorder &&
                position.getCol() >= 0 &&
                position.getCol() < colBorder;
    }

    /**
     *  Egy pozícióból vissza adja az összes lehetséges lépését a huszárnak, ami a táblán van.
     * @param standingOnX huszár x/sor pozíciója
     * @param standingOnY huszár y/oszlop pozíciója
     * @return Position-t tartalmazó lista
     */
    public ArrayList<Position> allMovesArray(int standingOnX, int standingOnY){

        ArrayList<Position> allMoves = new ArrayList<>();

        int[] allMovesY = { 1, 2, 2, 1, -1, -2, -2, -1 };
        int[] allMovesX = { 2, 1, -1, -2, -2, -1, 1, 2 };

            for (int i = 0; i < allMovesX.length; i++) {

                Position futurePosition = new Position(standingOnX + allMovesX[i],standingOnY + allMovesY[i]);

                if(isOnBoard(futurePosition))
                    allMoves.add(futurePosition);
            }
        return allMoves;
    }

    /**
     *  Az összes szabályos lépést adja vissza.
     * @param board aktuális táblapozíció
     * @param standingOnX huszár x/sor pozíciója
     * @param standingOnY huszár y/oszlop pozíciója
     * @param nextPlayer következő oldal/szín
     * @return Positiont-t tartalmazó lista, a lehetséges lépésekkel
     */
    public ArrayList<Position> legalMovesArray(State[][] board, int standingOnX, int standingOnY, State nextPlayer){

        ArrayList<Position> legitMoves = new ArrayList<>();

            for (Position futurePosition : allMovesArray(standingOnX,standingOnY)) {

                if (nextPlayer == WHITE) {
                    if (board[futurePosition.getRow()][futurePosition.getCol()] == WHITE_HIT  ||
                            board[futurePosition.getRow()][futurePosition.getCol()] == SAFE)
                    {legitMoves.add(futurePosition);}
                }
                if (nextPlayer == BLACK) {
                    if (board[futurePosition.getRow()][futurePosition.getCol()] == BLACK_HIT ||
                            board[futurePosition.getRow()][futurePosition.getCol()] == SAFE)
                    {legitMoves.add(futurePosition);}
                }
            }
        return legitMoves;
    }

    /**
     *  Az osztály board változóját írja le 4*3-as táblázatban.
     * @return formalizált string
     */
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

    /**
     *  Két osztályt hasonlít össze.
     * @param o hozzá hasonlított osztály
     * @return igaz, ha a két osztály megeggyezik
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessState state)) return false;
        return Arrays.deepEquals(board, state.board) && nextPlayer == state.nextPlayer;
    }
}
