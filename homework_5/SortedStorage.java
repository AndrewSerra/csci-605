package homework_5;

/**
 * This is a class that allows saving strings that are unique. The behavior
 * is similar to sets. The number of null values being added and removed are
 * being tracked along with the unique strings. This is a generic class where
 * it supports Integers, Strings, and SortedStorage.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class SortedStorage<T> {

    private int itemCount = 0;
    private int nullCount = 0;

    Object[] storage = new Object[2];

    /**
     * Compare sorted storages depending on their lengths. If they're the
     * same length, compare individual items. If the number of null values
     * are larger, it is considered smaller.
     *
     * @param storage1 SortedStorage object to make comparison
     * @param storage2 SortedStorage object to make comparison
     * @return 0 if equal or smaller storage1 is smaller, 1 if storage2
     */
    private int compareSortedStorage(SortedStorage<?> storage1,
                                     SortedStorage<?> storage2) {
        int length1 = storage1.getItemCount() + storage1.getNullCount();
        int length2 = storage2.getItemCount() + storage2.getNullCount();

        if(length1 < length2) {
            return 1;
        } else if(length1 > length2) {
            return 2;
        } else if(storage1.getNullCount() > storage2.getNullCount()) {
            return 1;
        } else if(storage1.getNullCount() < storage2.getNullCount()) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * Looks for the index where an object should be inserted to have a
     * sorted storage array.
     *
     * @param item The object to find the index of where to insert into
     *          storage.
     * @return The index to add into storage array.
     */
    private int findSortedStorageIdx(T item) {
        int idx = 0;

        while(idx < storage.length) {
            // Exit if end of the storage is received before a target value
            if(storage[idx] == null) {
                break;
            }
            if(item instanceof String) {
                if(((String) item).compareTo((String) storage[idx]) <= 0) {
                    break;
                }
            } else if(item instanceof Integer) {
                if((Integer) item <= (Integer)storage[idx]) {
                    break;
                }
            } else if(item instanceof SortedStorage<?>) {
                int comparisonResult =
                        compareSortedStorage(
                                (SortedStorage<?>) item,
                                (SortedStorage<?>) storage[idx]);
                if(comparisonResult == 0) {
                    break;
                }
            }

            idx++;
        }
        return idx;
    }

    /**
     * Checks if the object item exists in the storage.
     *
     * @param item The object to find from storage.
     * @return True if the value is stored, false if not.
     */
    private boolean isObjectStored(T item) {
        boolean itemExists = false;

        if(item == null) {
            return nullCount > 0;
        }

        for(Object obj : storage) {
            if(obj != null) {
                if (obj.equals(item)) {
                    itemExists = true;
                    break;
                }
            }
        }
        return itemExists;
    }

    /**
     * Iterates through the storage to find the index of a given object.
     *
     * @param item The object to find from storage.
     * @return The index of the object stored in the storage array.
     */
    private int getObjectIndex(T item) {
        int retStringIdx = -1;
        for(int i=0; i < storage.length; i++) {
            if((item instanceof String) || (item instanceof SortedStorage<?>)) {
                if (item.equals(storage[i])) {
                    retStringIdx = i;
                }
            } else if(item instanceof Integer) {
                if(item == storage[i]) {
                    retStringIdx = i;
                }
            }
        }
        return retStringIdx;
    }


    /**
     * Getter method that provides the amount of unique values in storage.
     *
     * @return Number of items in storage.
     */
    public int getItemCount() {
        return itemCount;
    }

    /**
     * Getter method that provides the amount of null values saved.
     *
     * @return Number of items saved as null.
     */
    public int getNullCount() {
        return nullCount;
    }

    /**
     * Converts items in storage in a string format. Null values are printed
     * separately than saved objects.
     *
     * @return The formatted string populated with the contents of the storage.
     */
    public String toString() {
        String returnStr = "\n\tValues: ";

        for(int i=0; i < storage.length; i++) {
            if(storage[i] == null) {
                continue;
            }
            returnStr = returnStr + storage[i] + " ";
        }

        return returnStr + "\n\t"
                + String.format("Number of null elements saved: %d", nullCount);
    }

    /**
     * Adds a new object to the storage if the value does not already exist.
     * If the new object item is null, the count of nulls are incremented.
     *
     * @param item The object to find from storage.
     * @return True if add operation was successful and the string item did not
     *         exist.
     */
    public boolean add(T item) {
        if(item == null) {
            nullCount++;
            return true;
        } else if(!isObjectStored(item)) {
            itemCount++;
            int insertIdx = findSortedStorageIdx(item);
            storage = ArrayOperator.insertItemAtIdx(storage, item, insertIdx);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the storage array contains an object item.
     *
     * @param item The object to find from storage.
     * @return True if storage contains the object item, if not, false.
     */
    public boolean find(T item) {
        if(item == null) {
            return includesNull();
        } else {
            boolean foundObject = false;
            for(Object storedObject: storage) {
                if (item.equals(storedObject)) {
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
     * Deletes an object from the storage if exists.
     *
     * @param item The object to remove from storage.
     * @return True if the object exists in storage and is removed, if not it
     *         returns false.
     */
    public boolean delete(T item) {
        if(item == null) {
            if(nullCount == 0){
                return false;
            } else {
                nullCount--;
                return true;
            }
        } else if(isObjectStored(item)) {
            itemCount--;
            int deleteIdx = getObjectIndex(item);
            storage = ArrayOperator.deleteItemAtIdx(storage, deleteIdx);
            return true;
        } else {
            return false;
        }
    }
}

/**
 * This is a utility class that allows operating on the array where storage
 * occurs. Doubling size, finding an element, insert, and delete are
 * available actions. All methods are generic.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
class ArrayOperator {

    /**
     * This function takes an array of any size and doubles the length with
     * the values copied to the new array.
     * @param a Object array containing string and null values. Null values
     *          are unused locations.
     * @return The expanded array with contents of the initial string array.
     */
    public static <T> T[] expand(T[] a) {
        int currentLength = a.length;
        @SuppressWarnings("unchecked")
        T[] newArr = (T[]) new Object[currentLength * 2];
        return transferToNew(a, newArr);
    }

    /**
     * Copies the values of one array into another.
     *
     * @param prevArr The Object array that the values are copied from.
     * @param newArr The Object array that the values are copied to.
     * @return New Object array that contains the old values.
     */
    public static <T> T[] transferToNew(T[] prevArr, T[] newArr) {
        for(int i=0; i < prevArr.length; i++) {
            newArr[i] = prevArr[i];
        }
        return newArr;
    }

    /**
     * Starting from the index provided, the values are moved one index to
     * the right. If the last index contains a non-null value, the array is
     * doubled in size.
     *
     * @param a Object array containing Object and null values. Null values
     *          are unused locations.
     * @param fromIndex The index to start shifting right from.
     * @return The Object array with values shifted. Open spot is filled with
     *         a null value.
     */
    private static <T> T[] shiftRightFromIndex(T[] a, int fromIndex) {
        if(fromIndex > a.length-1 || fromIndex < 0) {
            System.out.println("Index given for array shift is out of bounds.");
            return a;
        }

        if(a[a.length-1] != null) {
            a = expand(a);
        }

        for(int i=a.length-1; i > fromIndex; i--) {
            a[i] = a[i-1];
        }
        a[fromIndex] = null;
        return a;
    }

    /**
     * Starting from the index provided, the values are moved one index to
     * the left.
     *
     * @param a Object array containing Object and null values. Null values
     *          are unused locations.
     * @param fromIndex The index to start shifting left from.
     * @return The Object array with values shifted. Open spot is filled with
     *         a null value.
     */
    private static <T> T[] shiftLeftFromIndex(T[] a, int fromIndex) {
        if(fromIndex > a.length-1 || fromIndex < 0) {
            System.out.println("Index given for array shift is out of bounds.");
            return a;
        }

        for(int i=fromIndex; i < a.length-1; i++) {
            a[i] = a[i+1];
        }
        a[a.length-1] = null;
        return a;
    }

    /**
     * Inserts an Object at a given index of an Object array.
     *
     * @param a Object array containing Object and null values. Null values
     *          are unused locations.
     * @param item New Object to insert the array.
     * @param idx The index to insert the new Object.
     * @return The array with the new Object inserted at index idx.
     */
    public static <T> T[] insertItemAtIdx(T[] a, T item, int idx) {
        if(idx > a.length-1 || idx < 0) {
            System.out.println("Index given for array shift is out of bounds.");
            return a;
        }
        a = shiftRightFromIndex(a, idx);
        a[idx] = item;
        return a;
    }

    /**
     * Removed an Object at a given index from an Object array.
     *
     * @param a Object array containing Object and null values. Null values
     *          are unused locations.
     * @param idx The index to insert the new Object.
     * @return The array with the new Object removed from index idx.
     */
    public static <T> T[] deleteItemAtIdx(T[] a, int idx) {
        if(idx > a.length-1 || idx < 0) {
            System.out.println("Index given for array shift is out of bounds.");
            return a;
        }
        return shiftLeftFromIndex(a, idx);
    }
}
