package homework_10;

/**
 * This is an exception class for data that is not sufficient for execution.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class InsufficientDataException extends Exception {

    private static final long serialVersionUID = 1003L;
    public InsufficientDataException(String message) {
        super(message);
    }
}
