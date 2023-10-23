package homework_6;

/**
 * This is an abstract class that has all the required methods that one might
 * have to implement when they are extending to this class.
 * Abstract methods like the players attributes are declared here.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */

public abstract class GamePhysics {

    int pace = 0;
    int physical = 0;
    int dribbling = 0;
    int passing = 0;
    String position = null;

    public abstract String Attributes();

    public String toString(){
        return pace + " " + physical + " " + dribbling + " " + passing + " " + position;
    }

}
