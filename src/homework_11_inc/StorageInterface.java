package homework_11_inc;

/**
 * An interface of methods to define the functionality of a generic
 * SortedStorage.
 *
 *  @author Andrew Serra
 *  @author Anindhya Kushagra
 *
 * @param <T>
 */
public interface StorageInterface<T> extends Iterable<T> {

    Object[] storage = new Object[2];

    boolean add(T o) throws StorageHasBeenModifiedException;

    boolean delete(T o) throws StorageHasBeenModifiedException;

    boolean find(T o);

    boolean includesNull();

    int getItemCount();

    int getNullCount();

    int getTotalCount();
}
