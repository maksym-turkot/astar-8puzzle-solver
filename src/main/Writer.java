package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * This class implements the reading functionality of the program. The method 
 * createFile() creates a new file, overwriting the existing ones with the 
 * same name. Method writePuzzleFile() creates and writes a file containing 
 * initial states of the puzzle. Method writeLogFile() writes program 
 * execution results data to the log.txt file.
 * 
 * @author  Maksym Turkot
 * @version 10/03/22
 */
public class Writer {

    /**
     * Creates a new file with a specified path. If the file already exists, 
     * it is overwritten.
     * 
     * @param  filename name of the file to create.
     * @return the created file reference.
     */
    public File createFile(String filename) {
        try {
            File file = new File(filename);
            
            // Check if a new file was created successfully.
            if (file.createNewFile()) {
              System.out.println("File \"" + filename + "\" created successfully");
            }
            return file;
          } catch (IOException ioe) {
            System.out.println("Error opening file \"" + filename + "\"");
            ioe.printStackTrace();

            return null;
          }
    }

    /**
     * Creates and writes the passed puzzle state set to the file with a 
     * filename reflecting the properties of the puzzle set.
     * 
     * @param puzzles array of puzzles to be written to a file.
     * @param size    of puzzles to be written.
     * @param seed    of the generated puzzles.
     * @param swaps   made to generate each puzzles.
     * @param number  of puzzles generated.
     */
    public void writePuzzleFile(int[][][] puzzles, int size, int seed, int swaps, int number) {
        try {
            File file = this.createFile("data/puzzleSet/puzzleSet-" + size + "-" + seed + "-" + swaps + "-" + number + ".txt");
            FileWriter myWriter = new FileWriter(file.getPath());
            String puzzleSet = "";

            // For each puzzle in the puzzle array.
            for (int[][] puzzle : puzzles) {
                puzzleSet+= Arrays.deepToString(puzzle) + "\n";
            }
            myWriter.write(puzzleSet);

            myWriter.close();

          } catch (IOException ioe) {
            System.out.println("Error writing file \"log\"");
            ioe.printStackTrace();
          }
    }
  
    /**
     * Writes program execution data to the log file, appending to the 
     * existing log file additional.
     * 
     * @param seed        used to generate the puzzles.
     * @param size        of the puzzles.
     * @param swaps       used to generate each puzzle.
     * @param number      of upzzles generated.
     * @param nExpandedH1 avg number of states expanded for H1 heuristic.
     * @param depthH1     avg depth of the solution tree for the H1 heuristic.
     * @param timeH1      avg execution time for the H1 heuristic.
     * @param nExpandedH2 avg number of states expanded for H2 heuristic.
     * @param depthH2     avg depth of the solution tree for the H2 heuristic.
     * @param timeH2      avg execution time for the H2 heuristic.
     */
    public void writeLogFile(int seed, int size, int swaps, int number, 
            double nExpandedH1, double depthH1, double timeH1, double nExpandedH2, 
            double depthH2, double timeH2) {
        try {
            File file = this.createFile("data/log.txt");

            FileWriter myWriter = new FileWriter(file.getPath(), true);

            myWriter.write( "************************"      + "\n" +
                            "size:          " + size        + "\n" +
                            "seed:          " + seed        + "\n" +
                            "swaps:         " + swaps       + "\n" +
                            "number:        " + number      + "\n" +
                            "========================"      + "\n" + 
                            "H1: Number Misplaced"          + "\n" + 
                            "------------------------"      + "\n" + 
                            "expanded:      " + nExpandedH1 + "\n" + 
                            "treeDepth:     " + depthH1     + "\n" +
                            "execTime (ms): " + timeH1      + "\n" +
                            "------------------------"      + "\n" +
                            "H2: Manhattan Distance"        + "\n" + 
                            "------------------------"      + "\n" + 
                            "expanded:      " + nExpandedH2 + "\n" + 
                            "treeDepth:     " + depthH2     + "\n" +
                            "execTime (ms): " + timeH2      + "\n" +
                            "************************"      + "\n" +
                            ""                              + "\n");

            myWriter.close();

            } catch (IOException ioe) {
            System.out.println("Error writing file \"log\"");
            ioe.printStackTrace();
        }
    }
}
