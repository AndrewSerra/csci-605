package homework_6;

/**
 * An interface of methods to define the functionality of a generic
 * SortedStorage.
 *
 *  @author Andrew Serra
 *  @author Anindhya Kushagra
 *
 * @param <T>
 */
public interface SortedStorage<T> {

    Object[] storage = new Object[2];

    boolean add(T o);

    boolean delete(T o);

    boolean find(T o);

    boolean includesNull();

    int getItemCount();

    int getNullCount();

    int getTotalCount();
}
