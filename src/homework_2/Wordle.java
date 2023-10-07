/*
 * Wordle.java
 * Author: Andrew Serra
 * Date: 09/05/2023
 */
package homework_2;

import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * The game of Wordle in a text-based game format. The game continue until
 * the input file with words to play with have no new options.
 *
 * @author      Andrew Serra
 */
public class Wordle {
    static int soManyWordToPLayWith = 0;
    static int guessCount = 0;
    static HashMap<String, Boolean> usedWords = new HashMap<>();
    static final String[] theWords = new String[10231];
    static final Scanner readGuess = new Scanner(System.in);

    /**
     * Read all the words to play with throughout the games. Saves all the
     * values in a hashmap and a string array.
     *
     * @param       fileName    The file name including its path relative to
     *                          the project root.
     */
    public static void readWordsFromFile(String fileName) {
        try (
                BufferedReader input = new BufferedReader(new FileReader(fileName))
        ) {
            int counter = 0;
            while  ( ( theWords[counter] = input.readLine() )  != null ) {
                soManyWordToPLayWith++;
                usedWords.put(theWords[counter++], false);
            }
        }
        catch ( Exception e)	{
            System.out.println("ExceptionType occurred: " + e.getMessage() );
        }
    }

    /**
     * Continuously read the guess of the user until the length of the input
     * is 5 characters long
     *
     * @param       guessNumber    The current state of the number of guesses
     *                             made.
     *
     * @return      Returns the valid guess entered by the user.
     */
    public static String readUserInput(int guessNumber) {
        String guess = "";
        do {
            System.out.printf("%d > ", guessNumber+1);
            if  ( readGuess.hasNext() )	{
                guess = readGuess.nextLine();
            }
        } while ( guess.length() != 5 );
        return guess;
    }

    /**
     * Returns a word from the list of word options. Updates the hashmap to
     * prevent returning an already played word.
     *
     * @return A string of the next word from the list read from the input file.
     */
    public static String getWord() {
        if(usedWords.isEmpty()) {
            return null;
        }
        String nextWord = theWords[new Random().nextInt(soManyWordToPLayWith)];
        while(!usedWords.containsKey(nextWord)) {
            nextWord = theWords[new Random().nextInt(soManyWordToPLayWith)];
        }
        usedWords.remove(nextWord);
        return nextWord;

    }

    /**
     * Compares the input string to the word trying to be guessed.
     *
     * @param       guess    A five-character string of the guess the user
     *                       makes.
     * @param       target   The word trying to be guessed.
     *
     * @return      True if the parameters match, false if not.
     */
    public static boolean compareGuessInput(String guess, String target) {
        return guess.equals(target);
    }

    /**
     * Compares the input string to the word trying to be guessed.
     *
     * @param       guess    A five-character string of the guess the user
     *                       makes.
     * @param       target   The word trying to be guessed.
     *
     * @return      Five-character string representing the result of the
     *              guess. '*' is a correct guess, '-' is a correct character
     *              in the wrong location, 'x' is a character not in the
     *              target word.
     */
    public static String formatResultString(String guess, String target) {
        char[] resultCharList = new char[6];

        for(int i=0; i < 5; i++) {
            if(guess.charAt(i) == target.charAt(i)) {
                resultCharList[i] = '*';
            } else if(target.contains(String.valueOf(guess.charAt(i)))) {
                resultCharList[i] = '_';
            } else {
                resultCharList[i] = 'x';
            }
        }
        // Add newline character for consistency
        resultCharList[5] = '\n';
        return new String(resultCharList);
    }

    /**
     * Resets the game for the next word. Guess count is set to zero.
     */
    public static void resetGame() {
        guessCount = 0;
    }

    /**
     * Prints game instructions and instructions.
     */
    public static void printInstructions() {
        System.out.println("_ indicates the letter is in the word but in the wrong spot.");
        System.out.println("* indicates the letter is in the word and correct spot.");
        System.out.println("x indicates the letter is not in the word.");
        System.out.println("Try to guess the word in 5 tries.");
    }

    /**
     * Check for end of game. Ends the game is there is no words left.
     *
     * @param       nextWord String that is the next word assigned from the
     *                       wordlist.
     *
     * @return      True if the target word is null. If string is assigned return
     *              false.
     */
    public static boolean didGameEnd(String nextWord) {
        if(nextWord == null) {
            System.out.println("No words left. Goodbye");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Starts the game. Displays instructions and descriptions of what the
     * feedback string means. Game loop begins and continues until the words
     * read from the file ends.
     */
    public static void playWordle() {
        printInstructions();

        while(true) {
            String targetWord = getWord();

            if(didGameEnd(targetWord))
                break;

            do {
                String currentGuess = readUserInput(guessCount);

                if (compareGuessInput(currentGuess, targetWord)) {
                    System.out.println("Congrats correct guess.");
                    resetGame();
                    break;
                } else {
                    guessCount++;
                    System.out.print(
                            formatResultString(currentGuess,targetWord));
                }
            } while(guessCount < 5);
        }
    }

    public static void main(String[] args) {
        readWordsFromFile("src/homework_2/word.txt"); // reads the text
        // file - this file has to be in the local directory
        playWordle();
    }
}
