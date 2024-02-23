package homework_10;

import java.util.LinkedList;

/**
 * This is simple queue with only enqueue and dequeue methods that utilizes a
 * linked list. This queue contains integer arrays of size 2. For each item
 * the index of the item read and the digit value to process is stored.
 *
 * @author Andrew Serra
 * @author Anindhya Kushagra
 */
public class ProcessQueue {

    private final LinkedList<Integer[]> queue = new LinkedList<>();
    int maxSize;

    public ProcessQueue(int maxLength) {
        maxSize = maxLength;
    }

    /**
     * Adds a given index and value to the end of the linked list as an
     * integer array.The index is the first item, and the value is the second.
     *
     * @param index - Order of the item read from System.in or data file
     * @param value = The digit read from System.in or data file
     * @throws InvalidOperationException
     */
    public void enqueue(int index, int value) throws InvalidOperationException {
        if(isFull()) {
            throw new InvalidOperationException("Queue at max capacity.");
        }
        queue.add(new Integer[]{index, value});
    }

    /**
     * Removed the head of the linked list and returns its value.
     *
     * @return The value of the head node of the linked list.
     * @throws InvalidOperationException
     */
    public Integer[] dequeue() throws InvalidOperationException {
        if(isEmpty()) {
            throw new InvalidOperationException("Queue is empty.");
        }
        return queue.remove();
    }

    /**
     * Checks if the maximum capacity is reached for the queue.
     *
     * @return True if maximum value equals the current size.
     */
    public boolean isFull() {
        return maxSize == queue.size();
    }

    /**
     * Checks if there are any items left in the linked list.
     *
     * @return True if there are no items left, false if there are.
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

