package homework_7;

/**
 * An interface of methods to define the functionality of a generic
 * SortedStorage.
 *
 *  @author Andrew Serra
 *  @author Anindhya Kushagra
 *
 * @param <T>
 */
public interface ISortedStorage<T> {

    Object[] storage = new Object[2];

    boolean add(T o);

    boolean delete(T o);

    boolean find(T o);

    boolean includesNull();

    int getItemCount();

    int getNullCount();

    int getTotalCount();
}
