package homework_6;

/**
 * This class extends to the abstract class homework_6.GamePhysics and implements homework_6.Movement interface.
 * The methods have to be implemented as per requirement.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */

public class Ronaldo extends GamePhysics implements Movement<String>, Dimensions {

    int height = 10;
    int width = 2;

    public String Attributes() {
        pace = 91;
        passing = 82;
        dribbling = 93;
        physical = 80;
        position = "LW";

        return pace + " " + physical + " " + dribbling + " " + passing + " " + position;

    }

    public void setWidth(int w) {
        width = w;
    }

    public void setHeight(int h) {
        height = h;
    }

    public String moveLeft() {
        return "Ronaldo has moved left";
    }

    public String moveRight() {
        return "Ronaldo has moved right";
    }

    public String moveUp() {
        return "Ronaldo has moved up";
    }

    public String moveDown() {
        return "Ronaldo has moved down";
    }
}
