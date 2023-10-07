package homework_4;

/**
 * This is a class that allows saving strings that are unique. The behavior
 * is similar to sets. The number of null values being added and removed are
 * being tracked along with the unique strings.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class SortedStorage {

    int nullCount = 0;

    String[] storage = new String[2];

    /**
     * Looks for the index where the string s should be inserted to have a
     * sorted storage array.
     *
     * @param s The string value to find the index of where to insert the
     *          string s into storage.
     * @return The index to add the string into storage array.
     */
    private int findSortedStorageIdx(String s) {
        int idx = 0;

        while(idx < storage.length) {
            if(storage[idx] == null || s.compareTo(storage[idx]) <= 0) {
                break;
            }
            idx++;
        }
        return idx;
    }

    /**
     * Checks if the string s exists in the storage.
     *
     * @param s The string value to find from storage.
     * @return True if the value is stored, false if not.
     */
    private boolean isStrStored(String s) {
        boolean itemExists = false;

        if(s == null) {
            return nullCount > 0;
        }

        for(String str : storage) {
            if (str != null && str.equals(s)) {
                itemExists = true;
                break;
            }
        }
        return itemExists;
    }

    /**
     * Iterates through the storage to find the index of a given string.
     *
     * @param s The string value to find from storage.
     * @return The index of the string s stored in the storage array.
     */
    private int getStrIndex(String s) {
        int retStringIdx = -1;
        for(int i=0; i < storage.length; i++) {
            if(s.equals(storage[i])) {
                retStringIdx = i;
            }
        }
        return retStringIdx;
    }

    /**
     * Converts items in storage in a string format. Null values are printed
     * separately than string values.
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
     * Adds a new string to the storage if the value does not already exist.
     * If the new string s is null, the count of nulls are incremented.
     *
     * @param s The string value to find from storage.
     * @return True if add operation was successful and the string s did not
     *         exist.
     */
    public boolean add(String s) {
        if(s == null) {
            nullCount++;
            return true;
        } else if(!isStrStored(s)) {
            int insertIdx = findSortedStorageIdx(s);
            storage = ArrayOperator.insertItemAtIdx(storage, s, insertIdx);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the storage array contains a string value s.
     *
     * @param s The string value to find from storage.
     * @return True if storage contains the string s, if not, false.
     */
    public boolean find(String s) {
        if(s == null) {
            return includesNull();
        } else {
            boolean foundString = false;
            for(String storedStr: storage) {
                if (s.equals(storedStr)) {
                    foundString = true;
                    break;
                }
            }
            return foundString;
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
     * Deletes a string s from the storage if exists.
     *
     * @param s The string value to remove from storage.
     * @return True if the string exists in storage and is removed, if not it
     *         returns false.
     */
    public boolean delete(String s) {
        if(s == null) {
            nullCount--;
            return true;
        } else if(isStrStored(s)) {
            if(!isStrStored(s)) {
                return false;
            }
            int deleteIdx = getStrIndex(s);
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
 * available actions.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
class ArrayOperator {

    /**
     * This function takes an array of any size and doubles the length with
     * the values copied to the new array.
     * @param a String array containing string and null values. Null values
     *          are unused locations.
     * @return The expanded array with contents of the initial string array.
     */
    public static String[] expand(String[] a) {
        int currentLength = a.length;
        String[] newArr = new String[currentLength * 2];
        return transferToNew(a, newArr);
    }

    /**
     * Copies the values of one array into another.
     *
     * @param prevArr The string array that the values are copied from.
     * @param newArr The string array that the values are copied to.
     * @return New string array that contains the old values.
     */
    public static String[] transferToNew(String[] prevArr, String[] newArr) {
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
     * @param a String array containing string and null values. Null values
     *          are unused locations.
     * @param fromIndex The index to start shifting right from.
     * @return The string array with values shifted. Open spot is filled with
     *         a null value.
     */
    private static String[] shiftRightFromIndex(String[] a, int fromIndex) {
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
     * @param a String array containing string and null values. Null values
     *          are unused locations.
     * @param fromIndex The index to start shifting left from.
     * @return The string array with values shifted. Open spot is filled with
     *         a null value.
     */
    private static String[] shiftLeftFromIndex(String[] a, int fromIndex) {
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
     * Inserts a string at a given index of a string array.
     *
     * @param a String array containing string and null values. Null values
     *          are unused locations.
     * @param s New string to insert the array.
     * @param idx The index to insert the new string.
     * @return The array with the new string inserted at index idx.
     */
    public static String[] insertItemAtIdx(String[] a, String s, int idx) {
        if(idx > a.length-1 || idx < 0) {
            System.out.println("Index given for array shift is out of bounds.");
            return a;
        }
        a = shiftRightFromIndex(a, idx);
        a[idx] = s;
        return a;
    }

    /**
     * Removed a string at a given index from a string array.
     *
     * @param a String array containing string and null values. Null values
     *          are unused locations.
     * @param idx The index to insert the new string.
     * @return The array with the new string removed from index idx.
     */
    public static String[] deleteItemAtIdx(String[] a, int idx) {
        if(idx > a.length-1 || idx < 0) {
            System.out.println("Index given for array shift is out of bounds.");
            return a;
        }
        return shiftLeftFromIndex(a, idx);
    }
}
