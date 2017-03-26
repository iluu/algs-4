import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {

    private SET<Point2D> set;

    /**
     * construct an empty set of points
     */
    public PointSET() {
        set = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * number of points in the set
     */
    public int size() {
        return set.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        set.add(p);
    }

    /**
     * does the set contain point p ?
     */
    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D point : set) {
            point.draw();
        }
    }

    /**
     * all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> result = new SET<Point2D>();
        for (Point2D point : set) {
            if (rect.contains(point)) {
                result.add(point);
            }
        }
        return result;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        double minDistance = Double.MAX_VALUE;
        Point2D result = null;
        for (Point2D point : set) {
            if (p.distanceTo(point) < minDistance) {
                result = point;
                minDistance = p.distanceTo(result);
            }
        }
        return result;
    }

}
