package main;

import java.util.Random;

/**
 * Includes methods makePuzzle() to generate a random puzzle, makeGoal() to 
 * generate the goal state, shuffle() to shuffle the goal state to a solvable 
 * initial state, and moveLeft(), moveRight, moveUp, and moveDown methods used 
 * for shuffling.
 * 
 * @author  Maksym Turkot
 * @version 10/02/22
 */
public class PuzzleGenerator {
    private Random rand;

    /**
     * Constructor method.
     * 
     * @param seed seed for random number generation.
     */
    public PuzzleGenerator(int seed) {
        this.rand = new Random(seed);
    }

    /**
     * Initializes the goal state and runs the shuffle on it to produce the 
     * initial state.
     * 
     * @param  size  of the puzzle.
     * @param  swaps number to perform on the goal state.
     * @return initial state.
     */
    public int[][] makePuzzle(int size, int swaps) {
        int[][] puzzle = this.makeGoal(size);

        this.shuffle(puzzle, size, swaps);

        return puzzle;
    }

    /**
     * Generates a goal state for passed puzzle size.
     * 
     * @param  size of the puzzle.
     * @return the goal state.
     */
    private int[][] makeGoal(int size) {
        int[][] puzzle;

        // Check puzzle size.
        switch(size) {

            // For 3-puzzle.
            case 2:
                int[][] temp2 = {{0, 1}, {2, 3}};
                puzzle        = temp2;
                break;

            // For 8-puzzle.
            case 3:
                int[][] temp3 = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
                puzzle        = temp3;
                break;

            // For 15-puzzle.
            case 4:
                int[][] temp4 = {{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, 
                                 {12, 13, 14, 15}};
                puzzle        = temp4;
                break;
            
            // For undefined size.
            default:
                int[][] temp0 = {{},{}};
                puzzle        = temp0;
        }
        return puzzle;
    }

    /**
     * Generates a stream of random integers (ranging from 0 to 3) used for 
     * shuffling. For each integer, either left, right, top, or bottom tile is 
     * moved, generating the next state, provided such move is possible.
     * 
     * @param puzzle to be shuffled.
     * @param size   of the puzzle.
     * @param swaps  number to perform on the puzzle.
     */
    private void shuffle(int[][] puzzle, int size, int swaps) {
        int[] rnd = rand.ints(swaps, 0, 5).toArray();
        int   r   = 0;
        int   c   = 0;

        // For each random number in the stream, move a tile.
        for (int i = 0; i < rnd.length; i++) {

            // Check the random number.
            switch(rnd[i]) {

                // For 0 move the left tile.
                case 0:

                    // Check if move was successful.
                    if (this.moveLeft(puzzle, r, c)) {
                        c--;
                    }
                    break;

                // For 1 move the right tile.
                case 1:

                    // Check if move was successful.
                    if (this.moveRight(puzzle, size, r, c)) {
                        c++;
                    }
                    break;

                // For 2 move the top tile.
                case 2:
                    
                    // Check if move was successful.
                    if (this.moveUp(puzzle, r, c)) {
                        r--;
                    }
                    break;

                // For 3 move the bottom tile.
                case 3:

                    // Check if move was successful.
                    if (this.moveDown(puzzle, size, r, c)) {
                        r++;
                    }
                    break;
            }
        }
    }

    /**
     * Moves the left tile in the current state, provided such move is 
     * possible.
     * 
     * @param  puzzle to move.
     * @param  r      row index of empty space.
     * @param  c      column index of empty space.
     * @return true if move was successfull.
     */
    private boolean moveLeft(int[][] puzzle, int r, int c) {

        // Check if move is possible.
        if (c - 1 >= 0) {
            puzzle[r][c]     = puzzle[r][c - 1];
            puzzle[r][c - 1] = 0;

            return true;
        } 
        return false;
    }

    /**
     * Moves the right tile in the current state, provided such move is 
     * possible.
     * 
     * @param  puzzle to move.
     * @param  size   of the puzzle.
     * @param  r      row index of empty space.
     * @param  c      column index of empty space.
     * @return true if move was successfull.
     */
    private boolean moveRight(int[][] puzzle, int size, int r, int c) {

        // Check if move is possible.
        if (c + 1 < size) {
            puzzle[r][c]     = puzzle[r][c + 1];
            puzzle[r][c + 1] = 0;

            return true;
        }
        return false;
    }

    /**
     * Moves the top tile in the current state, provided such move is 
     * possible.
     * 
     * @param  puzzle to move.
     * @param  r      row index of empty space.
     * @param  c      column index of empty space.
     * @return true if move was successfull.
     */
    private boolean moveUp(int[][] puzzle, int r, int c) {

        // Check if move is possible.
        if (r - 1 >= 0) {
            puzzle[r][c]     = puzzle[r - 1][c];
            puzzle[r - 1][c] = 0;

            return true;
        }
        return false;
    }

    /**
     * Moves the bottom tile in the current state, provided such move is 
     * possible.
     * 
     * @param  puzzle to move.
     * @param  size   of the puzzle.
     * @param  r      row index of empty space.
     * @param  c      column index of empty space.
     * @return true if move was successfull.
     */
    private boolean moveDown(int[][] puzzle, int size, int r, int c) {

        // Check if move is possible.
        if (r + 1 < size) {
            puzzle[r][c]    = puzzle[r + 1][c];
            puzzle[r + 1][c] = 0;

            return true;
        }
        return false;
    }
}