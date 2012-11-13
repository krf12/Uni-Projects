package wordladderassignment.data;

import java.util.*;

/**
 *
 * @author krf
 */
public class GenerateHashTable {

    private ArrayList<String> words;
    private Hashtable<String, ArrayList<String>> ht = new Hashtable();

    /**
     * This is the constructor for GenerateHashTable.
     *
     * @param al An ArrayList < String > of previously generated words, all of
     * the same length.
     */
    public GenerateHashTable(ArrayList<String> al) {
        words = al;
    }

    /**
     * This methods check two words to determine if they have a difference of
     * one character.
     *
     * @param word1 First String
     * @param word2 Second String
     * @return - Boolean :
     *
     * Returns true if there is a difference of one character.
     *
     */
    public boolean wordDifference(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }

        int diffs = 0;

        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                if (++diffs > 1) {
                    return false;
                }

            }

        }

        return diffs == 1;
    }

    /**
     * This method creates a Hashtable<Node, ArrayList< String >> from an
     * ArrayList< String >. It goes through every word in the ArrayList< String>
     * , creating another ArrayList< String > that contains all the words that
     * have a one character difference from it. After creating the ArrayList<
     * String> , the word and ArrayList< String > are placed in the Hashtable as
     * a node key and value respectively.
     *
     *
     * @return - Hashtable< String, ArrayList< String > >
     */
    public Hashtable<String, ArrayList<String>> populateGraph() {

        for (int i = 0; i < words.size(); i++) {
            String n = words.get(i);
            ArrayList<String> temp = new ArrayList<>();

            for (int j = 0; j < words.size(); j++) {
                if (this.wordDifference(n, words.get(j)) == true) {
                    temp.add(words.get(j));
                }

            }

            ht.put(n, temp);
        }
        return ht;
    }

    /**
     *
     * This method prints out the entirety of the Hashtable, labeling which what
     * is a key and what is a value.
     *
     */
    public void printHashtable() {

        Enumeration keys = ht.keys();

        while (keys.hasMoreElements() == true) {

            String tem = (String) keys.nextElement();

            ArrayList<String> temp = ht.get(tem);

            System.out.print("Key : " + tem);

            for (int i = 0; i < temp.size(); i++) {
                System.out.print("Value : " + temp.get(i));
            }

        }

    }
}
