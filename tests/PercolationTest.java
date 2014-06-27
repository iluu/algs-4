import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class PercolationTest {

    @Test
    public void shouldOpenClosedSite() throws Exception {
        Percolation percolation = new Percolation(2);
        assertThat(percolation.isOpen(1, 1), is(false));

        percolation.open(1, 1);
        assertThat(percolation.isOpen(1, 1), is(true));
    }

    @Test
    public void shouldNotPercolate() throws Exception {
        Percolation percolation = new Percolation(2);
        assertFalse(percolation.percolates());

        percolation.open(2,2);
        assertFalse(percolation.isFull(2,2));
        assertFalse(percolation.percolates());
    }

    @Test
    public void shouldAllFirstRowOpenSitesBeFull() throws Exception {
        Percolation percolation = new Percolation(10);
        assertThat(percolation.isOpen(1, 1), is(false));
        assertThat(percolation.isFull(1, 1), is(false));

        percolation.open(1, 1);
        assertTrue(percolation.isFull(1, 1));
    }

    @Test
    public void shouldPercolateForSingleSite() throws Exception {
        Percolation percolation = new Percolation(1);

        percolation.open(1, 1);
        assertTrue(percolation.percolates());
    }

    @Test
    public void shouldFillOpenNeighbour() throws Exception {
        Percolation percolation = new Percolation(2);

        percolation.open(1, 1);
        percolation.open(2, 2);
        percolation.open(1, 2);
        assertTrue(percolation.percolates());
    }

    @Test
    public void shouldPropagatePercolation() throws Exception {
        Percolation percolation = new Percolation(3);

        percolation.open(1, 3);
        percolation.open(2, 3);
        percolation.open(3, 3);

        assertTrue(percolation.percolates());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowOutOfBoundsExceptionFromIsOpen() throws Exception {
        Percolation percolation = new Percolation(10);
        percolation.isOpen(0, 6);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowOutOfBoundsExceptionFromIsFull() throws Exception {
        Percolation percolation = new Percolation(10);
        percolation.isFull(6, 11);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionFromConstructor() throws Exception {
        new Percolation(0);
    }

}
