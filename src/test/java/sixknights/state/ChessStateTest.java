package sixknights.state;

import startApp.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChessStateTest {
    @Test
    void setStartBoard() {
        sixKnightsGameState randomState = new sixKnightsGameState(
                new Position(0,0),
                new Position(2,2),
                new Position(0,2),

                new Position(1,1),
                new Position(2,0),
                new Position(3,1),
                State.BLACK);
        randomState.setStartBoard();
        sixKnightsGameState startPositionManual = new sixKnightsGameState(
                new Position(0,0),
                new Position(0,1),
                new Position(0,2),

                new Position(3,0),
                new Position(3,1),
                new Position(3,2),
                State.WHITE);
        startPositionManual.calculateBoard();
        assertEquals(startPositionManual, randomState);


    }

    @Test
    void goalTest() {
        sixKnightsGameState goalState = new sixKnightsGameState(
                new Position(3,0),
                new Position(3,1),
                new Position(3,2),

                new Position(0,0),
                new Position(0,1),
                new Position(0,2),
                State.BLACK);
        sixKnightsGameState state = new sixKnightsGameState();
        assertFalse(state.goalTest());
        assertTrue(goalState.goalTest());

    }

    @Test
    void movePiece() {
        sixKnightsGameState state = new sixKnightsGameState();
        state.movePiece(new Position(0,0),new Position(2,1));
        sixKnightsGameState expectedState = new sixKnightsGameState(
                new Position(2,1),
                new Position(0,1),
                new Position(0,2),

                new Position(3,0),
                new Position(3,1),
                new Position(3,2),
                State.BLACK);
        expectedState.calculateBoard();
        assertEquals(expectedState,state);

        sixKnightsGameState state1 = new sixKnightsGameState();
        state1.movePiece(new Position(3,0),new Position(1,1));
        sixKnightsGameState expectedState2 = new sixKnightsGameState(
                new Position(0,0),
                new Position(0,1),
                new Position(0,2),

                new Position(1,1),
                new Position(3,1),
                new Position(3,2),
                State.BLACK);
        expectedState2.calculateBoard();
        assertEquals(expectedState2,state1);
    }

    @Test
    void changeNextPlayer() {
        sixKnightsGameState state = new sixKnightsGameState();
        state.changeNextPlayer();
        assertEquals(State.BLACK,state.nextPlayer);
        state.changeNextPlayer();
        assertEquals(State.WHITE,state.nextPlayer);
        state.changeNextPlayer();
        assertEquals(State.BLACK,state.nextPlayer);
    }

    @Test
    void prepBoardForCalculation() {
        sixKnightsGameState expectedState = new sixKnightsGameState();
        for(int row = 0;row < expectedState.rowBorder;row ++){
            for(int col = 0;col < expectedState.colBorder;col++){
                expectedState.board[row][col] = State.SAFE;

            }
        }
        expectedState.board[2][1] = State.BLACK;
        expectedState.board[0][1] = State.BLACK;
        expectedState.board[0][2] = State.BLACK;

        expectedState.board[3][0] = State.WHITE;
        expectedState.board[3][1] = State.WHITE;
        expectedState.board[3][2] = State.WHITE;

        sixKnightsGameState testState = new sixKnightsGameState(
                new Position(2,1),
                new Position(0,1),
                new Position(0,2),

                new Position(3,0),
                new Position(3,1),
                new Position(3,2),
                State.WHITE);
        State[][] asd = testState.prepBoardForCalculation();

        for(int row = 0;row < expectedState.rowBorder;row ++){
            for(int col = 0;col < expectedState.colBorder;col++){
                assertEquals(expectedState.board[row][col],asd[row][col]);

            }
        }
    }

    @Test
    void calculateBoard() {
    }

    @Test
    void calculateBoardHelper() {
    }

    @Test
    void isOnBoard() {
        sixKnightsGameState state = new sixKnightsGameState();

        Position x = new Position(0,0);
        assertTrue(state.isOnBoard(x));

        x = new Position(1,4);
        assertFalse(state.isOnBoard(x));

        x = new Position(4,0);
        assertFalse(state.isOnBoard(x));

        x = new Position(4,4);
        assertFalse(state.isOnBoard(x));

        x = new Position(1,2);
        assertTrue(state.isOnBoard(x));
    }

    @Test
    void allMovesArray() {
        sixKnightsGameState state = new sixKnightsGameState();
        ArrayList<Position> pos = new ArrayList<>();
        pos.add(new Position(2,1));
        pos.add(new Position(1,2));
        assertEquals(pos,state.allMovesArray(0,0));

        pos = new ArrayList<>();
        pos.add(new Position(3,1));
        pos.add(new Position(2,2));
        pos.add(new Position(0,2));
        assertEquals(pos,state.allMovesArray(1,0));
    }

    @Test
    void legalMovesArray() {
        sixKnightsGameState randomState = new sixKnightsGameState(
                new Position(0,0),
                new Position(2,2),
                new Position(0,2),

                new Position(1,1),
                new Position(2,0),
                new Position(3,1),
                State.BLACK);

        ArrayList<Position> pos = new ArrayList<>();
        assertEquals(pos,randomState.legalMovesArray(randomState.board,0,0,randomState.nextPlayer));

        randomState.setStartBoard();
        pos.add(new Position(1,1));
        assertEquals(pos,randomState.legalMovesArray(randomState.board,3,0,randomState.nextPlayer));

    }

    @Test
    void testToString() {
        sixKnightsGameState state = new sixKnightsGameState();
        assertEquals("\nBLACK BLACK BLACK \nDEAD WHITE_HIT DEAD \nDEAD BLACK_HIT DEAD \nWHITE WHITE WHITE \n",state.toString());
    }

    @Test
    void equals(){
        sixKnightsGameState randomState = new sixKnightsGameState(
                new Position(0,0),
                new Position(2,2),
                new Position(0,2),

                new Position(1,1),
                new Position(2,0),
                new Position(3,1),
                State.BLACK);
        sixKnightsGameState randomState2 = new sixKnightsGameState(
                new Position(0,0),
                new Position(2,2),
                new Position(0,2),

                new Position(1,1),
                new Position(2,0),
                new Position(3,1),
                State.BLACK);
        sixKnightsGameState randomState3 = new sixKnightsGameState(
                new Position(0,0),
                new Position(2,2),
                new Position(0,2),

                new Position(1,1),
                new Position(2,0),
                new Position(3,1),
                State.WHITE);
        sixKnightsGameState randomState4 = new sixKnightsGameState(
                new Position(0,0),
                new Position(0,2),
                new Position(0,2),

                new Position(1,1),
                new Position(2,0),
                new Position(3,1),
                State.BLACK);

        assertTrue(randomState2.equals(randomState));
        assertFalse(randomState3.equals(randomState2));
        assertFalse(randomState4.equals(randomState));
    }

    @Test
    void posToString(){
        Position pos = new Position(5,5);
        assertEquals("(5 5)",pos.toString());
    }
}