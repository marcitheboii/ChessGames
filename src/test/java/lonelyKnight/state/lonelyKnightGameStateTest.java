package lonelyKnight.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import startApp.Position;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class lonelyKnightGameStateTest {

    GameState state = new GameState();

    @BeforeEach
    void setUp() {
        state = new GameState();
    }

    @Test
    void getKnightsPos() {
        Position expected = new Position(0,0);
        assertEquals(expected,state.getKnightsPos());
    }

    @Test
    void allMovesArray() {
        ArrayList<Position> actual = state.allMovesArray();
        ArrayList<Position> expected = new ArrayList<>();
        expected.add(new Position(2,1));
        expected.add(new Position(1,2));

        for (int i = 0; i<actual.size(); i++){
            assertEquals(expected.get(i).getCol(),actual.get(i).getCol());
            assertEquals(expected.get(i).getRow(),actual.get(i).getRow());
        }
    }

    @Test
    void moveKnight() {
        state.moveKnight(new Position(1,2));
        assertNotSame(state.board[0][0], State.KNIGHT);
        assertSame(state.board[1][2], State.KNIGHT);
    }

    @Test
    void isOver() {
        assertFalse(state.isOver());
        state.moveKnight(new Position(2,1));
        state.moveKnight(new Position(4,2));
        state.moveKnight(new Position(6,3));
        state.moveKnight(new Position(7,5));
        state.moveKnight(new Position(5,6));
        state.moveKnight(new Position(7,7));
        assertTrue(state.isOver());

    }
}