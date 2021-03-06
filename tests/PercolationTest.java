import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class PercolationTest {

    @Test
    public void shouldOpenClosedSite() {
        Percolation percolation = new Percolation(2);
        assertThat(percolation.isOpen(1, 1), is(false));

        percolation.open(1, 1);
        assertThat(percolation.isOpen(1, 1), is(true));
    }

    @Test
    public void shouldReturnNoOpenSitesForNewPercolation() {
        Percolation percolation = new Percolation(2);

        assertThat(percolation.numberOfOpenSites(), is(0));
    }

    @Test
    public void shouldReturnCorrectNumberOfOpenSites() {
        Percolation percolation = new Percolation(2);
        percolation.open(1, 1);

        assertThat(percolation.numberOfOpenSites(), is(1));
    }

    @Test
    public void shouldNotPercolate() {
        Percolation percolation = new Percolation(2);
        assertFalse(percolation.percolates());

        percolation.open(2, 2);
        assertFalse(percolation.isFull(2, 2));
        assertFalse(percolation.percolates());
    }

    @Test
    public void shouldAllFirstRowOpenSitesBeFull() {
        Percolation percolation = new Percolation(10);
        assertThat(percolation.isOpen(1, 1), is(false));
        assertThat(percolation.isFull(1, 1), is(false));

        percolation.open(1, 1);
        assertTrue(percolation.isFull(1, 1));
    }

    @Test
    public void shouldPercolateForSingleSite() {
        Percolation percolation = new Percolation(1);

        percolation.open(1, 1);
        assertTrue(percolation.percolates());
    }

    @Test
    public void shouldFillOpenNeighbour() {
        Percolation percolation = new Percolation(2);

        percolation.open(1, 1);
        percolation.open(2, 2);
        percolation.open(1, 2);
        assertTrue(percolation.percolates());
    }

    @Test
    public void shouldPropagatePercolation() {
        Percolation percolation = new Percolation(3);

        percolation.open(1, 3);
        percolation.open(3, 3);
        percolation.open(2, 3);

        assertTrue(percolation.percolates());
    }

    @Test
    public void shouldPropagatePercolationForBigDimens() {
        Percolation percolation = new Percolation(5);

        percolation.open(4, 5);
        percolation.open(5, 5);
        percolation.open(2, 5);
        assertFalse(percolation.percolates());

        percolation.open(1, 5);
        percolation.open(3, 5);
        assertTrue(percolation.percolates());
    }

    @Test
    public void shouldNotBackwash() {
        Percolation percolation = new Percolation(3);

        percolation.open(1, 3);
        percolation.open(2, 3);
        percolation.open(3, 3);

        percolation.open(3, 1);
        assertFalse(percolation.isFull(3, 1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowOutOfBoundsExceptionFromIsOpen() {
        Percolation percolation = new Percolation(10);
        percolation.isOpen(0, 6);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowExceptionWhenTryingToOpenSiteAboveSizeLimit() {
        Percolation percolation = new Percolation(10);
        percolation.isOpen(1, 11);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowOutOfBoundsExceptionFromIsFull() {
        Percolation percolation = new Percolation(10);
        percolation.isFull(6, 11);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowExceptionWhenCheckingIsFullWithInvalidIndex() {
        Percolation percolation = new Percolation(10);
        percolation.isFull(0, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionFromConstructor() {
        new Percolation(0);
    }

}
