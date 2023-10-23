package homework_6;

/**
 * This class extends to the abstract class GamePhysics and implements Movement interface.
 * The methods have to be implemented as per requirement.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */

public class Messi extends GamePhysics implements Movement<String>, Dimensions {

    int height = 10;
    int width = 2;

    public String Attributes() {
        pace = 90;
        passing = 92;
        dribbling = 94;
        physical = 74;
        position = "RW";

        return pace + " " + physical + " " + dribbling + " " + passing + " " + position;
    }

    public void setWidth(int w) {
        width = w;
    }

    public void setHeight(int h) {
        height = h;
    }

    public String moveLeft() {
        return "Messi has moved left";
    }

    public String moveRight() {
        return "Messi has moved right";
    }

    public String moveUp() {
        return "Messi has moved up";
    }

    public String moveDown() {
        return "Messi has moved down";
    }
}
