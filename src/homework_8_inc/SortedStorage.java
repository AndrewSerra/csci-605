package homework_8_inc;

import java.util.Iterator;

/**
 * This is a class that allows saving any type of object sorted that are unique.
 * The behavior is similar to sets. The number of null values being added and
 * removed are being tracked along with the unique strings. This is a generic
 * class.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class SortedStorage<T extends Comparable<T>>
        implements StorageInterface<T>, Comparable<SortedStorage<T>> {

    private final boolean isSetBehavior;
    private int itemCount = 0;
    private int nullCount = 0;
    private SortedStorageIterator<T> iterator = null;

    private Object[] storage = new Object[2];

    public SortedStorage() {
        isSetBehavior = true;
    }

    public SortedStorage(boolean isSet) {
        isSetBehavior = isSet;
    }

    /**
     * Checks if the object item exists in the storage.
     *
     * @param o The object to find from storage.
     * @return True if the value is stored, false if not.
     */
    private boolean isObjectStored(T o) {
        boolean itemExists = false;

        if(o == null) {
            return nullCount > 0;
        }

        for(Object obj : storage) {
            if(obj != null) {
                if (obj.equals(o)) {
                    itemExists = true;
                    break;
                }
            }
        }
        return itemExists;
    }

    /**
     * Looks for the index where an object should be inserted to have a
     * sorted storage array.
     *
     * @param o The object to find the index of where to insert into
     *          storage.
     * @return The index to add into storage array.
     */
    @SuppressWarnings("unchecked")
    private int findSortedStorageIdx(T o) {
        int idx = 0;
        while(idx < storage.length) {
            // Exit if end of the storage is received before a target value
            if(storage[idx] == null) {
                break;
            }

            if(o.compareTo((T) storage[idx]) <= 0) {
                break;
            }

            idx++;
        }
        return idx;
    }

    /**
     * Iterates through the storage to find the index of a given object.
     *
     * @param o The object to find from storage.
     * @return The index of the object stored in the storage array.
     */
    private int getObjectIndex(T o) {
        int retStringIdx = -1;
        for(int i=0; i < storage.length; i++) {
            if (o.equals(storage[i])) {
                retStringIdx = i;
            }
        }
        return retStringIdx;
    }

    @SuppressWarnings("unchecked")
    public T getObjectAtIdx(int idx) {
        if(itemCount < idx) {
            throw new IndexOutOfBoundsException("Index is larger than stored " +
                    "item count.");
        }
        return (T) storage[idx];
    }

    /**
     * Getter method to receive the item count.
     *
     * @return The non-null item count.
     */
    public int getItemCount() {
        return itemCount;
    }

    /**
     * Getter method to receive the null item count.
     *
     * @return The null item count.
     */
    public int getNullCount() {
        return nullCount;
    }

    /**
     * Getter method to receive the sum of item and null item count.
     *
     * @return The sum of the null item count and the item count.
     */
    public int getTotalCount() {
        return nullCount + itemCount;
    }

    /**
     * Adds a new object to the storage if the value does not already exist.
     * If the new object o is null, the count of nulls are incremented.
     *
     * @param o The object to find from storage.
     * @return True if add operation was successful and the string item did not
     *         exist.
     */
    public boolean add(T o) throws StorageHasBeenModifiedException {
        if(!iterator.getIfOpsAllowed()) {
            throw new StorageHasBeenModifiedException("Cannot modify storage " +
                    "during iterator activity.");
        } else if(o == null) {
            nullCount++;
            return true;
        } else if (isSetBehavior) {
            if (!isObjectStored(o)) {
                itemCount++;
                int insertIdx = findSortedStorageIdx(o);
                storage = ArrayOperator.insertItemAtIdx(storage, o, insertIdx);
                return true;
            } else {
                return false;
            }
        } else {
            itemCount++;
            int insertIdx = findSortedStorageIdx(o);
            storage = ArrayOperator.insertItemAtIdx(storage, o, insertIdx);
            return true;
        }
    }

    /**
     * Deletes an object from the storage if exists.
     *
     * @param o The object to remove from storage.
     * @return True if the object exists in storage and is removed, if not it
     *         returns false.
     */
    public boolean delete(T o) throws StorageHasBeenModifiedException {
        if(!iterator.getIfOpsAllowed()) {
            throw new StorageHasBeenModifiedException("Cannot modify storage " +
                    "during iterator activity.");
        } else if(o == null) {
            if(nullCount == 0){
                return false;
            } else {
                nullCount--;
                return true;
            }
        } else if(isObjectStored(o)) {
            itemCount--;
            int deleteIdx = getObjectIndex(o);
            storage = ArrayOperator.deleteItemAtIdx(storage, deleteIdx);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deletes an item where the cursor of the iterator points to.
     */
    public void delete() {
        if(iterator == null) {
            throw new NullPointerException("iterator not initialized.");
        } else {
            itemCount--;
            int idx = iterator.getCursorIdx();
            storage = ArrayOperator.deleteItemAtIdx(storage, idx);
        }
    }

    /**
     * Checks if the storage array contains an object item.
     *
     * @param o The object to find from storage.
     * @return True if storage contains the object item, if not, false.
     */
    public boolean find(T o) {
        if(o == null) {
            return includesNull();
        } else {
            boolean foundObject = false;
            for(Object storedObject: storage) {
                if (o.equals(storedObject)) {
                    foundObject = true;
                    break;
                }
            }
            return foundObject;
        }
    }

    /**
     * Checks if there are any null values saved in storage.
     *
     * @return True if storage contains any null values.
     */
    public boolean includesNull() {
        return nullCount > 0;
    }

    /**
     * Converts items in storage in a string format. Null values are printed
     * separately than saved objects.
     *
     * @return The formatted string populated with the contents of the storage.
     */
    @Override
    public String toString() {
        StringBuilder returnStr = new StringBuilder("\n\tValues: ");

        for (Object o : storage) {
            if (o == null) {
                continue;
            }
            returnStr.append(o).append(" ");
        }

        return returnStr + "\n\t"
                + String.format("Number of null elements saved: %d", nullCount);
    }

    /**
     * Compares the SortedStorage to another SortedStorage
     * instance. Compares length, then amount of null values, and item count
     * to determine which is greater.
     *
     * @param o the object to be compared.
     * @return The integer values -1, 0, or 1, meaning less than, equal, and
     *         greater than.
     */
    @Override
    public int compareTo(SortedStorage<T> o) {

        if(getTotalCount() > o.getTotalCount()) {
            return 1;
        } else if(getTotalCount() < o.getTotalCount()) {
            return -1;
        } else {
            if(getNullCount() > o.getNullCount()) {
                return 1;
            } else if(getNullCount() < o.getNullCount()) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * Instantiates a new iterator of sorted storages and returns the object.
     * @return
     */
    public Iterator<T> iterator() {
        iterator = new SortedStorageIterator<>(this);
        return iterator;
    }
}
