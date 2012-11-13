package wordladderassignment.data;

import java.util.*;

/**
 *
 * @author krf
 */
public class Discovery {

    Hashtable<String, ArrayList<String>> ht = new Hashtable<>();
    ArrayList<String> wordLadder = new ArrayList<>();

    /**
     * This is the constructor for the Discovery Class
     *
     * @param hash The Hashtable containing the generated keys and values from
     * GenerateHashTable
     */
    public Discovery(Hashtable hash) {
        ht = hash;
    }

    /**
     * This methods check two words to determine how many characters differ
     * between the two words.
     *
     * @param word1 First String
     * @param word2 Second String
     * @return - int :
     *
     * Returns the amount of characters that differ between words.
     *
     */
    public int wordDifferenceAmount(String s1, String s2) {

        if (s1.length() != s2.length()) {
            return s2.length() + 1;
        }

        int diff = 0;

        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                diff++;
            }

        }

        return diff;
    }

    /**
     * This method generates a word ladder based on two given words. Two strings
     * are created, which are initialized to the given words. Then it adds that
     * word to the word ladder. Afterwards, it retrieves all words that are one
     * letter away from that word, going through them and checking if they are
     * the finish word, a word one letter away from the finish word or of a
     * small difference away from the finish word. It then adds a word, or
     * words, based on which condition it fulfills. When it reaches the finish
     * word, it breaks out of the method.
     *
     * @param one Start word of word ladder.
     * @param two Finish word of word ladder.
     */
    public void dicoverWordLadder(String one, String two) {
        String start = one;
        String finish = two;


        if ((ht.containsKey(one) != true) || (ht.containsKey(two) != true)) {
            System.out.print("Either both or one of the words cannot be found, Please try again \n");
        } else {

            ArrayList<String> temp;

            wordLadder.add(start);

            Queue<String> Q = new LinkedList<>();

            Q.add(start);

            while ((!Q.isEmpty()) && (!wordLadder.contains(finish))) {

                String t = Q.poll();


                if (t.equals(finish)) {
                    wordLadder.add(t);
                    break;
                } else {
                    temp = ht.get(t);

                    for (int i = 0; i < temp.size() - 1; i++) {
                        if (temp.contains(finish)) {
                            wordLadder.add(finish);
                            break;
                        }

                        String s = temp.get(i);

                        if (this.wordDifferenceAmount(s, finish) == 1) {
                            wordLadder.add(s);
                            wordLadder.add(finish);
                            break;
                        } else if ((this.wordDifferenceAmount(s, finish) <= 3) && (wordLadder.contains(s) == false)) {
                            wordLadder.add(s);
                            Q.add(s);
                        }

                    }
                }

            }

        }
    }

    /**
     * This method prints out the word ladder that has been generated. This
     * method checks to see if the word ladder created is empty. If it isn't, it
     * prints out the word ladder.
     *
     */
    public void printWordLadder() {
        if (wordLadder.isEmpty() == false) {

            System.out.print("Word Ladder \n");
            for (int k = 0; k < wordLadder.size(); k++) {
                System.out.print(wordLadder.get(k));
            }
            System.out.println();
        }

    }

    /**
     * Gets the Word Ladder. Mainly used for testing.
     *
     * @return ArrayList< String >
     */
    public ArrayList<String> getWordLadder() {
        return wordLadder;
    }
}
