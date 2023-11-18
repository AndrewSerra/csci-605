package homework_10;

/**
 * This is an exception class for operations that are not permitted in a
 * certain condition.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class InvalidOperationException extends Exception {
    private static final long serialVersionUID = 1002L;
    public InvalidOperationException(String message) {
        super(message);
    }
}
