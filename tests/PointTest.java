import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PointTest {

    @Test
    public void compareToReturnsLessForSmallerY() {
        Point point1 = new Point(1, 1);
        Point point2 = new Point(1, 2);

        assertThat(point1.compareTo(point2), is(-1));
    }

    @Test
    public void compareToReturnsLessForSmallerYAndBiggerX() {
        Point point1 = new Point(2, 1);
        Point point2 = new Point(1, 2);

        assertThat(point1.compareTo(point2), is(-1));
    }

    @Test
    public void compareToReturnMoreForBiggerY() {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(1, 1);

        assertThat(point1.compareTo(point2), is(1));
    }

    @Test
    public void compareReturnsLessForEqualYAndSmallerX() {
        Point point1 = new Point(1, 1);
        Point point2 = new Point(2, 1);

        assertThat(point1.compareTo(point2), is(-1));
    }

    @Test
    public void compareReturnsMoreForEqualYAndBiggerX() {
        Point point1 = new Point(2, 1);
        Point point2 = new Point(1, 1);

        assertThat(point1.compareTo(point2), is(1));
    }

    @Test
    public void compareReturnsEqualForEqualYAndX() {
        Point point1 = new Point(1, 1);
        Point point2 = new Point(1, 1);

        assertThat(point1.compareTo(point2), is(0));
    }

    @Test
    public void slopeToForHorizontalLineIsZero() {
        Point point1 = new Point(1, 1);
        Point point2 = new Point(2, 1);

        double positiveZero = +0.0;
        double negativeZero = -0.0;

        assertThat(point1.slopeTo(point2), is(positiveZero));
        assertThat(point1.slopeTo(point2), is(not(negativeZero)));
    }

    @Test
    public void slopeToForVerticalLineIsPositiveInfinity() {
        Point point1 = new Point(1, 1);
        Point point2 = new Point(1, 2);

        assertThat(point1.slopeTo(point2), is(Double.POSITIVE_INFINITY));
    }

    @Test
    public void slopeToForDegenerateLineIsNegativeInfinity() {
        Point point = new Point(1, 1);
        assertThat(point.slopeTo(point), is(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void slopeToForValidLine() {
        Point point1 = new Point(19000, 10000);
        Point point2 = new Point(1234, 5678);

        assertThat(point1.slopeTo(point2), is(0.2432736688055837));
    }

    @Test
    public void arraysSortShouldUseSlopeOrder() {
        Point[] points = {
                new Point(19000, 10000),
                new Point(19000, 10000),
                new Point(18000, 10000),
                new Point(32000, 10000),
                new Point(21000, 10000),
                new Point(1234, 5678),
                new Point(14000, 10000)
        };

        Arrays.sort(points, points[0].SLOPE_ORDER);
        assertTrue(points[0].slopeTo(points[5]) < (points[0].slopeTo(points[6])));
    }
}
