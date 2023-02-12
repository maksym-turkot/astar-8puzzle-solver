package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class implements the reading features of the project. Configuration 
 * files fully determine how the program is run. Class implements the readDir() 
 * method to read all files in a specified directory. The readFile() method 
 * reads a specified file, and calls the readPuzzleConfigToken() or 
 * stringToPuzzle() to extract data from the file into the system.
 * 
 * @author  Maksym Turkot
 * @version 10/03/22
 */
public class Reader {
    private int size, seed, swaps, number;
    private int[][][] puzzles;

    /**
     * Constructor method.
     */
    public Reader() {
        this.size   = 0;
        this.seed   = 0;
        this.swaps  = 0;
        this.number = 0;
    }

    /**
     * Getter for the puzzle sie.
     * 
     * @return puzzle size.
     */
    public int getPSize() {
        return this.size;
    }

    /**
     * Getter for the generator seed.
     * 
     * @return generator seed.
     */
    public int getPSeed() {
        return this.seed;
    }

    /**
     * Getter of number of swaps.
     * 
     * @return number of swaps.
     */
    public int getPSwaps() {
        return this.swaps;
    }

    /**
     * Getter of number of states generated.
     * @return number of states generated.
     */
    public int getPNumber() {
        return this.number;
    }

    /**
     * Getter for the array of initial puzzles.
     * 
     * @return array of initial puzzles.
     */
    public int[][][] getPuzzles() {
        return this.puzzles;
    }

    /**
     * Reads the specified file by calling a necessary helper function to 
     * store the information into the system.
     * 
     * @param filename name of the file to read.
     * @param size     of the puzzle, applied for puzzleSet reading.
     * @param number   of generated puzzles, applied for puzzleSet reading.
     */
    public void readFile(String filename, int size, int number) {
        try {
        File file = new File(filename);
        int index = 0;
        puzzles = new int[number][size][size];

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens;
                
            if (filename.contains("puzzleConfig")) {
                tokens = line.split(": ");
                this.readPuzzleConfigToken(tokens, filename);
            } else if (filename.contains("puzzleSet")) {
                puzzles[index++] = this.stringToPuzzle(line, size);
            }
        }
        scanner.close();
        } catch(FileNotFoundException fnfe) {
            System.out.println("Did not find file \"" + filename + "\"");
            fnfe.printStackTrace();
        }
    }
    
    /**
     * Splits the read string into tokens and stores each one as a variable to 
     * the system.
     * 
     * @param tokens   array of string tokens to save as date.
     * @param filename name of the file to read.
     */
    private void readPuzzleConfigToken(String[] tokens, String filename) {
        switch(tokens[0]) {
            case "size":
                this.size   = Integer.parseInt(tokens[1]);
                break;
            case "seed":
                this.seed   = Integer.parseInt(tokens[1]);
                break;
            case "swaps":
                this.swaps  = Integer.parseInt(tokens[1]);
                break;
            case "number":
                this.number = Integer.parseInt(tokens[1]);
                break;
            default:
                System.out.println("Unknown token in file \"" + filename + "\"");
        }
    }

    /**
     * Converts a string specifying the puzzle state to an array and stores it 
     * to the array of states.
     * 
     * @param line containing the puzzle state to be read.
     * @param size of the puzzle.
     * @return the puzzle array.
     */
    public int[][] stringToPuzzle(String line, int size) {
        line = line.replace("[", ""); // Replace all [ to "".
        line = line.substring(0, line.length() - 2); // Ignore last two ]].
        String[] lineSplit=line.split("],"); // Separate all by "],".

        // Declare two-dimensional matrix for input.
        int[][] puzzle = new int[lineSplit.length][lineSplit.length];

        // For each row of the puzzle.
        for(int i=0; i < lineSplit.length; i++){

            // Ignore all extra space in string lineSplit[i].
            lineSplit[i] = lineSplit[i].trim(); 
            
            // Separate integers by ", ".
            String[] singleInt = lineSplit[i].split(", "); 

            // For each column of the puzzle.
             for(int j=0; j < singleInt.length; j++){
                puzzle[i][j] = Integer.valueOf(singleInt[j]); // Add single values.
            }
        }
        return puzzle;
    }
}