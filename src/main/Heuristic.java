package main;

/**
 * This class implements the logic of heuristic calculation. The 
 * findHeuristic() processes the heuristic string and activates the correct 
 * heuristic construction method, findNumberMisplaced() or findManhattanDist(). 
 * The former computes the number of misplaced tiles in the current state, 
 * while the latter finds the distance along row and columns of the puzzle 
 * from each tile’s current position to the goal position. A helper method 
 * findCorrPos() is also present.
 * 
 * @author Maksym Turkot
 * @version 10/01/22
 */
public class Heuristic {

    /**
     * Accepts the string indicating the heuristic, and based on it activates 
     * the correct heuristic construction function.
     * 
     * @param  state       to find heuristic for.
     * @param  heuristicTp type of the heuristic to find.
     * @return heuristic value.
     */
    public int findHeuristic(State state, String heuristicTp) {

        // Check and compute respective heuristic value.
        if (heuristicTp.equals("nMisplaced")) {
            return this.findNumberMisplaced(state);
        } else if (heuristicTp.equals("manhattanDist")) {
            return this.findManhattanDist(state);
        }
        return -1;
    }

    /**
     * Computes the number of tiles misplaced from the goal state by comparing 
     * value of each cell to the incremented counter.
     * 
     * @param  state to compute heuristic for.
     * @return heuristic value.
     */
    public int findNumberMisplaced(State state) {
        int[][] puzzle = state.getPuzzle();
        int size = state.getSize();
        int numberMisplaced = 0;
        int index = 0;

        // For each row of the puzzle.
        for (int row = 0; row < size; row++) {

            // For each column of the puzzle.
            for (int col = 0; col < size; col++) {

                // Check if tile is misplaced.
                if (puzzle[row][col] != index) {
                    numberMisplaced++;
                }
                index++;
            }
        }
        return numberMisplaced;
    }

    /**
     * Computes the Manhattan distance. For each tile, it compares its value 
     * to the expected correct value. If they are different, it scans the 
     * puzzle for the correct position of the tile, and calculates the 
     * distance between the expected and current tiles.
     * 
     * @param  state to compute heuristic for.
     * @return heuristic value.
     */
    public int findManhattanDist(State state) {
        int[][] puzzle = state.getPuzzle();
        int size = state.getSize();
        int distance = 0;
        
        int index = 0;

        // For each row of the puzzle.
        for (int row = 0; row < size; row++) {

            // For each column of the puzzle.
            for (int col = 0; col < size; col++) {

                // Check if the tile is misplaced.
                if (puzzle[row][col] != index) {
                    int[] corrPosRC = this.findCorrPos(puzzle, size, index);
                    distance = distance + Math.abs(corrPosRC[0] - row)
                                          + Math.abs(corrPosRC[1] - col);
                }
                index++;
            }
        }
        return distance;
    }

    /**
     * Finds the coordinates of the correct position of a puzzle tile.
     * 
     * @param  puzzle puzzle to analyze.
     * @param  size   of the puzzle.
     * @param  index  value to find correct position for.
     * @return coordinates of the correct position for index value.
     */
    private int[] findCorrPos(int[][] puzzle, int size, int index) {
        int[] coords = {-1, -1};

        // For each row of the puzzle.
        for (int row = 0; row < size; row++) {

            // For each column of the puzzle.
            for (int col = 0; col < size; col++) {

                // Check if tile matches the desired value.
                if (puzzle[row][col] == index) {
                    coords[0] = row;
                    coords[1] = col;

                    return coords;
                }
            }
        }
        return coords;
    }
}
