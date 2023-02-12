package test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Searcher;
import main.State;

public class SearcherTest {
    Searcher searcher = new Searcher();
    State state1, state2, state3;

    public SearcherTest() {

    }

    @BeforeEach
    public void setUp() {
        int[][] puzzle1 = {{2, 3, 5}, {1, 8, 4}, {7, 6, 0}};
        int[][] puzzle2 = {{1, 2, 3}, {0, 4, 5}, {7, 8, 6}};
        int[][] puzzle3 = {{7, 4, 3}, {1, 0, 5}, {8, 2, 6}};
        state1 = new State(puzzle1, null);
        state2 = new State(puzzle2, null);
        state3 = new State(puzzle3, null);
    }

    public void aStarSearchTest() {
        int[][] goal = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        State result1 = searcher.aStarSearch(state1, "nMisplaced");
        State result2 = searcher.aStarSearch(state2, "nMisplaced");
        State result3 = searcher.aStarSearch(state3, "nMisplaced");

        assertTrue(Arrays.deepEquals(result1.getPuzzle(), goal));
        assertTrue(Arrays.deepEquals(result2.getPuzzle(), goal));
        assertTrue(Arrays.deepEquals(result3.getPuzzle(), goal));
    }

    @Test
    public void expandTest() {
        searcher.expand(state1);

        int[][] pMoveL1 = {{2, 3, 5}, {1, 8, 4}, {7, 0, 6}};
        int[][] pMoveU1 = {{2, 3, 5}, {1, 8, 0}, {7, 6, 4}};

        assertTrue(Arrays.deepEquals(state1.getMoveL().getPuzzle(), pMoveL1));
        assertNull(state1.getMoveR());
        assertTrue(Arrays.deepEquals(state1.getMoveU().getPuzzle(), pMoveU1));
        assertNull(state1.getMoveD());

        searcher.expand(state2);

        int[][] pMoveR2 = {{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};
        int[][] pMoveU2 = {{0, 2, 3}, {1, 4, 5}, {7, 8, 6}};
        int[][] pMoveD2 = {{1, 2, 3}, {7, 4, 5}, {0, 8, 6}};

        assertNull(state2.getMoveL());
        assertTrue(Arrays.deepEquals(state2.getMoveR().getPuzzle(), pMoveR2));
        assertTrue(Arrays.deepEquals(state2.getMoveU().getPuzzle(), pMoveU2));
        assertTrue(Arrays.deepEquals(state2.getMoveD().getPuzzle(), pMoveD2));

        searcher.expand(state3);

        int[][] pMoveL3 = {{7, 4, 3}, {0, 1, 5}, {8, 2, 6}};
        int[][] pMoveR3 = {{7, 4, 3}, {1, 5, 0}, {8, 2, 6}};
        int[][] pMoveU3 = {{7, 0, 3}, {1, 4, 5}, {8, 2, 6}};
        int[][] pMoveD3 = {{7, 4, 3}, {1, 2, 5}, {8, 0, 6}};

        assertTrue(Arrays.deepEquals(state3.getMoveL().getPuzzle(), pMoveL3));
        assertTrue(Arrays.deepEquals(state3.getMoveR().getPuzzle(), pMoveR3));
        assertTrue(Arrays.deepEquals(state3.getMoveU().getPuzzle(), pMoveU3));
        assertTrue(Arrays.deepEquals(state3.getMoveD().getPuzzle(), pMoveD3));
    }

    @Test
    public void findEmptyTest() {
        int[] emptyXY1 = {-1, -1};
        int[] emptyXY2 = {-1, -1};

        int[] emptyXY1Exp = {2, 2};
        int[] emptyXY2Exp = {1, 0};

        emptyXY1 = searcher.findEmpty(state1.getPuzzle(), 3);
        emptyXY2 = searcher.findEmpty(state2.getPuzzle(), 3);

        assertTrue(Arrays.equals(emptyXY1, emptyXY1Exp));
        assertTrue(Arrays.equals(emptyXY2, emptyXY2Exp));
    }

    @AfterEach
    public void tearDown() {
        state1 = null;
        state2 = null;
    }

    
}
