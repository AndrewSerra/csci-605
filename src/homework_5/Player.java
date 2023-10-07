package homework_5;

import java.util.Scanner;

/**
 * This is a class to handle user actions for the Hangman Game. User input is
 * taken here, guess count is tracked.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class Player {

    final static int MAX_GUESS_COUNT = 9;
    private Scanner scanner = new Scanner(System.in);
    private int guessCount = 0;

    /**
     * Validates if the guess length is more than one character long and that
     * it does not contain whitespace.
     *
     * @param guess A string value of length 1
     * @return True if the word is a single character and does not contain
     *         whitespace
     */
    private boolean isGuessValid(String guess) {
        return (!guess.isBlank()) && (guess.length() == 1);
    }

    /**
     * Ask for input of a guess from the user. Only returns the guess if it
     * is valid.
     *
     * @return A guess string that is validated.
     */
    public String askForGuess() {
        String guess = null;

        do {
            System.out.print("> ");
            guess = scanner.next();
        } while(!isGuessValid(guess));

        return guess;
    }

    /**
     * Ask for input of a command from the user to play the game again or not.
     *
     * @return True if entered "yes", False if "no".
     */
    public boolean askForReplay() {
        String guess = null;

        do {
            System.out.println("Do you want to continue (yes/no)?");
            guess = scanner.next().toLowerCase();
        } while(!guess.equals("yes") && !guess.equals("no"));

        return guess.equals("yes");
    }

    /**
     * Increment the guess count of the user.
     */
    public void incrementGuess() {
        guessCount++;
    }

    /**
     * Getter method to provide the number of wrong guesses made.
     *
     * @return Guess count for the user
     */
    public int getGuessCount() {
        return guessCount;
    }

    /**
     * Resets the player guess count to zero.
     */
    public void reset() {
        guessCount = 0;
    }
}
