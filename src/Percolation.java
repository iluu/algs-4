import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean percolates;
    private int dimenN;
    private boolean[] openSites;
    private boolean[] connectedToBottom;
    private WeightedQuickUnionUF quickUnion;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be > 0");
        }

        dimenN = n;
        openSites = new boolean[(n + 1) * (n + 1)];
        quickUnion = new WeightedQuickUnionUF((n + 1) * (n + 1));
        connectedToBottom = new boolean[(n + 1) * (n + 1)];
    }

    /**
     * Open site (row, col) if it is not already open
     */
    public void open(int row, int col) {
        if (!isValid(row, col)) {
            throw new IndexOutOfBoundsException(row + ", or " + col + " > " + dimenN);
        }

        int siteId = xyTo1D(row, col);
        openSites[siteId] = true;

        if (isFirstRow(row)) {
            quickUnion.union(siteId, 0);
        }

        // neighbours
        fillNeighbour(siteId, row - 1, col);
        fillNeighbour(siteId, row + 1, col);
        fillNeighbour(siteId, row, col + 1);
        fillNeighbour(siteId, row, col - 1);

        if (isLastRow(row)) {
            connectToBottom(siteId);
        }

        if (isConnectedToBottom(siteId) && !percolates) {
            percolates = quickUnion.connected(siteId, 0);
        }
    }

    private void fillNeighbour(int siteId, int newX, int newY) {
        if (isValid(newX, newY) && isOpen(newX, newY)) {
            int newSiteId = xyTo1D(newX, newY);

            if (isConnectedToBottom(newSiteId)) {
                connectToBottom(siteId);
            }

            quickUnion.union(newSiteId, siteId);
        }
    }

    private void connectToBottom(int siteId) {
        connectedToBottom[quickUnion.find(siteId)] = true;
    }

    private boolean isConnectedToBottom(int siteId) {
        return connectedToBottom[quickUnion.find(siteId)];
    }

    private boolean isLastRow(int row) {
        return row == dimenN;
    }

    private boolean isFirstRow(int row) {
        return row == 1;
    }

    private int xyTo1D(int row, int col) {
        return (row - 1) + (col - 1) * dimenN + 1;
    }

    private boolean isValid(int row, int col) {
        return row <= dimenN && row >= 1 && col <= dimenN && col >= 1;
    }

    /**
     * @return true if site at row, col is open, false otherwise
     */
    public boolean isOpen(int row, int col) {
        if (!isValid(row, col)) {
            throw new IndexOutOfBoundsException(row + ", or " + col + " > " + dimenN);
        }

        return openSites[xyTo1D(row, col)];
    }

    /**
     * @return true if site at row, col is full, false otherwise
     */
    public boolean isFull(int row, int col) {
        if (!isValid(row, col)) {
            throw new IndexOutOfBoundsException(row + ", or " + col + " > " + dimenN);
        }

        return quickUnion.connected(xyTo1D(row, col), 0);
    }

    /**
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        int result = 0;
        for (boolean openSite : openSites) {
            if (openSite) {
                result++;
            }
        }
        return result;
    }

    /**
     * @return true if system percolates, false otherwise
     */
    public boolean percolates() {
        return percolates;
    }
}
