package queues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

/**
 * Created by alla.hranatova on 09-10-2016.
 * <p>
 * queues.Subset takes a command-line integer k;
 * reads in a sequence of N strings from standard input using StdIn.readString();
 * and prints out exactly k of them, uniformly at random.
 * Each item from the sequence can be printed out at most once.
 * 0 ≤ k ≤ n, where N is the number of string on standard input.
 */
public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        if (k == 0) {
            return;
        }
        int n = 0;
        while (!StdIn.isEmpty()) {
            String newItem = StdIn.readString();
            if (queue.size() == k) {
                int random = StdRandom.uniform(0, n + 1);
                String oldItem = queue.dequeue();
                newItem = random < k ? newItem : oldItem;
            }
            queue.enqueue(newItem);
            n++;
        }
        Iterator<String> it = queue.iterator();
        for (int i = 0; i < k; i++) {
            StdOut.println(it.next());
        }
    }
}
