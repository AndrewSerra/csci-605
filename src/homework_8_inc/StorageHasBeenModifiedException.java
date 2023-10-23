package homework_8_inc;

public class StorageHasBeenModifiedException extends Exception {

    private static final long serialVersionUID = 1234L;

    public StorageHasBeenModifiedException(String message) {
        super(message);
    }
}
