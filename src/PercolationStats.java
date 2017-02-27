import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int dimen;
    private int experiments;
    private double[] results;

    /**
     * Perform trials independent experiments on an n-by-n grid
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Try again: N <= 0 and T <= 0");
        }

        dimen = n;
        experiments = trials;
        results = new double[trials];
        runExperiments();
    }

    /**
     * @return sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(results);
    }

    /**
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(results);
    }

    /**
     * @return lower bound of the 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(experiments);
    }

    /**
     * @return upper bound of the 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(experiments);
    }

    /**
     * Test client
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);

        StdOut.printf("mean                    %.20f\n", stats.mean());
        StdOut.printf("stddev                  %.20f\n", stats.stddev());
        StdOut.printf("95%% confidence interval %.20f, %.20f",
                stats.confidenceLo(), stats.confidenceHi());
    }

    private void runExperiments() {
        for (int i = 0; i < experiments; i++) {
            Percolation perc = new Percolation(dimen);
            double round = 0;
            while (!perc.percolates()) {
                int x, y;
                do {
                    x = StdRandom.uniform(dimen) + 1;
                    y = StdRandom.uniform(dimen) + 1;
                } while (perc.isOpen(x, y));

                perc.open(x, y);
                round++;
            }
            results[i] = round / (dimen * dimen);
        }
    }
}
