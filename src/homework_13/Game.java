package homework_13;

import java.util.Random;

/**
 * Runs the wordle game logic. Methods can be called individually.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class Game {

    private String playerID;
    private String word;
    private int guessNum = 0;
    private static String[] theWords = new String[10231];
    private static int soManyWordToPLayWith;
    private static final int MAX_GUESS_COUNT = 5;

    /**
     * Creates a new Game object.
     *
     * @param player The identifier of the client
     */
    public Game(String player) {
        playerID = player;
        word = getWord();
        System.out.println("Player " + playerID + " is assigned word: " + word);
    }

    /**
     * Static method that loads the words to the class Game.
     *
     * @param words Array of words that are used in the game
     * @param count Number of words existing in the string array
     */
    public static void loadWords(String[] words, int count) {
        theWords = words;
        soManyWordToPLayWith = count;
    }

    /**
     * Resets the game for a new start.
     */
    public void reset() {
        guessNum = 0;
        word = getWord();
        System.out.println("Player " + playerID + " is assigned word: " + word);
    }

    /**
     * Marks the characters correctly guessed depending on position.
     *
     * @param guess The user guess to verify
     * @return A feedback string containing the correctly guessed characters
     */
    public String evaluateGuess(String guess) {
        StringBuilder rValue = new StringBuilder();
        for (int index = 0; index < guess.length(); index++) {
            char charAtPositionIndex = guess.charAt(index);
            if (charAtPositionIndex == word.charAt(index))
                rValue.append("*");
            else if (word.indexOf(charAtPositionIndex) > 0)
                rValue.append("_");
            else
                rValue.append("x");
        }
        return rValue.toString();
    }

    /**
     * Checks if the feedback string is fully guessed correctly.
     *
     * @param progressSoFar The feedback string
     * @return True if the string is completed, else False
     */
    public boolean isItSolved(String progressSoFar) {
        int counter = 0;
        for (int index = 0; index < progressSoFar.length(); index++) {
            if (progressSoFar.charAt(index) == '*')
                counter++;
        }
        boolean isComplete = progressSoFar.length() == counter;

        if(!isComplete) {
            guessNum++;
        }
        return isComplete;
    }

    /**
     * Checks if the guess count is at the limit.
     *
     * return True if the guess number is equal to maximum allowed.
     */
    public boolean isAtMaxAttempts() {
        return guessNum == MAX_GUESS_COUNT;
    }

    /**
     * Gets a new word to play from the word list.
     *
     * @return The selected word string
     */
    private String getWord() {
        return theWords[new Random().nextInt(soManyWordToPLayWith)];
    }
}
