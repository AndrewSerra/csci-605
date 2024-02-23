package homework_6;

/**
 * This is a class that allows saving any type of object sorted that are unique.
 * The behavior is similar to sets. The number of null values being added and
 * removed are being tracked along with the unique strings. This is a generic
 * class.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class UniqueSortedStorage<T extends Comparable<T>>
        implements SortedStorage<T>,
        Comparable<UniqueSortedStorage<T>> {

    private int itemCount = 0;
    private int nullCount = 0;

    Object[] storage = new Object[2];

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
    private int findSortedStorageIdx(T o) {
        int idx = 0;
        while(idx < storage.length) {
            // Exit if end of the storage is received before a target value
            if(storage[idx] == null) {
                break;
            }

            if(o.compareTo((T) storage[idx]) >= 0) {
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
    public boolean add(T o) {
        if(o == null) {
            nullCount++;
            return true;
        } else if(!isObjectStored(o)) {
            itemCount++;
            int insertIdx = findSortedStorageIdx(o);
            storage = ArrayOperator.insertItemAtIdx(storage, o, insertIdx);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Deletes an object from the storage if exists.
     *
     * @param o The object to remove from storage.
     * @return True if the object exists in storage and is removed, if not it
     *         returns false.
     */
    public boolean delete(T o) {
        if(o == null) {
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
        String returnStr = "\n\tValues: ";

        for (Object o : storage) {
            if (o == null) {
                continue;
            }
            returnStr = returnStr + o + " ";
        }

        return returnStr + "\n\t"
                + String.format("Number of null elements saved: %d", nullCount);
    }

    /**
     * Compares the UniqueSortedStorage to another UniqueSortedStorage
     * instance. Compares length, then amount of null values, and item count
     * to determine which is greater.
     *
     * @param o the object to be compared.
     * @return The integer values -1, 0, or 1, meaning less than, equal, and
     *         greater than.
     */
    @Override
    public int compareTo(UniqueSortedStorage<T> o) {

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
}
