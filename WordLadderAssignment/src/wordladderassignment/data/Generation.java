package wordladderassignment.data;

import java.util.*;

/**
 *
 * @author krf
 */
public class Generation {

    private Hashtable<String, ArrayList<String>> ht = new Hashtable<>();
    private ArrayList<String> wordLadder = new ArrayList<>();
    int count = 0;

    /**
     * This is the constructor for Generation.
     *
     * @param hash The Hashtable containing the generated keys and values from
     * GenerateHashTable
     */
    public Generation(Hashtable hash) {
        ht = hash;
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
     * This method generates a word ladder based on the given word and given
     * length. A string is created, which is initialized to the given word. Then
     * it adds that word to the word ladder. Afterwards, it retrieves a random
     * word, or the only word available, in the ArrayList< String > that is
     * attached to that word. This word is then also added to the word ladder,
     * then the word being used is changed to the word retrieved. This continues
     * until the word ladder is of the size given by the user. If a word ladder
     * cannot be found, it breaks out of the method.
     *
     * @param start The start word
     * @param count The size of word ladder the user asked for
     */
    public void generateWordLadder(String start, int length) {
        String n = start;
        count = length;

        wordLadder.add(n);

        while (wordLadder.size() < count) {
            ArrayList<String> temp;
            int index = 0;

            temp = (ArrayList<String>) ht.get(n);

            if (!temp.isEmpty()) {

                Random r = new Random();

                if (temp.size() == 1) {
                    index = temp.size() - 1;
                } else {
                    index = r.nextInt(temp.size() - 1);
                }

                if (wordLadder.contains(temp.get(index)) == false) {
                    wordLadder.add(temp.get(index));
                    n = temp.get(index);
                }

            } else {
                break;
            }

        }

    }

    /**
     * This method prints out the word ladder that has been generated. This
     * method checks to see if the word ladder created is the same as the size
     * asked for by the user. If it isn't, it informs the user of this.
     * Otherwise, it prints out the word ladder.
     *
     * @param count The size of the word ladder the user asked for.
     */
    public void printWordLadder() {
        if (wordLadder.size() < count) {
            System.out.print("A word ladder of that size could not be made \n");
        } else if (wordLadder.size() != 1) {
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
