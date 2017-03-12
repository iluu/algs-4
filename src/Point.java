import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    /**
     * Compare points by slope
     */
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {

            double slope1 = Point.this.slopeTo(o1);
            double slope2 = Point.this.slopeTo(o2);

            if (slope1 < slope1) {
                return -1;
            } else if (slope1 > slope2) {
                return 1;
            }
            return 0;
        }
    };

    private final int x;
    private final int y;

    /**
     * Create the point (x, y)
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Plot this point to standard drawing
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draw line between this point and that point to standard drawing
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Slope between this point and that point
     */
    public double slopeTo(Point that) {
        if (this.compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        }

        if (isVertical(this, that)) {
            return Double.POSITIVE_INFINITY;
        }

        if (isHorizontal(this, that)) {
            return +0d;
        }

        return (double)(that.y - this.y) / (that.x - this.x);
    }

    private static boolean isHorizontal(Point p1, Point p2) {
        return p1.y == p2.y;
    }

    private static boolean isVertical(Point p1, Point p2) {
        return p1.x == p2.x;
    }

    /**
     * Is this point lexicographically smaller than that one?
     * comparing y-coordinates and breaking ties by x-coordinates
     */
    @Override
    public int compareTo(Point that) {
        if (this.y > that.y) {
            return 1;
        } else if (this.y < that.y) {
            return -1;
        } else if (this.x < that.x) {
            return -1;
        } else if (this.x > that.x) {
            return 1;
        }
        return 0;
    }

    /**
     * Return string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}