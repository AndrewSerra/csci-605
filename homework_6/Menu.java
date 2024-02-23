package homework_6;

/**
 * This class implements homework_6.Movement interface. It implements the movement functionalities, as all you can
 * do in the menu of the game is to move around.
 * This movement is different from movement of a player in the game.
 * The methods have to be implemented as per requirement.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */

public class Menu implements Movement<Integer>, Dimensions {

    int container_height = 400;
    int container_width = 300;
    int screen_pos_x = 0;
    int screen_pos_y = 0;

    public void setWidth(int w) {
        container_width = w;
    }

    public void setHeight(int h) {
        container_height = h;
    }

    public Integer moveLeft() {
        return --screen_pos_x;
    }

    public Integer moveRight() {
        return ++screen_pos_x;
    }

    public Integer moveUp() {
        return ++screen_pos_y;
    }

    public Integer moveDown() {
        return --screen_pos_y;
    }
}
