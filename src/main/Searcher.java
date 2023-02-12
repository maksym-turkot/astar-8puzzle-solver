package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * This class searchers the puzzle states for the desired goal state. The 
 * aStarSearch() method runs the search logic; setGoal() method creates a 
 * reference goal state based on the puzzle size; updateFrontier() and adds up 
 * to four children states to the frontier PriorityQueue and computes the 
 * heuristic and pathCost values; expand() method creates children states from 
 * the current state with up to four possible tile moves. Additional helper 
 * methods are present, including copyPuzzle(), findEmpty which finds 
 * coordinates of the empty tile, and moveLeft(), moveRight(), moveUp(), and 
 * moveDown() methods.
 * 
 * @author  Maksym Turkot
 * @version 10/02/22
 */
public class Searcher {
    private Heuristic heuristicFinder;
    private int       stateId;

    /**
     * Constructor method.
     */
    public Searcher() {
        this.heuristicFinder = new Heuristic();
        this.stateId = 0;
    }

    /**
     * It keeps track of the frontier of candidate states as well as a lookup 
     * table of reached states for performance optimization. The state from 
     * the frontier with the smallest evalFunc value is checked if it is a 
     * goal, and if not is expanded, and its children are added to the 
     * frontier. Before expanding a state, its puzzle value is checked against 
     * reached states in the lookup table.
     * 
     * @param  initial     initial state to search.
     * @param  heuristicTp heuristic type to use.
     * @return the result state.
     */
    public State aStarSearch(State initial, String heuristicTp) {
        PriorityQueue<State> frontier = new PriorityQueue<State>(50, 
                                                                 new StateComparator());
        ArrayList<String> reached = new ArrayList<String>();
        int[][] goal = this.setGoal(initial.getSize());
        
        initial.setId(this.stateId++);

        initial.setHeuristic(heuristicFinder.findHeuristic(initial, heuristicTp));

        frontier.add(initial);

        // For each state in the frontier.
        while (!frontier.isEmpty()) {
            State curr = frontier.poll();
            curr.setId(this.stateId++);
            String currId = Arrays.deepToString(curr.getPuzzle());
            
            // Check if current state is the goal state.
            if (Arrays.deepEquals(curr.getPuzzle(), goal)) {
                this.stateId = 0;
                return curr;
            }

            // Check if this state was reached.
            if (!reached.contains(currId)) {
                this.expand(curr);
                this.updateFrontier(curr, frontier, heuristicTp);
            }

            reached.add(currId);
        }
        
        this.stateId = 0;
        return null;
    }

