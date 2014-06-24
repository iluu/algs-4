import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class PercolationTest {

    @Test
    public void shouldConvertDimensionsTo1D() throws Exception {
        Percolation percolation = new Percolation(10);
        // because 0 is not really useful
        assertThat(percolation.xyTo1D(1, 1), is(1));
        assertThat(percolation.xyTo1D(1, 2), is(11));
        assertThat(percolation.xyTo1D(2, 1), is(2));
    }

    @Test
    public void shouldOpenClosedSite() throws Exception {
        Percolation percolation = new Percolation(2);
        assertThat(percolation.isOpen(1, 1), is(false));

        percolation.open(1, 1);
        assertThat(percolation.isOpen(1,1), is(true));
    }

    @Test
    public void shouldAllFirstRowOpenSitesBeFull() throws Exception {
        Percolation percolation = new Percolation(10);
        assertThat(percolation.isOpen(1, 1), is(false));
        assertThat(percolation.isFull(1, 1), is(false));

        percolation.open(1,1);
        assertTrue(percolation.isFull(1, 1));
    }

    @Test
    public void shouldPercolateForSingleSite() throws Exception {
        Percolation percolation = new Percolation(1);

        percolation.open(1,1);
        assertTrue(percolation.percolates());
    }

    @Test
    public void shouldFillOpenNeighbour() throws Exception {
        Percolation percolation = new Percolation(2);

        percolation.open(1,1);
        percolation.open(2,2);
        percolation.open(1,2);
        assertTrue(percolation.percolates());
    }

    @Test
    public void shouldPropagatePercolation() throws Exception {
        Percolation percolation = new Percolation(3);

        percolation.open(1,3);
        percolation.open(2,3);
        percolation.open(3,3);

        assertTrue(percolation.percolates());
    }

}
