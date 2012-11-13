package wordladderassignment.data;

import java.io.*;
import java.util.*;

/**
 *
 * @author krf
 */
public class WordLadderAssignment {

    /**
     *
     * This is the main front of the program. First the user must choose a
     * length of word it wishes to use, then there is the option of Generation
     * or Discovery.
     *
     * Generation is used to create a word ladder of a certain length, from a
     * starting word. The user inputs the word and the length.
     *
     * Discovery is used to discover a word ladder of the shortest possible
     * length between two different words. These words are entered by the user.
     *
     * There is an option to exit the program after every Generation or
     * Discovery.
     *
     * @param args the command line arguments
     * @throws FileNotFoundException
     * @throws IOException
     *
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        ArrayList<String> al = new ArrayList<>();
        Hashtable<String, ArrayList<String>> ht = new Hashtable<>();
        String exit = "";
        String s = "";
        String word = "";
        String answer = " ";
        int length, size = 0;
        File f;
        Scanner in = new Scanner(System.in);

        /*
         * 
         * Asks for the length of word you'd like to use, then selects a file containing words only of that size.
         * Length must be between 3-9 as these are the only files present.
         * 
         */

        do {

            System.out.print("Please enter the length of words you'd like to use (enter a number between 3 - 9): \n");
            length = in.nextInt();

            while ((length < 3) || (length > 9)) {
                System.out.print("Please enter an appropriate value between 3 - 9: ");
                length = in.nextInt();
            }


            switch (length) {
                case 3:
                    s = "dictionary3.dat";
                    break;
                case 4:
                    s = "dictionary4.dat";
                    break;
                case 5:
                    s = "dictionary5.dat";
                    break;
                case 6:
                    s = "dictionary6.dat";
                    break;
                case 7:
                    s = "dictionary7.dat";
                    break;
                case 8:
                    s = "dictionary8.dat";
                    break;
                case 9:
                    s = "dictionary9.dat";
                    break;

            }

            /*
             * Reads words from file and generates a hashtable, which is representing an adjaceny list,
             * from those words.
             */

            f = new File(s);

            WordFileReader wfr = new WordFileReader(f);
            al = wfr.getWords();

            GenerateHashTable gwl = new GenerateHashTable(al);

            ht = gwl.populateGraph();

            /*
             * Asks user to choose between Generation or Discovery. If the users enter anything other than
             * Generation or Discovery, UpperCase or LowerCase, the program will restart.
             */

            System.out.print("Please enter what you would like to do : \n");
            System.out.print("(Generation or Discovery) \n");

            answer = in.next();

            answer = answer.toLowerCase();

            switch (answer) {

                case ("generation"): {
                    Generation g = new Generation(ht);

                    /*
                     *The user is asked for the word with which to start the word ladder, and the length they wish to create. 
                     */

                    System.out.print("Please enter a word : \n");
                    word = in.next();

                    while (word.length() != length) {
                        System.out.print("Please enter a word of the same length as you chose earlier : \n");
                        word = in.next();
                    }

                    word = word.concat("\n");

                    word = word.toLowerCase();

                    /*
                     * The word the user enters is checked against the hashtable to ensure
                     * it is present.
                     */

                    if (ht.containsKey(word) == true) {

                        System.out.print("Please enter the size of word ladder you wish to use : \n");
                        size = in.nextInt();

                        g.generateWordLadder(word, size);

                        System.out.println();

                        g.printWordLadder();

                        /*
                         * After Generation, the user is asked if they would like to exit. Otherwise,
                         * the program restarts.
                         */

                        System.out.print("Would you like to exit? \n");
                        exit = in.next();

                        exit = exit.toLowerCase();

                        if (("y".equals(exit)) || ("yes".equals(exit))) {
                            System.exit(0);
                        }

                        break;

                    } else {
                        System.out.print("No such word in file \n");


                        break;
                    }

                }

                case ("discovery"): {

                    Discovery d = new Discovery(ht);

                    /*
                     *The user is asked for the word with which to start the word ladder, and the word
                     * they wish to end with.
                     */

                    System.out.print("Please enter your first word : \n");
                    String one = in.next();

                    one = one.concat("\n");

                    one = one.toLowerCase();

                    System.out.print("Please enter your second word : \n");
                    String two = in.next();

                    two = two.concat("\n");

                    two = two.toLowerCase();

                    d.dicoverWordLadder(one, two);

                    System.out.println();

                    d.printWordLadder();

                    System.out.print("Would you like to exit? \n");
                    exit = in.next();

                    exit = exit.toLowerCase();

                    /*
                     * After Discovery, the user is asked if they would like to exit. Otherwise,
                     * the program restarts.
                     */

                    if (("y".equals(exit)) || ("yes".equals(exit))) {
                        System.exit(0);
                    }

                    break;

                }
            }
            /*
             * This is loops carries on while the answer does not equal null,
             * as the answer is unlikely to ever be null. This is used so
             * the program will continue until the user
             * wishes to exit. They can do this when prompted.
             */

        } while (answer != null);
    }
}
