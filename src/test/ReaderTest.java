package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;

import main.Reader;

public class ReaderTest {
    Reader reader;
    

    public ReaderTest() {
        this.reader = new Reader();
        
    }

    @Test
    public void readFileTest() {

        File file = new File(".");
        System.out.println(file.getAbsolutePath());
        
        
        this.reader.readFile("/Users/maxtur/Documents/git-pre/search-8puzzle-solver/data/puzzleConfig/" + 
                "puzzleConfig-3-2424-80-100.txt", 0, 0);

        assertEquals(2424, reader.getPSeed());
        assertEquals(3,    reader.getPSize());
        assertEquals(80,  reader.getPSwaps());
        assertEquals(100,  reader.getPNumber());
    }

    @Test 
    public void stringToPuzzle() {
        String  string1 = "[[5, 3, 4], [0, 6, 1], [7, 8, 2]]";
        int[][] puzzle1 =  {{5, 3, 4}, {0, 6, 1}, {7, 8, 2}};

        assertTrue(Arrays.deepEquals(puzzle1, reader.stringToPuzzle(string1, 3)));
    }
    
}
