package homework_5;

/**
 * This is a class to handle the Hangman Game Scene to be drawn. Each segment
 * skip is an increase in wrong guesses.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class Scene {

    final String[] FULL_SCENE = {
            "........................",
            "........................",
            "................111.....",
            "................111.....",
            ".................2......",
            "...............34443....",
            "..............3.444.3...",
            "................444.....",
            "...............5..5.....",
            "..............66...66...",
            "777777777777777777777777",
            "88....................88",
            "99....................99" };
    final int MAX_SCENE_NUM = 9;
    final char SCENE_EMPTY_CHAR = '.';
    final char SCENE_FULL_CHAR = '*';

    private int activeScene = 0;

    /**
     *  Increment the scene count to represent a wrong guess.
     */
    public void nextSegment() {
        if(activeScene < MAX_SCENE_NUM) {
            activeScene++;
        }
    }

    /**
     * Displays the scene. If the character is a digit and the value is less
     * than the activeScene value, the digit is replaced by a '.', digits are
     * replace with an "*".
     */
    public void displayCurrentScene() {
        for (String row : FULL_SCENE) {
            for (int col = 0; col < FULL_SCENE[0].length(); col++) {
                char currCol = row.charAt(col);

                if(Character.isDigit(currCol)
                    && (Character.getNumericValue(currCol) > activeScene)) {
                    System.out.print(SCENE_FULL_CHAR);
                } else {
                    System.out.print(SCENE_EMPTY_CHAR);
                }

            }
            System.out.println();
        }
    }

    /**
     * Resets the game scene to the initial setting.
     */
    public void reset() {
        activeScene = 0;
    }
}
