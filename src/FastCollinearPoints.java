import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private static final int MIN_NO_POINTS = 3;

    private Line[] lines;
    private int numberOfSegments;

    public FastCollinearPoints(Point[] input) {
        if (input == null) {
            throw new NullPointerException();
        }

        Point[] points = new Point[input.length];
        System.arraycopy(input, 0, points, 0, input.length);

        Arrays.sort(points);
        for (int i = 0; i < input.length - 1; i++) {
            if (input[i].compareTo(input[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        findCollinearPoints(points);
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            result[i] = new LineSegment(lines[i].p1, lines[i].p2);
        }
        return result;
    }

    private void addSegment(Point[] points, int from, int to) {
        Point[] sort = new Point[to - from + 1];
        sort[0] = points[0];
        for (int i = from, j = 1; i < to; i++, j++) {
            sort[j] = points[i];
        }

        Arrays.sort(sort);

        if (!containsLine(sort[0], sort[sort.length - 1])) {
            if (numberOfSegments == lines.length) {
                Line[] temp = new Line[numberOfSegments * 2];
                System.arraycopy(lines, 0, temp, 0, numberOfSegments);
                lines = temp;
            }

            lines[numberOfSegments++] = new Line(sort[0], sort[sort.length - 1]);
        }
    }

    private boolean containsLine(Point p1, Point p2) {
        for (int i = 0; i < numberOfSegments; i++) {
            if (lines[i].p1 == p1 && lines[i].p2 == p2) {
                return true;
            }
        }
        return false;
    }

    private void findCollinearPoints(Point[] points) {
        lines = new Line[2];
        Point[] original = new Point[points.length];
        System.arraycopy(points, 0, original, 0, points.length);
        for (int iter = 0; iter < points.length; iter++) {
            Arrays.sort(points, original[iter].slopeOrder());

            for (int i = 1; i < points.length - 1; i++) {
                int j = i + 1;
                boolean collinear = true;
                double slope = points[0].slopeTo(points[i]);
                do {
                    double newSlope = points[0].slopeTo(points[j]);
                    if (newSlope == Double.NEGATIVE_INFINITY) {
                        throw new IllegalArgumentException();
                    } else if (Math.abs(slope - newSlope) > .0000001) {
                        collinear = false;
                    }
                } while (collinear && ++j < points.length);

                if ((j - i) >= MIN_NO_POINTS) {
                    addSegment(points, i, j);
                    i = j + 1;
                } else {
                    i = j - 1;
                }
            }
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private static class Line {
        final Point p1;
        final Point p2;

        public Line(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
    }
}
