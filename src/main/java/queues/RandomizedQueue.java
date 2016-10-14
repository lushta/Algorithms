package queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by alla.hranatova on 10/09/2016.
 * <p>
 * A randomized queue is similar to a stack or queue,
 * except that the item removed is chosen uniformly at random from items in the data structure.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int size;

    /**
     * Construct an empty randomized queue
     */
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }

    /**
     * Is the queue empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the number of items on the queue
     */
    public int size() {
        return size;
    }

    /**
     * Add the item
     */
    public void enqueue(Item item) {
        validate(item);
        size++;
        if (size == queue.length) {
            recreateQueue();
        }
        queue[size - 1] = item;
    }

    /**
     * Remove and return a random item
     */
    public Item dequeue() {
        validateBeforeRemoving();

        int elementIndex = StdRandom.uniform(0, size);
        Item element = queue[elementIndex];
        if (size > 1) {
            queue[elementIndex] = queue[size - 1];
            queue[size - 1] = null;
        }
        size--;
        if (size == queue.length / 4) {
            recreateQueue();
        }
        return element;
    }

    /**
     * Return (but do not remove) a random item
     */
    public Item sample() {
        validateBeforeRemoving();
        return queue[StdRandom.uniform(0, size)];
    }

    /**
     * Return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        int[] order = new int[size];
        for (int i = 0; i < size; i++) {
            order[i] = i;
        }
        StdRandom.shuffle(order);
        return new Iterator<Item>() {
            private int current = 0;
            @Override
            public boolean hasNext() {
                return current < size;
            }
            @Override
            public Item next() {
                if (current >= size) {
                    throw new NoSuchElementException();
                }
                return queue[order[current++]];
            }
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private void validate(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }

    private void validateBeforeRemoving() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    private void recreateQueue() {
        Item[] newQueue;
        if (size > 0) {
            newQueue = (Item[]) new Object[size * 2];
            System.arraycopy(queue, 0, newQueue, 0, size);
            queue = newQueue;
        } else {
            queue = (Item[]) new Object[1];
        }
    }

    public static void main(String[] args) {
    }
}
