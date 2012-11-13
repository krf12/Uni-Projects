package wordladderassignment.data;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author krf
 */
public class WordFileReader {

    private File f;
    private BufferedReader br;
    private ArrayList<String> words = new ArrayList<>();

    /**
     * This is the constructor for WordFileReader.
     *
     *
     * @param file This is a file containing a number of words, all of a certain
     * length. Each word is on a different line in the file.
     *
     * @throws FileNotFoundException
     */
    public WordFileReader(File file) throws FileNotFoundException {
        f = file;
        br = new BufferedReader(new FileReader(f));
    }

    /**
     * This method reads through the entire file and returns an ArrayList<
     * String >. The elements in the ArrayList should contain each word with a
     * newline character added to the end.
     *
     * @return java.util.ArrayList< java.lang.String >
     * @throws IOException
     */
    public ArrayList<String> getWords() throws IOException {
        String s = "";
        while ((s = br.readLine()) != null) {
            words.add(s + "\n");
        }
        if (words != null) {
            return words;
        } else {
            return null;
        }
    }
}
