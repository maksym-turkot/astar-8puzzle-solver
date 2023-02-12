package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Heuristic;
import main.State;

public class HeuristicTest {
    Heuristic heuristicFinder;
    State state1, state2, state3, state4, state5, state6;

    public HeuristicTest() {
        heuristicFinder = new Heuristic();
    }

    @BeforeEach
    public void setUp() {
        // Random cases.
        int[][] puzzle1 = {{2, 3, 5}, {1, 8, 4}, {7, 6, 0}};
        int[][] puzzle2 = {{3, 0, 8}, {6, 7, 5}, {1, 4, 2}};

        // Custom cases.
        int[][] puzzle3 = {{3, 0, 8}, {4, 7, 5}, {1, 6, 2}};
        int[][] puzzle4 = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        int[][] puzzle5 = {{0, 1, 2}, {4, 5, 3}, {6, 7, 8}};
        int[][] puzzle6 = {{1, 2, 0}, {3, 4, 5}, {7, 8, 6}};

        state1 = new State(puzzle1, null);
        state2 = new State(puzzle2, null);
        state3 = new State(puzzle3, null);
        state4 = new State(puzzle4, null);
        state5 = new State(puzzle5, null);
        state6 = new State(puzzle6, null);
    }

    @Test
    public void findNumberMisplacedTest() {
        assertEquals(heuristicFinder.findNumberMisplaced(state1), 9);
        assertEquals(heuristicFinder.findNumberMisplaced(state2), 8);
        assertEquals(heuristicFinder.findNumberMisplaced(state3), 8);
        assertEquals(heuristicFinder.findNumberMisplaced(state4), 0);
        assertEquals(heuristicFinder.findNumberMisplaced(state5), 3);
        assertEquals(heuristicFinder.findNumberMisplaced(state6), 6);
    }

    @Test
    public void findManhattanDistTest() {
        assertEquals(heuristicFinder.findManhattanDist(state1), 16);
        assertEquals(heuristicFinder.findManhattanDist(state2), 12);
        assertEquals(heuristicFinder.findManhattanDist(state3), 12);
        assertEquals(heuristicFinder.findManhattanDist(state4), 0);
        assertEquals(heuristicFinder.findManhattanDist(state5), 4);
        assertEquals(heuristicFinder.findManhattanDist(state6), 8);
    }

    @AfterEach
    public void tearDown() {
        state1 = null;
        state2 = null;
        state3 = null;
        state4 = null;
        state5 = null;
        state6 = null;
    }
}
