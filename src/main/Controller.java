package main;

import java.io.File;
import java.util.Arrays;

/**
 * Contains the main() method that starts the program execution, as 
 * well as two methods that split the program into two modules: 
 * makePuzzleSets() that generates sets of random initial states, 
 * and runSearch() that executes the search, validates the solution, 
 * and outputs the result of the program. A helper method 
 * splitString() is also present.
 * 
 * @author  Maksym Turkot
 * @version 10/04/2022
 */
public class Controller {

    /**
     * Creates the reader and writer objects and calls the 
     * makePuzzleSets() and runSearch() methods.
     * 
     * @param args standard String array of arguments.
     * @throws Exception general exception thrown by the program.
     */
    public static void main(String[] args) throws Exception {
        Reader reader = new Reader();
        Writer writer = new Writer();

        makePuzzleSets(reader, writer);

        System.out.println("progress: ");

        runSearch(reader, writer);

        System.out.println("");
    }

    /**
     * Reads all of the puzzle configuration files stored in the 
     * project1/data/puzzleConfig directory, calls puzzleGenerator to create 
     * the random initial states, and writes these states as strings to a file 
     * in the project1/data/puzzleSet directory.
     * 
     * @param reader used to read files.
     * @param writer used to write to files.
     */
    private static void makePuzzleSets(Reader reader, Writer writer) {
        File puzzleConfigs = new File("data/puzzleConfig");

        // Loop throug puzzleConfig files in the data/puzzleConfig dir.
        for (File puzzleConfig : puzzleConfigs.listFiles()) {
            PuzzleGenerator puzzleGenerator;

            // Exclude automatically generated .DS_Store file.
            if (!puzzleConfig.getPath().contains(".DS_Store")) {
                reader.readFile(puzzleConfig.getPath(), 0, 0);
                puzzleGenerator = new PuzzleGenerator(reader.getPSeed());

                int size   = reader.getPSize();
                int seed   = reader.getPSeed();
                int swaps  = reader.getPSwaps();
                int number = reader.getPNumber();

                int [][][] puzzles = new int[number][size][size];

                // Generate specified number of random initial states.
                for (int i = 0; i < number; i++) {
                    puzzles[i] = puzzleGenerator.makePuzzle(size, swaps);
                }

                writer.writePuzzleFile(puzzles, size, seed, swaps, number);
            }
        }
    }

    /**
     * Reads all of the puzzle set files stored in the 
     * project1/data/puzzleConfig directory, runs A* search using each 
     * heuristic, and writes the aggregated results to the 
     * project1/data/log.txt file.
     * 
     * @param reader used to read files.
     * @param writer used to write to files.
     */
    private static void runSearch(Reader reader, Writer writer) {
        File      puzzleSets = new File("data/puzzleSet");
        Searcher  searcher   = new Searcher();
        Validator validator  = new Validator();
        int[][][] puzzles;

        // Loop throug puzzleSet files in the data/puzzleSet dir.
        for (File puzzleSet : puzzleSets.listFiles()) {

            // Exclude automatically generated .DS_Store file.
            if (!puzzleSet.getPath().contains(".DS_Store")) {
                System.out.print(puzzleSet.getName() + ": ");

                int[]  info              = splitString(puzzleSet.getName());
                int    size              = info[0];
                int    seed              = info[1];
                int    swaps             = info[2];
                int    number            = info[3];
                int    numExpandedSumH1  = 0;
                int    numExpandedSumH2  = 0;
                int    depthSumH1        = 0;
                int    depthSumH2        = 0;
                long   execTimeSumH1     = 0;
                long   execTimeSumH2     = 0;
                double averageExpandedH1 = 0.0;
                double averageExpandedH2 = 0.0;
                double averageDepthH1    = 0.0;
                double averageDepthH2    = 0.0;
                double avgExecTimeH1     = 0.0;
                double avgExecTimeH2     = 0.0;
                
                reader.readFile(puzzleSet.getPath(), size, number);
                puzzles = reader.getPuzzles();

                // Solve each puzzle in the puzzleSet file using search.
                for (int[][] puzzle : puzzles) {
                    State initial       = new State(puzzle, null);
                    int   numExpandedH1 = 0;
                    int   numExpandedH2 = 0;
                    int   depthH1       = 0;
                    int   depthH2       = 0;
                    long  execTimeH1    = 0;
                    long  execTimeH2    = 0;
                    long  startTime     = 0;
                    long  stopTime      = 0;

                    // Search using H1.
                    startTime      = System.nanoTime();
                    State resultH1 = searcher.aStarSearch(initial, "nMisplaced");
                    stopTime       = System.nanoTime();
                    execTimeH1     = stopTime - startTime;

                    State originH1 = validator.testSolution(resultH1);
                    numExpandedH1  = resultH1.getId();
                    depthH1        = validator.getDepth();

                    // Validate the solution.
                    if (!Arrays.deepEquals(initial.getPuzzle(), originH1.getPuzzle()))
                        System.out.println("Solution invalid.");

                    // Search using H2.
                    startTime      = System.nanoTime();
                    State resultH2 = searcher.aStarSearch(initial, "manhattanDist");
                    stopTime       = System.nanoTime();
                    execTimeH2     = stopTime - startTime;

                    State originH2 = validator.testSolution(resultH2);
                    numExpandedH2  = resultH2.getId();
                    depthH2        = validator.getDepth();

                    // Validate the solution.
                    if (!Arrays.deepEquals(initial.getPuzzle(), originH2.getPuzzle()))
                        System.out.println("Solution invalid.");

                    System.out.print("#");
                    
                    // Add to the aggregate statistics.
                    numExpandedSumH1 += numExpandedH1;
                    numExpandedSumH2 += numExpandedH2;
                    depthSumH1       += depthH1;
                    depthSumH2       += depthH2;
                    execTimeSumH1    += execTimeH1;
                    execTimeSumH2    += execTimeH2;
                }

                System.out.println("");

                // Compute average statistics.
                averageExpandedH1 = numExpandedSumH1 / (double) puzzles.length;
                averageExpandedH2 = numExpandedSumH2 / (double) puzzles.length;
                averageDepthH1    = depthSumH1       / (double) puzzles.length;
                averageDepthH2    = depthSumH2       / (double) puzzles.length;
                avgExecTimeH1     = execTimeSumH1    / (double) puzzles.length / 1000000.0;
                avgExecTimeH2     = execTimeSumH2    / (double) puzzles.length / 1000000.0;

                writer.writeLogFile(seed, size, swaps, number, averageExpandedH1, 
                        averageDepthH1, avgExecTimeH1,  averageExpandedH2, 
                        averageDepthH2, avgExecTimeH2);
            }
        }
    }

    /**
     * Used to split the filename string of the puzzleSet file to retrieve 
     * puzzle configuration info.
     * 
     * @param  string puzzleSet file name to retrieve info from.
     * @return array of [size, seed, swaps, number] puzzleSet info.
     */
    private static int[] splitString(String string) {

        // Ignore last .txt.
        string          = string.substring(0, string.length() - 4); 
        String[] tokens = string.split("-");
        int[] info      = new int[4];

        // Save split string tokens to int array.
        for (int i = 0; i < tokens.length - 1; i++) {
            info[i] = Integer.valueOf(tokens[i + 1]);
        }
        return info;
    }
}
