package queues;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by alla.hranatova on 10-10-2016.
 */
public class TestRandomizedQueue {

    private static final int INITIAL_SIZE = 3;
    private RandomizedQueue<Integer> queue;

    @Before
    public void setup() {
        initQueue();
    }

    private void initQueue() {
        queue = new RandomizedQueue<>();
        for (int i = 0; i < INITIAL_SIZE; i++) {
            queue.enqueue(i);
        }
    }

    @Test
    public void testIsEmpty() {
        assertFalse(queue.isEmpty());
        for (int i = 0; i < INITIAL_SIZE; i++) {
            queue.dequeue();
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(INITIAL_SIZE, queue.size());
        for (int i = 0; i < INITIAL_SIZE; i++) {
            queue.dequeue();
        }
        assertEquals(0, queue.size());
    }

    @Test
    public void testEnqueue() {
        assertEquals(INITIAL_SIZE, queue.size());
        for (int i = 0; i < INITIAL_SIZE; i++) {
            queue.enqueue(i);
        }
        assertEquals(INITIAL_SIZE * 2, queue.size());
    }


    @Test
    public void testDequeue() {
        assertEquals(INITIAL_SIZE, queue.size());
        for (int i = 0; i < INITIAL_SIZE; i++) {
            queue.dequeue();
        }
        assertEquals(0, queue.size());
    }

    @Test
    public void testSubset() {
        int[] count = new int[10];
        for (int j = 0; j < 1000; j++) {
            RandomizedQueue<Integer> queue = new RandomizedQueue<>();
            int k = 1;
            if (k == 0) {
                return;
            }
            int n = 0;
            int[] testData = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
            while (n != testData.length) {
                int newItem = testData[n];
                if (queue.size() == k) {
                    int random = StdRandom.uniform(0, n + 1);
                    int oldItem = queue.dequeue();
                    newItem = random < k ? newItem : oldItem;
                }
                queue.enqueue(newItem);
                n++;
            }
            Iterator<Integer> it = queue.iterator();
            for (int i = 0; i < k; i++) {
                int r = it.next();
                count[r]++;
            }
        }
        StdOut.println(Arrays.toString(count));
    }

    @Test
    public void testSample() {
        assertTrue(queue.sample() < INITIAL_SIZE);
        assertEquals(INITIAL_SIZE, queue.size());
    }

    @Test
    public void testIterator() {
        Set<Integer> result1 = new HashSet<>();
        Set<Integer> result2 = new HashSet<>();
        Iterator<Integer> it1 = queue.iterator();
        Iterator<Integer> it2 = queue.iterator();
        while (it1.hasNext()) {
            result1.add(it1.next());
            result2.add(it2.next());
        }
        assertTrue(result1.containsAll(result2));

    }
}
