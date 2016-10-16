package interview.questions.queues;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

/**
 * Created by alla.hranatova on 10/15/2016.
 * <p>
 * Stack with max. Create a data structure that efficiently supports
 * the stack operations (push and pop) and also a return-the-maximum operation.
 * Assume the elements are reals numbers so that you can compare them.
 */
public class StackWithMax {

    private Node headOfStack;
    private Node sortedNodes;
    private int size;

    private class Node {
        private Number item;
        private Node next;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void push(Number number) {
        Node newNode = new Node();
        newNode.item = number;
        newNode.next = headOfStack;
        headOfStack = newNode;


        Number max;
        Node newMax = new Node();
        if (sortedNodes != null) {
            max = sortedNodes.item.doubleValue() > newNode.item.doubleValue() ? sortedNodes.item : newNode.item;
        } else {
            max = newNode.item;
        }
        newMax.item = max;
        newMax.next = sortedNodes;
        sortedNodes = newMax;
        size++;
    }

    public Number pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Number first = headOfStack.item;
        headOfStack = headOfStack.next;
        sortedNodes = sortedNodes.next;
        size--;
        return first;
    }

    public Number max() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return sortedNodes.item;
    }

    public static void main(String[] args) {
        StackWithMax stack = new StackWithMax();
        stack.push(2);
        stack.push(4);
        stack.push(3);
        stack.push(1);
        assertEquals(4, stack.max());
        assertEquals(1, stack.pop());
        assertEquals(4, stack.max());
        stack.push(5);
        assertEquals(5, stack.max());
        assertEquals(5, stack.pop());
        assertEquals(3, stack.pop());
        assertEquals(4, stack.pop());
        assertEquals(2, stack.max());
    }
}
