package maximalistKnight.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import startApp.Position;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class maximalistKnightGameStateTest {

    GameState state = new GameState();

    @BeforeEach
    void setUp() {
        state = new GameState();
    }

    @Test
    void getKnightsPos() {
        Position actual = state.getKnightsPos();
        Position expected = new Position(0,0);
        assertEquals(expected,actual);
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
        assertEquals(0,state.isOver());
    }
}