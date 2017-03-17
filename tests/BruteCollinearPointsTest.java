import org.junit.Test;

public class BruteCollinearPointsTest {

    @Test(expected = NullPointerException.class)
    public void throwsNPEWhenPassingEmptyArrayToConstructor() {
        new BruteCollinearPoints(null);
    }

    @Test(expected = NullPointerException.class)
    public void throwsNPEWhenAnyOfThePointsIsNull() {
        Point[] arr = new Point[]{
                new Point(1, 1),
                new Point(2, 3),
                new Point(3, 4)
        };

        new BruteCollinearPoints(arr);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsIllegalArgumentWhenPassingRepeatedPoint() {
        Point[] arr = new Point[]{
                new Point(1, 1),
                new Point(2, 3),
                new Point(3, 4),
                new Point(1, 1)
        };

        new BruteCollinearPoints(arr);

    }
}
