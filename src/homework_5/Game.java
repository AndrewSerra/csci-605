package homework_5;

/**
 * This is the main class to run the Hangman Game. Main game logic is
 * contained in this class.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class Game {

    private final Player player = new Player();
    private final Scene scene = new Scene();
    private final Dictionary dictionary = new Dictionary();
    private boolean isGameOngoing = true;
    private String targetWord;
    private String targetFeedback;


    /**
     * Checks if there is an argument passed, if so override the default
     * words.txt file.
     *
     * @param args Command line arguments provided
     * @return File name and path of the file of the game word bank
     */
    private String parseArgs(String[] args) {
        try {
            return args.length == 0 ? "words.txt" : args[0];
        } catch (Exception e) {
            System.err.println("Arguments could not be parsed. " + e.getMessage());
            System.exit(2);
        }
        return null;
    }

    /**
     * Runs through the feedback string containing already marked correct
     * guesses. If there is a match, updates the feedback string.
     *
     * @param guess Input string from the user to compare with the target word
     */
    private void formatFeedbackString(String guess) {
        char[] targetWordAsArr = targetFeedback.toCharArray();
        for (int i = 0; i < targetWord.length(); i++) {
            if (targetWord.charAt(i) == guess.charAt(0)) {
                targetWordAsArr[i] = guess.charAt(0);
            }
        }
        targetFeedback = new String(targetWordAsArr);
    }

    /**
     * Checks if the target word contains the character entered by the user.
     *
     * @param guess A string of length one from the user
     * @return True if the character exists, false if not
     */
    private boolean validateGuess(String guess) {
        boolean isGuessCorrect = (targetWord.indexOf(guess) >= 0);
        if(isGuessCorrect) {
            formatFeedbackString(guess);
        }
        return isGuessCorrect;
    }

    /**
     * Resets the player, scene, and game to the initial state.
     */
    private void reset() {
        player.reset();
        scene.reset();
        targetWord = dictionary.getNext();
        targetFeedback = targetWord.replaceAll(".", ".");
    }

    /**
     * Checks if the guess limit is reached.
     *
     * @return True if maximum number of guesses are made
     */
    private boolean isGuessLimitExceeded() {
        return player.getGuessCount() >= Player.MAX_GUESS_COUNT;
    }

    /**
     * Checks if there is any non-filled in characters.
     *
     * @return True if all characters are filled, false if not
     */
    private boolean isWordGuessedFully() {
        return targetFeedback.indexOf('.') == -1;
    }

    /**
     * Prints the information string containing the guess count of the user
     * and the feedback string of the current guessing status.
     */
    private void printGuessCountAndFeedback() {
        System.out.printf(
                "# wrong guesses: %s  -- Word to guess: %s\n",
                player.getGuessCount(), targetFeedback);
    }

    /**
     * Print a message notifying the user has succeeded.
     */
    private void printPlayerWinMessage() {
        System.out.println("You win.");
    }

    /**
     * Print a message notifying the user has failed and the correct word.
     */
    private void printPlayerLoseMessage() {
        System.out.println("You lose.");
        System.out.printf("Correct word was: %s\n\n", targetWord);
    }

    /**
     * Single run of the game for only one word. Continuously runs the game
     * until maximum number of guesses is reached or the word is guessed
     * fully. Display result of the run.
     */
    private void runGameForOneWord() {
        boolean isExitCond;
        System.out.println(targetWord);
        do {
            scene.displayCurrentScene();
            printGuessCountAndFeedback();

            String guess = player.askForGuess();
            boolean isSuccessful = validateGuess(guess);

            if (!isSuccessful) {
                player.incrementGuess();
                scene.nextSegment();
            }

            isExitCond = isGuessLimitExceeded() || isWordGuessedFully();

            if(isGuessLimitExceeded()) {
                printPlayerLoseMessage();
            } else if(isWordGuessedFully()) {
                printPlayerWinMessage();
            }
        } while(!isExitCond);
    }

    /**
     * Main game loop. Loads the words in the dictionary, runs the game loop
     * until the user enters "no" to the prompt to play another game.
     *
     * @param args Command line arguments of the program
     */
    private void play(String[] args) {
        dictionary.loadWords(parseArgs(args));

        // Game loop
        do {
            reset();
            runGameForOneWord();

            if(!player.askForReplay()) {
                isGameOngoing = false;
            }
        } while(isGameOngoing);
    }

    public static void main(String[] args) {
        new Game().play(args);
    }
}