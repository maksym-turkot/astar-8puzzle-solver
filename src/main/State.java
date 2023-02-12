package main;

/**
 * This class constructs state objects that store information about the puzzle 
 * state. It includes data fields as well as getters and setters for those 
 * fields.
 * 
 * @author  Maksym Turkot
 * @version 10/01/2022
 */
public class State {
    private int id;
    private int size;
    private int pathCost;
    private int heuristic;
    private int evalFunc;
    private int[][] puzzle;

    private State moveL;
    private State moveR;
    private State moveU;
    private State moveD;

    private State parent;

    /**
     * Constructor method.
     * 
     * @param puzzle of the state.
     * @param parent from which the state was derived.
     */
    public State(int[][] puzzle, State parent) {
        this.id = 0;
        this.size = puzzle.length;
        this.pathCost = 0;
        this.heuristic = 0;
        this.evalFunc = this.heuristic + this.pathCost;
        this.puzzle = puzzle;
        this.moveL  = null;
        this.moveR  = null;
        this.moveU  = null;
        this.moveD  = null;
        this.parent = parent;
    }

    /**
     * Getter for puzzle ID.
     * 
     * @return puzzle id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Setter for puzzle ID.
     * 
     * @param id new puzzle id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the size of the puzzle.
     * 
     * @return size of the puzzle.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Getter for the cost to get from the initial state to the current state.
     * 
     * @param pathCost cost of the path from the initial state to this state.
     */
    public int getPathCost() {
        return this.pathCost;
    }

    /**
     * Setter for the cost to get from the initial state to the current state.
     * 
     * @param pathCost new cost from the initial state to this state.
     */
    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    /**
     * Getter for the heuristic value.
     * 
     * @return heuristic value.
     */
    public int getHeuristic() {
        return this.heuristic;
    }

    /**
     * Setter for the heuristic value.
     * @param heuristic new heuristic value.
     */
    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    /**
     * Getter for the evaluation function, sum of the pathCost and heuristic.
     * 
     * @return evaluation function.
     */
    public int getEvalFunc() {
        return this.evalFunc;
    }

    /**
     * Updates the evalFunc with new pathCost and heuristic values.
     */
    public void updateEvalFunc() {
        this.evalFunc = this.pathCost + this.heuristic;
    }

    /**
     * Getter for the puzzle array.
     * 
     * @return puzzle array.
     */
    public int[][] getPuzzle() {
        return this.puzzle;
    }

    /**
     * Getter for the derived left move state.
     * 
     * @return derived left move state.
     */
    public State getMoveL() {
        return this.moveL;
    }

    /**
     * Setter for the derived left move state.
     * 
     * @return new derived left move state.
     */
    public void setMoveL(State moveL) {
        this.moveL = moveL;
    }

    /** 
     * Getter for the derived right move state.
     * 
     * @return derived right move state.
     */
    public State getMoveR() {
        return this.moveR;
    }

    /** 
     * Setter for the derived right move state.
     * 
     * @return new derived right move state.
     */
    public void setMoveR(State moveR) {
        this.moveR = moveR;
    }

    /**
     * Getter for the derived top move state.
     * 
     * @return derived top move state.
     */
    public State getMoveU() {
        return this.moveU;
    }

    /** 
     * Setter for the derived right move state.
     * 
     * @return new derived right move state.
     */
    public void setMoveU(State moveU) {
        this.moveU = moveU;
    }

    /**
     * Getter for the derived bottom move state.
     * 
     * @return derived bottom move state.
     */
    public State getMoveD() {
        return this.moveD;
    }
    
    /**
     * Setter for the derived bottom move state.
     * 
     * @return new derived bottom move state.
     */
    public void setMoveD(State moveD) {
        this.moveD = moveD;
    }

    /**
     * Getter for the parent state.
     * 
     * @return parent state.
     */
    public State getParent() {
        return this.parent;
    }
}