package homework_3_inc;
// TODO: INCOMPLETE

// Total 9 guesses per word
// round consists of 9/9 guess or correct word

import java.io.File;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * The class plays the game of hangman.
 *
 * @author Andrew Serra
 */
public class BackToTheFuture {

    static final int MAX_ATTEMPTS = 9;
    static int currentGuessCount = 0;
    static int wordCount = 0;
    static String[] dictionary = new String[10231];
    static boolean[] wordUsed = new boolean[10231];
    static char[] guessList = new char[MAX_ATTEMPTS];
    static Scanner scanner = new Scanner(System.in);

    /**
     * Reads in the file anf records the words in a string array of the class.
     *
     * @param fileName The file name to collect the words in the dictionary.
     */
    public static void loadDictionaryFromFile(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));

            while(scanner.hasNextLine()) {
                dictionary[wordCount] = scanner.nextLine();
                wordUsed[wordCount++] = false;
            }

        } catch (Exception e) {
            System.out.println("Something went wrong. Error: " + e.getMessage());
        }
    }

    /**
     * Select a new word from the dictionary and mark it as used word in the
     * boolean array.
     *
     * @return Selected new word.
     */
    public static String selectNewWord() {
        Random rand = new Random();
        int nextIndex = rand.nextInt(wordCount);
        // Iterate until a new word is selected
        while(wordUsed[nextIndex]) {
            nextIndex = rand.nextInt(wordCount);
        }
        wordUsed[nextIndex] = true;
        return dictionary[nextIndex];
    }

    /**
     * Draw the state of the game. Shows empty spaces if the attempts are
     * used, if not populate with pound sign.
     */
    public static void drawScene() {
        int[][] fullScene = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
        };
        for(int i=0; i < fullScene.length; i++) {
            if(i < currentGuessCount) {
                System.out.println(" ".repeat(fullScene[0].length));
            } else {
                for(int j=0; j < fullScene[0].length; j++) {
                    System.out.print(fullScene[i][j] == 0 ? " " : "#");
                }
                System.out.println();
            }
        }
    }

    /**
     *
     */
    public static void displayGameState(String targetWord, char guess) {
//        drawScene();
        char[] feedbackStr = targetWord.toCharArray();

        if(Arrays.asList(guessList).contains(guess)) {
            return;
        }

        guessList[currentGuessCount] = guess;

        for(int i=0; i < feedbackStr.length; i++) {
            char currentChar = feedbackStr[i];
            System.out.println(currentChar + " " + guess + " " + String.valueOf(guessList) +
                    " " + (currentChar == guess));
            System.out.println(Arrays.asList(guessList).contains(currentChar));
            if(!Arrays.asList(guessList).contains(currentChar)) {
                feedbackStr[i] = '.';
            }
            System.out.println(feedbackStr);
            System.out.println("-------");
        }
        String guessText =
                String.format("# wrong guesses: %d -- Word to guess: %s",
                        (currentGuessCount++), String.valueOf(feedbackStr));
        System.out.println(guessText);
    }

    /**
     * Resets the game. New word is selected and attempts are set back to
     * maximum value. Sets the guessList array to be empty.
     *
     * @return The new word to play the game.
     */
    public static String resetGame() {
        currentGuessCount = 0;
        // guessList.
        return selectNewWord();
    }

    /**
     * Play the game Hangman. Game loops is run in this function.
     */
    public static void playHangman() {}


    public static void main(String[] args) {
        loadDictionaryFromFile(args[0]);
        displayGameState("hello", 'o');
        displayGameState("hello", 'h');
        displayGameState("hello", 'p');
        displayGameState("hello", 'h');
    }
}
