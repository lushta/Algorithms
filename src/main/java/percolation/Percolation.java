package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by alla.hranatova on 10/8/2016.
 * <p>
 * Percolation system is modeled using an N-by-N grid of sites.
 * Each site is either open or blocked.
 * A full site is an open site that can be connected to an open site in the top row
 * via a chain of neighboring (left, right, up, down) open sites.
 * We say the system percolates if there is a full site in the bottom row.
 * In other words, a system percolates if we fill all open sites connected to the top row
 * and that process fills some open site on the bottom row.
 * <p>
 * The row and column indices i and j are integers between 1 and n,
 * where (1, 1) is the upper-left site
 */
public class Percolation {

    private static final byte CLOSED = 0;
    private static final byte OPEN = 1;
    private static final byte TOP = 101;
    private static final byte BOTTOM = 11;
    private static final byte TOP_BOTTOM = 111;

    private byte[] states;
    private int size;
    private boolean isPercolates;
    private WeightedQuickUnionUF quickUnionUF;

    /**
     * Create n-by-n grid, with all sites blocked
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        quickUnionUF = new WeightedQuickUnionUF(n * n);
        size = n;
        states = new byte[n * n];
    }

    /**
     * Open site (row i, column j) if it is not open already
     */
    public void open(int i, int j) {
        validate(i, j);
        int current = getIndex(i, j);
        if (states[current] == CLOSED) {
            byte resultState = getInitialState(current);
            // connect to top
            if (current > size - 1) {
                resultState = (byte) (resultState | connect(current, current - size));
            }
            // connect to bottom
            if (current < size * size - size) {
                resultState = (byte) (resultState | connect(current, current + size));
            }
            // connect to left
            if (current % size != 0) {
                resultState = (byte) (resultState | connect(current, current - 1));
            }
            // connect to right
            if ((current + 1) % size != 0) {
                resultState = (byte) (resultState | connect(current, current + 1));
            }
            int root = quickUnionUF.find(current);
            states[root] = (byte) (resultState | states[root]);
            if (!isPercolates && states[root] >= TOP_BOTTOM) {
                isPercolates = true;
            }
        }
    }


    /**
     * Is site (row i, column j) open
     */
    public boolean isOpen(int i, int j) {
        validate(i, j);
        return states[getIndex(i, j)] > CLOSED;
    }

    /**
     * Is site (row i, column j) full
     */
    public boolean isFull(int i, int j) {
        validate(i, j);
        return states[quickUnionUF.find(getIndex(i, j))] >= TOP;
    }

    /**
     * Returns true if system percolates
     */
    public boolean percolates() {
        return isPercolates;
    }


    private void validate(int i, int j) {
        if (i <= 0 || j <= 0 || i > size || j > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private byte getInitialState(int index) {
        byte state;
        if (size == 1) {
            state = TOP_BOTTOM;
        } else if (index < size) {
            state = TOP;
        } else if (index >= size * size - size) {
            state = BOTTOM;
        } else {
            state = OPEN;
        }
        states[index] = state;
        return state;
    }

    private byte connect(int current, int target) {
        byte state = CLOSED;
        if (states[target] != CLOSED) {
            state = states[quickUnionUF.find(target)];
            quickUnionUF.union(current, target);
        }
        return state;
    }

    private int getIndex(int i, int j) {
        return (i - 1) * size + (j - 1);
    }

}