    /**
     * For the puzzle size, creates a goal state to be used by the searching 
     * algorithm.
     * 
     * @param size of the puzzle.
     * @return the goal puzzle state.
     */
    private int[][] setGoal(int size) {
        int[][] puzzle;

        // Check what size goal to create.
        switch(size) {

            // Create 3-puzzle goal state.
            case 2:
                int[][] temp2 = {{0, 1}, {2, 3}};
                puzzle = temp2;
                break;

            // Create 8-puzzle goal state.
            case 3:
                int[][] temp3 = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
                puzzle = temp3;
                break;

            // Create 15-puzzle goal state.
            case 4:
                int[][] temp4 = {{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, 
                                 {12, 13, 14, 15}};
                puzzle = temp4;
                break;
            
            // For undefined size.
            default:
                int[][] temp0 = {{},{}};
                puzzle = temp0;
        }
        return puzzle;
    }

    /**
     * For each child state, this method computes the evaluation function and 
     * adds the child to the frontier.
     * 
     * @param curr        state whose children are considered.
     * @param frontier    frontier of unexpanded states.
     * @param heuristicTp heuristic type used.
     */
    private void updateFrontier(State curr, PriorityQueue<State> frontier, String heuristicTp) {
        State moveL       = curr.getMoveL();
        State moveR       = curr.getMoveR();
        State moveU       = curr.getMoveU();
        State moveD       = curr.getMoveD();
        int   newPathCost = curr.getPathCost() + 1;

        // Check if left move state exists.
        if (moveL != null) {
            // moveL.setId(stateId++);
            moveL.setPathCost(newPathCost);
            moveL.setHeuristic(heuristicFinder.findHeuristic(moveL, heuristicTp));
            moveL.updateEvalFunc();
            frontier.add(moveL);
        }

        // Check if right move state exists.
        if (moveR != null) {
            // moveR.setId(stateId++);
            moveR.setPathCost(newPathCost);
            moveR.setHeuristic(heuristicFinder.findHeuristic(moveR, heuristicTp));
            moveR.updateEvalFunc();
            frontier.add(moveR);
        }

        // Check if top move state exists.
        if (moveU != null) {
            // moveU.setId(stateId++);
            moveU.setPathCost(newPathCost);
            moveU.setHeuristic(heuristicFinder.findHeuristic(moveU, heuristicTp));
            moveU.updateEvalFunc();
            frontier.add(moveU);
        }

        // Check if bottom move state exists.
        if (moveD != null) {
            // moveD.setId(stateId++);
            moveD.setPathCost(newPathCost);
            moveD.setHeuristic(heuristicFinder.findHeuristic(moveD, heuristicTp));
            moveD.updateEvalFunc();
            frontier.add(moveD);
        }
    }

    /**
     * Expands a state by creating a child state for each possible tile move.
     * 
     * @param state to be expanded.
     */
    public void expand(State state) {
        int[][] OriginPuzzle = state.getPuzzle();
        int     size    = state.getSize();

        int[]   emptyRC = findEmpty(OriginPuzzle, size);
        int     r       = emptyRC[0]; // Row
        int     c       = emptyRC[1]; // Col

        int[][] puzzleL = this.copyPuzzle(OriginPuzzle, size);
        int[][] puzzleR = this.copyPuzzle(OriginPuzzle, size);
        int[][] puzzleU = this.copyPuzzle(OriginPuzzle, size);
        int[][] puzzleD = this.copyPuzzle(OriginPuzzle, size);

        // Check if move left is possible.
        if (this.moveLeft (puzzleL, r, c)) {
            state.setMoveL(new State(puzzleL, state));
        }
        
        // Check if move right is possible.
        if (this.moveRight(puzzleR, size, r, c)) {
            state.setMoveR(new State(puzzleR, state));
        }
        
        // Check if move top is possible.
        if (this.moveUp   (puzzleU, r, c)) {
            state.setMoveU(new State(puzzleU, state));
        }

        // Check if move bottom is possible.
        if (this.moveDown (puzzleD, size, r, c)) {
            state.setMoveD(new State(puzzleD, state));
        }
    }

    /**
     * Creates a copy of the puzzle array.
     * 
     * @param  puzzle to copy.
     * @param  size   of the puzzle.
     * @return copy of a puzzle.
     */
    private int[][] copyPuzzle(int[][] puzzle, int size) {
        int[][] puzzleCopy = new int[size][size];

        // For each row in the puzzle, copy the column.
        for (int row = 0; row < size; row++) {
            puzzleCopy[row] = puzzle[row].clone();
        }

        return puzzleCopy;
    }

    /**
     * Finds the coordinates of the empty tile.
     * 
     * @param  puzzle to analyze.
     * @param  size   of the puzzle.
     * @return coordinates of the empty tile.
     */
    public int[] findEmpty(int[][] puzzle, int size) {
        int[] coords = {-1, -1};

        // For each row of the puzzle.
        for (int row = 0; row < size; row++) {

            // For each column of the puzzle.
            for (int col = 0; col < size; col++) {

                // Check if empty tile was found.
                if (puzzle[row][col] == 0) {
                    coords[0] = row;
                    coords[1] = col;

                    return coords;
                }
            }
        }
        return coords;
    }

    /**
     * Creates a new puzzle state by moving the left tile.
     * 
     * @param  puzzle to analyze.
     * @param  r      row number.
     * @param  c      column number.
     * @return true if move was successful.
     */
    private boolean moveLeft(int[][] puzzle, int r, int c) {

        // Check if move is possible.
        if (c - 1 >= 0) {
            puzzle[r][c] = puzzle[r][c - 1];
            puzzle[r][c - 1] = 0;

            return true;
        } 
        return false;
    }

    /**
     * Creates a new puzzle state by moving the right tile.
     * 
     * @param  puzzle to analyze.
     * @param  size   size of the puzzle.
     * @param  r      row number.
     * @param  c      column number.
     * @return true if move was successful.
     */
    private boolean moveRight(int[][] puzzle, int size, int r, int c) {

        // Check if move is possible.
        if (c + 1 < size) {
            puzzle[r][c] = puzzle[r][c + 1];
            puzzle[r][c + 1] = 0;

            return true;
        }
        return false;
    }

    /**
     * Creates a new puzzle state by moving the top tile.
     * 
     * @param  puzzle to analyze.
     * @param  r      row number.
     * @param  c      column number.
     * @return true if move was successful.
     */
    private boolean moveUp(int[][] puzzle, int r, int c) {

        // Check if move is possible.
        if (r - 1 >= 0) {
            puzzle[r][c] = puzzle[r - 1][c];
            puzzle[r - 1][c] = 0;

            return true;
        }
        return false;
    }

    /**
     * Creates a new puzzle state by moving the bottom tile.
     * 
     * @param  puzzle to analyze.
     * @param  size   size of the puzzle.
     * @param  r      row number.
     * @param  c      column number.
     * @return true if move was successful.
     */
    private boolean moveDown(int[][] puzzle, int size, int r, int c) {

        // Check if move is possible.
        if (r + 1 < size) {
            puzzle[r][c] = puzzle[r + 1][c];
            puzzle[r + 1][c] = 0;

            return true;
        }
        return false;
    }

    /**
     * Implements the comparator for the State Priority Queue.
     * 
     * @author  Max Turkot
     * @version 10/02/22
     */
    private class StateComparator implements Comparator<State> {

        /**
         * Compares the evaluation functions of two states.
         */
        public int compare(State s1, State s2) {
            return s1.getEvalFunc() - s2.getEvalFunc();
        }
    }
}
