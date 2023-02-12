package main;

/**
 * This class implements the testSolution() method that walks up the parent 
 * references from the result state to the initial state to make sure that the 
 * solution is valid.
 * 
 * @author  Maksym Turkot
 * @version 10/03/22
 */
public class Validator {
    private int depth;

    /**
     * Constructor method.
     */
    public Validator() {
        this.depth = 0;
    }

    /**
     * Getter for the depth of the solution tree.
     * 
     * @return depth of the solution tree.
     */
    public int getDepth() {
        return this.depth;
    }

    /**
     * Walks up the parent references of the result state to validate a 
     * solution and find the depth of the tree.
     * 
     * @param state result state to walk from.
     * @return initial state of the puzzle.
     */
    public State testSolution(State state) {
        this.depth = 0;
        
        // While state has a parent state, go to that parent.
        while (state.getParent() != null) {
            state = state.getParent();
            depth++;
        }
        return state;
    }
}
