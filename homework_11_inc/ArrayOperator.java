package homework_11_inc;

import java.lang.reflect.Array;

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
        T[] newArr = (T[]) Array.newInstance(
                a.getClass().getComponentType(), currentLength * 2);
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
        if((fromIndex > a.length) || fromIndex < 0) {
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
        if(idx > (a.length) || idx < 0) {
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
        if(idx > (a.length-1) || idx < 0) {
            System.out.println("Index given for array shift is out of bounds.");
            return a;
        }
        return shiftLeftFromIndex(a, idx);
    }
}
