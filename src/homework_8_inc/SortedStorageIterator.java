package homework_8_inc;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an iterator class that accepts a SortedStorage object and returns
 * a value in the storage. Does not return null values.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class SortedStorageIterator<T extends Comparable<T>> implements Iterator<T> {

    private SortedStorage<T> storage;
    private int cursorIdx = 0;
    private boolean isRemoveAllowed = true;

    private boolean isOperationsAllowed = true;
    public boolean ended = false;

    public SortedStorageIterator(SortedStorage<T> s) {
        storage = s;
    }

    /**
     * A getter method to return the current cursor location.
     * @return current cursor location
     */
    public int getCursorIdx() {
        return cursorIdx;
    }

    /**
     * A getter method to return the flag if operations on the storage is
     * allowed.
     * @return the flag for operations being executed
     */
    public boolean getIfOpsAllowed() {
        return isOperationsAllowed;
    }

    /**
     * Checks if there are additional data to be returned. The operations are
     * allowed if the iteration through the whole storage is completed.
     * @return true if not the end, false, is not.
     */
    @Override
    public boolean hasNext() {
        ended = !((storage.getItemCount() > 0) && (storage.getItemCount() > cursorIdx));
        isOperationsAllowed = ended;
        return !ended;
    }

    /**
     * Returns the current value the cursor points to, then increments the
     * cursor location.
     * @return The value at the current storage index.
     */
    @Override
    public T next() {
        if(!hasNext()) {
            throw new NoSuchElementException("Cursor index is at the end of storage.");
        }
        isRemoveAllowed = true;
        return storage.getObjectAtIdx(cursorIdx++);
    }

    /**
     * Removes an object from the storage where the cursor is located. The
     * value is only removed if there were no other calls to remove while
     * pointing to the same object.
     */
    @Override
    public void remove() {
        if(isRemoveAllowed) {
            storage.delete();
            isRemoveAllowed = false;
        } else {
            throw new IllegalStateException("Cannot run remove operation for " +
                    "same object.");
        }
    }
}
