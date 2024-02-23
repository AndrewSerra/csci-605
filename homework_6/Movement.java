package homework_6;

/**
 * This is an interface that has all the required methods that one might have to implement
 * when they are implementing this class.
 * Moving in different directions are taken care of by the abstract methods of this interface.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */

public interface Movement<T> {

     T moveLeft();

     T moveRight();

     T moveUp();

     T moveDown();
}
