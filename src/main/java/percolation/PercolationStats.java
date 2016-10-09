package percolation;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by alla.hranatova on 10/8/2016.
 * <p>
 * Performs a series of computational experiments.
 * Repeats the following until the system percolates:
 * choose a site (row i, column j) uniformly at random among all blocked sites.
 * open the site (row i, column j).
 * <p>
 * The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.
 */
public class PercolationStats {

    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    /**
     * Perform trials independent experiments on an n-by-n grid
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Number of trials and size of grid should be greater than 0");
        }
        double[] fractionOfOpenSites = new double[trials];
        int sizeOfGrid = n * n;

        // perform trials
        for (int k = 0; k < trials; k++) {
            Percolation percolation = new Percolation(n);
            double numberOfOpenSites = 0;
            while (!percolation.percolates()) {
                openSite(percolation, n);
                numberOfOpenSites++;
            }
            fractionOfOpenSites[k] = numberOfOpenSites / sizeOfGrid;
        }
        this.mean = StdStats.mean(fractionOfOpenSites);
        this.stddev = StdStats.stddev(fractionOfOpenSites);
        this.confidenceLo = this.mean - (1.96 * this.stddev / Math.sqrt(trials));
        this.confidenceHi = this.mean + (1.96 * this.stddev / Math.sqrt(trials));
    }

    private void openSite(Percolation percolation, int n) {
        int i;
        int j;
        do {
            i = StdRandom.uniform(1, n + 1);
            j = StdRandom.uniform(1, n + 1);
        } while (percolation.isOpen(i, j));
        percolation.open(i, j);
    }

    /**
     * Sample mean of percolation threshold
     */
    public double mean() {
        return mean;
    }

    /**
     * Sample standard deviation of percolation threshold
     */
    public double stddev() {
        return stddev;
    }

    /**
     * Low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return confidenceLo;
    }

    /**
     * High endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return confidenceHi;
    }

    /**
     * Test client
     * Takes two command-line arguments n and trials, performs trials independent computational experiments (discussed above)
     * on an n-by-n grid,
     * and prints the mean, standard deviation, and the 95% confidence interval for the percolation threshold.
     */
    public static void main(String[] args) {
        int gridSize = StdIn.readInt();
        int numberOfTrials = StdIn.readInt();
        PercolationStats percolationStats = new PercolationStats(gridSize, numberOfTrials);
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());

    }
}
