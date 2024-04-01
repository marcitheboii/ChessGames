package eightBishops.state;

import games.eightBishops.state.GameState;
import games.eightBishops.state.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import position.Position;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class eightBishopsGameStateTest {

    GameState state = new GameState();

    @BeforeEach
    void setUp() {
        state = new GameState();
    }

    @Test
    void SetStepsForBishop() {
        ArrayList<Position> actual = state.setStepsForBishop(new Position(0,0));
        Position pos = new Position(1,1);

        assertEquals(pos.getCol(),actual.get(0).getCol());
        assertEquals(pos.getRow(),actual.get(0).getRow());
    }

    @Test
    void MoveBishop() {

        state.moveBishop(new Position(0,0),new Position(1,1));

        assertSame(state.board[1][1], State.BLACK);
        assertNotSame(state.board[0][0], State.BLACK);
    }

    @Test
    void GetWhiteBishopsPos() {
        ArrayList<Position> actual = state.getWhiteBishopsPos();
        ArrayList<Position> expected = new ArrayList<>();
        expected.add(new Position(4,0));
        expected.add(new Position(4,1));
        expected.add(new Position(4,2));
        expected.add(new Position(4,3));

        for (int i = 0; i<actual.size();i++){
            assertEquals(expected.get(i).getRow(),actual.get(i).getRow());
            assertEquals(expected.get(i).getCol(),actual.get(i).getCol());
        }
    }

    @Test
    void GoalTest() {
        state.moveBishop(new Position(0,0),new Position(3,0));
        state.moveBishop(new Position(0,1),new Position(3,1));
        state.moveBishop(new Position(0,2),new Position(3,2));
        state.moveBishop(new Position(0,3),new Position(3,3));

        assertFalse(state.goalTest());

        state.moveBishop(new Position(4,0),new Position(0,0));
        state.moveBishop(new Position(4,1),new Position(0,1));
        state.moveBishop(new Position(4,2),new Position(0,2));
        state.moveBishop(new Position(4,3),new Position(0,3));

        assertFalse(state.goalTest());

        state.moveBishop(new Position(3,0),new Position(4,0));
        state.moveBishop(new Position(3,1),new Position(4,1));
        state.moveBishop(new Position(3,2),new Position(4,2));
        state.moveBishop(new Position(3,3),new Position(4,3));

        assertTrue(state.goalTest());
    }

    @Test
    void GetBlackBishopsPos() {
        ArrayList<Position> actual = state.getBlackBishopsPos();
        ArrayList<Position> expected = new ArrayList<>();
        expected.add(new Position(0,0));
        expected.add(new Position(0,1));
        expected.add(new Position(0,2));
        expected.add(new Position(0,3));

        for (int i = 0; i<actual.size();i++){
            assertEquals(expected.get(i).getRow(),actual.get(i).getRow());
            assertEquals(expected.get(i).getCol(),actual.get(i).getCol());
        }
    }
}