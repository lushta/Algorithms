package queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by alla.hranatova on 10/09/2016.
 * <p>
 * A double-ended queue or deque is a generalization of a stack and a queue
 * that supports adding and removing items from either the front or the back of the data structure.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> head;
    private int size;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;

        Node(Item item) {
            this.item = item;
        }
    }

    /**
     * Construct an empty deque
     */
    public Deque() {
    }

    /**
     * Is the deque empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the number of items on the deque
     */
    public int size() {
        return size;
    }

    /**
     * Add the item to the front
     */
    public void addFirst(Item item) {
        validate(item);
        if (size == 0) {
            head = new Node<>(item);
        } else {
            Node<Item> oldHead = head;
            head = new Node<>(item);
            head.next = oldHead;
            head.previous = oldHead.previous == null ? head.next : oldHead.previous;
            oldHead.previous = head;
        }
        size++;
    }

    /**
     * Add the item to the end
     */
    public void addLast(Item item) {
        validate(item);
        if (size == 0) {
            head = new Node<>(item);
        } else if (size == 1) {
            Node<Item> newTail = new Node<>(item);
            newTail.previous = head;
            head.next = newTail;
            head.previous = newTail;
        } else {
            Node<Item> oldTail = head.previous;
            Node<Item> newTail = new Node<>(item);
            newTail.previous = oldTail;
            oldTail.next = newTail;
            head.previous = newTail;
        }
        size++;
    }

    /**
     * Remove and return the item from the front
     */
    public Item removeFirst() {
        validateBeforeRemoving();
        Item item = head.item;
        if (size == 1) {
            head = null;
        } else if (size == 2) {
            head = head.next;
            head.next = null;
            head.previous = null;
        } else {
            Node<Item> tail = head.previous;
            head = head.next;
            head.previous = tail;
        }
        size--;
        return item;
    }

    /**
     * Remove and return the item from the end
     */
    public Item removeLast() {
        validateBeforeRemoving();
        Item item;
        if (size == 1) {
            item = head.item;
            head = null;
        } else if (size == 2) {
            item = head.previous.item;
            head.next = null;
            head.previous = null;
        } else {
            item = head.previous.item;
            head.previous.previous.next = null;
            head.previous = head.previous.previous;
        }
        size--;
        return item;
    }

    /**
     * Return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node<Item> current = head;
            @Override
            public boolean hasNext() {
                return current != null;
            }
            @Override
            public Item next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }
                Item result = current.item;
                current = current.next;
                return result;
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

    public static void main(String[] args) {
    }
}

