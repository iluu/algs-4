import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private static final boolean VERTICAL = true;
    private static final RectHV ROOT_RECT = new RectHV(0, 0, 1, 1);

    private static class Node {
        private Point2D point;
        private RectHV rect;
        private Node left;
        private Node right;
        private boolean split;
        private int size;

        Node(Point2D p, boolean split, RectHV rectHV, int size) {
            this.point = p;
            this.split = split;
            this.rect = rectHV;
            this.size = size;
        }

        public int compareTo(Point2D p) {
            if (split == VERTICAL) {
                return p.x() > this.point.x() ? 1 : this.point.x() == p.x() ? 0 : -1;
            } else {
                return p.y() > this.point.y() ? 1 : this.point.y() == p.y() ? 0 : -1;
            }
        }
    }

    private Node root;

    /**
     * construct an empty set of points
     */
    public KdTree() {
        root = null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * number of points in the set
     */
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) return 0;
        return node.size;
    }

    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        root = put(root, p, VERTICAL, ROOT_RECT);
    }

    private Node put(Node node, Point2D p, boolean split, RectHV rectHV) {
        if (node == null) return new Node(p, split, rectHV, 1);
        int compare = node.compareTo(p);
        if (compare < 0) node.left = put(node.left, p, !node.split, getLeftRect(node));
        else if (compare > 0 || node.point.compareTo(p) != 0) node.right = put(node.right, p, !node.split, getRightRect(node));
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    private RectHV getLeftRect(Node node) {
        if (node.split == VERTICAL) {
            return new RectHV(node.rect.xmin(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        } else {
            return new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.point.y());
        }
    }

    private RectHV getRightRect(Node node) {
        if (node.split == VERTICAL) {
            return new RectHV(node.point.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
        } else {
            return new RectHV(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.rect.ymax());
        }
    }

    /**
     * does the set contain point p ?
     */
    public boolean contains(Point2D p) {
        return get(root, p) != null;
    }

    private Node get(Node node, Point2D point2D) {
        if (node == null) return null;

        int compare = node.compareTo(point2D);
        if (compare < 0) return get(node.left, point2D);
        else if (compare > 0 || node.point.compareTo(point2D) != 0) return get(node.right, point2D);
        else return node;
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        draw(root);
    }

    private void draw(Node node) {
        if (node != null) {
            StdDraw.setPenRadius(0.001);

            Point2D[] line = findLineWithPoint(node);
            line[0].drawTo(line[1]);

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.02);
            node.point.draw();

            draw(node.left);
            draw(node.right);
        }
    }

    private Point2D[] findLineWithPoint(Node node) {
        if (node.split == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);

            return new Point2D[]{
                    new Point2D(node.point.x(), node.rect.ymin()),
                    new Point2D(node.point.x(), node.rect.ymax())
            };
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);

            return new Point2D[]{
                    new Point2D(node.rect.xmin(), node.point.y()),
                    new Point2D(node.rect.xmax(), node.point.y())
            };
        }
    }

    /**
     * all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        return range(root, rect, new SET<Point2D>());
    }

    private Iterable<Point2D> range(Node node, RectHV rect, SET<Point2D> result) {
        if (node != null) {
            if (rect.contains(node.point)) {
                result.add(node.point);
            }
            if (node.left != null && node.left.rect.intersects(rect)) {
                range(node.left, rect, result);
            }
            if (node.right != null && node.right.rect.intersects(rect)) {
                range(node.right, rect, result);
            }
        }
        return result;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        return nearest(root, p, root.point);
    }

    private Point2D nearest(Node node, Point2D p, Point2D closest) {
        if (node.left != null) {
            double distance = p.distanceTo(closest);
            if (node.left.rect.distanceTo(p) < distance) {
                double newDistance = p.distanceTo(node.left.point);
                closest = nearest(node.left, p, distance < newDistance ? closest : node.left.point);
            }
        }
        if (node.right != null) {
            double distance = p.distanceTo(closest);
            if (node.right.rect.distanceTo(p) < distance) {
                double newDistance = p.distanceTo(node.right.point);
                closest = nearest(node.right, p, distance < newDistance ? closest : node.right.point);
            }
        }
        return closest;
    }

}
