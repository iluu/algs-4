public class PercolationStats {

    private int dimen;
    private int experiments;
    private double[] results;

    /**
     * Perform T independent computational experiments on an N-by-N grid
     *
     * @param N dimensions of the array
     * @param T number of experiments to run
     */
    public PercolationStats(int N, int T) {
        dimen = N;
        experiments = T;
        results = new double[T];
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
        return mean() - 1.96 * stddev() / Math.sqrt(dimen);
    }

    /**
     * @return upper bound of the 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(dimen);
    }

    /**
     * Test client
     */
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Try again: N <= 0 and T <= 0");
        }

        PercolationStats stats = new PercolationStats(N, T);
        stats.runExperiments();

        StdOut.printf("dimentions              %d\n", N);
        StdOut.printf("experiments             %d\n", T);
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
