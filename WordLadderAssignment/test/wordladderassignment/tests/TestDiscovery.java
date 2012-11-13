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
public class TestDiscovery {

    WordFileReader wfr;
    ArrayList<String> words = new ArrayList<>();
    GenerateHashTable ght;
    Hashtable<String, ArrayList<String>> ht;
    Discovery d;

    public TestDiscovery() throws FileNotFoundException, IOException {
        wfr = new WordFileReader(new File("dictionary5.dat"));
        words = wfr.getWords();
        ght = new GenerateHashTable(words);
        ht = new Hashtable<>();
        ht = ght.populateGraph();
        d = new Discovery(ht);
    }

    @Test
    public void testWordLadder() {

        String start = "wired\n";
        String finish = "fires\n";

        d.dicoverWordLadder(start, finish);
        d.printWordLadder();

        ArrayList<String> wordLadder = d.getWordLadder();

        assertEquals("wordLadder did not start with wired", "wired\n", wordLadder.get(0));
        assertEquals("wordLadder did not end with fires", "fires\n", wordLadder.get(wordLadder.size() - 1));

        wordLadder.clear();

        start = "claps\n";
        finish = "chaps\n";

        d.dicoverWordLadder(start, finish);
        d.printWordLadder();

        wordLadder = d.getWordLadder();

        assertEquals("wordLadder did not start with claps", "claps\n", wordLadder.get(0));
        assertEquals("wordLadder did not end with chaps", "chaps\n", wordLadder.get(wordLadder.size() - 1));

        wordLadder.clear();

        start = "clock\n";
        finish = "chuck\n";

        d.dicoverWordLadder(start, finish);
        d.printWordLadder();

        wordLadder = d.getWordLadder();

        assertEquals("wordLadder did not start with clock", "clock\n", wordLadder.get(0));
        assertEquals("wordLadder did not end with chuck", "chuck\n", wordLadder.get(wordLadder.size() - 1));

    }
}
