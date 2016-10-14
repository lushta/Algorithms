package queues;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by alla.hranatova on 10/09/2016.
 */
public class TestDeque {

    private static final int INITIAL_SIZE = 20;
    private Deque<Integer> deque;

    @Before
    public void setup() {
        initDeque();
    }

    private void initDeque() {
        deque = new Deque<>();
        for (int i = 0; i < INITIAL_SIZE; i++) {
            deque.addLast(i);
        }
    }

    @Test
    public void testIsEmpty() {
        assertFalse(deque.isEmpty());
        for (int i = 0; i < INITIAL_SIZE; i++) {
            deque.removeFirst();
        }
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(INITIAL_SIZE, deque.size());
        for (int i = 0; i < INITIAL_SIZE; i++) {
            deque.removeFirst();
        }
        assertEquals(0, deque.size());
    }

    @Test
    public void testAddFirst() {
        Integer testNumber = 156;
        deque.addFirst(testNumber);
        assertEquals(testNumber, deque.iterator().next());
        assertEquals(INITIAL_SIZE + 1, deque.size());
    }

    @Test
    public void testAddLast() {
        Integer testNumber = 2050;
        deque.addLast(testNumber);
        assertEquals(testNumber, deque.removeLast());
        assertEquals(INITIAL_SIZE, deque.size());
    }

    @Test
    public void testRemoveFirst() {
        assertEquals(new Integer(0), deque.removeFirst());
        assertEquals(INITIAL_SIZE - 1, deque.size());
    }

    @Test
    public void testRemoveLast() {
        assertEquals(new Integer(INITIAL_SIZE - 1), deque.removeLast());
        assertEquals(INITIAL_SIZE - 1, deque.size());
    }

    @Test
    public void testIterator() {
        Iterator<Integer> it = deque.iterator();
        for (Integer i = 0; i < INITIAL_SIZE; i++) {
            assertEquals(i, it.next());
        }
        assertFalse(it.hasNext());
    }

    @Test
    public void createDeque(){
        Deque<Integer> testDeque = new Deque<>();
        testDeque.addFirst(0);
        testDeque.addFirst(1);
        testDeque.addLast(2);
        testDeque= new Deque<>();
        testDeque.isEmpty();
        testDeque.addFirst(1);
        testDeque.removeFirst();
        testDeque.addLast(3);
        testDeque.addFirst(4);
        testDeque.isEmpty();
        testDeque.addLast(6);
    }

}
