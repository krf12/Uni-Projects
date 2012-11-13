
package wordladderassignment.tests;

import java.io.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import wordladderassignment.data.*;

/**
 *
 * @author krf
 */
public class TestGeneration {

    WordFileReader wfr;
    ArrayList<String> words = new ArrayList<>();
    GenerateHashTable ght;
    Hashtable<String, ArrayList<String>> ht;
    Generation g;

    public TestGeneration() throws FileNotFoundException, IOException {
        wfr = new WordFileReader(new File("dictionary5.dat"));
        words = wfr.getWords();
        ght = new GenerateHashTable(words);
        ht = new Hashtable<>();
        ht = ght.populateGraph();
        g = new Generation(ht);
    }

    @Test
    public void testWordDifference() {
        String one = "hello";
        String two = "hells";

        assertEquals("Returned False, when True was expected", true, g.wordDifference(one, two));

        two = "helms";

        assertEquals("Returned True when False was expected", false, g.wordDifference(one, two));
    }

    @Test
    public void testWordLadder() {

        String start = "hello\n";
        int length = 3;

        g.generateWordLadder(start, length);
        g.printWordLadder();

        ArrayList<String> wordLadder = g.getWordLadder();

        assertEquals("wordLadder did not start with hello", "hello\n", wordLadder.get(0));
        assertEquals("wordLadder was not the correct length", 3, wordLadder.size());

        wordLadder.clear();

        start = "match\n";
        length = 5;

        g.generateWordLadder(start, length);
        g.printWordLadder();

        wordLadder = g.getWordLadder();

        assertEquals("wordLadder did not start with hello", "match\n", wordLadder.get(0));
        assertEquals("wordLadder was not the correct length", 5, wordLadder.size());

        wordLadder.clear();

        start = "claps\n";
        length = 2;

        g.generateWordLadder(start, length);
        g.printWordLadder();

        wordLadder = g.getWordLadder();

        assertEquals("wordLadder did not start with hello", "claps\n", wordLadder.get(0));
        assertEquals("wordLadder was not the correct length", 2, wordLadder.size());
    }
}
