public class Percolation {

    private boolean percolates;
    private int dimenN;
    private boolean[] openSites;
    private WeightedQuickUnionUF quickUnion;

    /**
     * Create N by B grid, with all sites blocked
     */
    public Percolation(int N) {
        dimenN = N;
        openSites = new boolean[(N+1) * (N+1)];
        quickUnion = new WeightedQuickUnionUF((N + 1) * (N + 1));
    }

    /**
     * Open site at i, j if it is not already open
     *
     * @param x row
     * @param y column
     */
    public void open(int x, int y) {
        if (!isValid(x, y)) {
            throw new IndexOutOfBoundsException(x + " or " + y + " exceeds bounds 1 < " + dimenN);
        }

        int siteId = xyTo1D(x, y);
        openSites[siteId] = true;

        if (isFirstRow(x)) {
            quickUnion.union(siteId, 0);
        }

        if(isLastRow(x)){
            percolates = quickUnion.connected(siteId, 0);
        }

        // neighbours
        fillNeighbour(siteId, x-1, y);
        fillNeighbour(siteId, x+1, y);
        fillNeighbour(siteId, x, y + 1);
        fillNeighbour(siteId, x, y - 1);
    }

    private void fillNeighbour(int siteId, int newX, int newY){
        if (isValid(newX, newY) && isOpen(newX, newY)) {
            int newSiteId = xyTo1D(newX, newY);
            quickUnion.union(newSiteId, siteId);
            if(isLastRow(newX)){
                percolates = quickUnion.connected(newSiteId, 0);
            }
        }
    }

    private boolean isFirstRow(int x) {
        return x == 1;
    }

    private boolean isLastRow(int x) {
        return x == dimenN;
    }

    protected int xyTo1D(int x, int y) {
        int id= (x - 1) + (y - 1) * dimenN + 1;
        //System.out.println("SideID: " + id + "(x: " + x + ", y: " +y + ")" );
        return id;
    }

    private boolean isValid(int x, int y) {
        return x <= dimenN && x >= 1 && y <= dimenN && y >= 1;
    }

    /**
     * @return true if site at x, y is open, false otherwise
     */
    public boolean isOpen(int x, int y) {
        return openSites[xyTo1D(x, y)];
    }

    /**
     * Full site is a site, that can be connected to the top row
     *
     * @return true if site at i, j is full, false otherwise
     */
    public boolean isFull(int x, int y) {
        return quickUnion.connected(xyTo1D(x, y), 0);
    }

    /**
     * @return true if system percolates, false otherwise
     */
    public boolean percolates() {
        return percolates;
    }

}
