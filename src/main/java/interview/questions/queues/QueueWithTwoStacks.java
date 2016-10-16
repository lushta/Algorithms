package interview.questions.queues;

import edu.princeton.cs.algs4.Stack;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Created by alla.hranatova on 10/15/2016.
 * <p>
 * Queue with two stacks.
 * Implement a queue with two stacks so that each queue operations
 * takes a constant amortized number of stack operations.
 */
public class QueueWithTwoStacks<Item> {

    private Stack<Item> stackToPush;
    private Stack<Item> stackToPop;

    public QueueWithTwoStacks() {
        stackToPush = new Stack<>();
        stackToPop = new Stack<>();
    }

    public boolean isEmpty() {
        return stackToPush.isEmpty() && stackToPop.isEmpty();
    }

    public int size() {
        return stackToPush.size() + stackToPop.size();
    }

    /**
     * Removes and returns the object at the beginning of the Queue
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (stackToPop.isEmpty()) {
            while (!stackToPush.isEmpty()) {
                stackToPop.push(stackToPush.pop());
            }

        }
        return stackToPop.pop();
    }

    /**
     * Adds an object to the end of the Queue
     */

    public void enqueue(Item item) {
        Objects.nonNull(item);
        stackToPush.push(item);
    }

}
