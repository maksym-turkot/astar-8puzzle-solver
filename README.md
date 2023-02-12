# A* Search n-Puzzle Solver and Heuristic Domination Analysis

Searching algorithms are at the foundation of the methods that AI agents use to solve problems. In this project, I am implementing an agent that uses A* searching algorithm with two heuristics to search through 3-, 8-, and 15-puzzles, and comparing the performance results for both heuristics. 

## Configuring the program

Program is completely configured by the puzzleConfig files, and it takes no input from the terminal. To change the behavior of the program, user should modify or add puzzleConfig files.

Configuration files must be located in the project1/data/puzzleConfig folder. The structure of the file is as follows:

size: \<size\>    // Size of the puzzle (2/3/4…).
seed: \<seed\>    // Random seed.
swaps: \<swaps\>    // Number of random swaps from goal state (randomization).
number: \<number\>    // Number of initial states generated.

The phrase “puzzleConfig” must be present in the filename. The following naming convention is recommended:

puzzleConfig-\<size\>-\<seed\>-\<swaps\>-\<number\>.txt

For example:

puzzleConfig-3-111-200-100.txt
puzzleConfig-4-2323-30-50.txt

## Running the program

Program was developed using Java.

To run the program, please make sure you are located in the turkotm folder. The Makefile is provided, so you can run the program using the following commands:

make or make compile - compiles the program
make run - runs the program
make clean - removes compiled .class files.

When running, program will first read puzzleConfig files and generate puzzle sets, and then save them to the data/puzzleSet folder. The puzzleSet file simply stores string representations of puzzle 2D arrays. The naming format of a puzzleSet file is similar to the puzzleConfig one, and must not be modified:

puzzleSet-3-111-200-100.txt
puzzleSet-4-2323-30-50.txt

Each puzzleSet file corresponds to one puzzleConfig file. User has no need to edit or add puzzleSet files, even though they may inspect files to see what puzzles are being solved.

As the program is executed, a progress bar will appear for each file, with a “#” indicating a solved puzzle.

For more info, please see the search-8puzzle-doc.pdf in the doc folder.