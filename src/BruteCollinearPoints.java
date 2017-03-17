import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private static final int POINTS_NO = 4;

    private LineSegment[] segments;
    private int numberOfSegments;

    public BruteCollinearPoints(Point[] input) {
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

        findCollinearSegments(points);
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        if (numberOfSegments < segments.length) {
            LineSegment[] result = new LineSegment[numberOfSegments];
            System.arraycopy(segments, 0, result, 0, numberOfSegments);
            segments = result;
        }
        return segments;
    }

    private void findCollinearSegments(Point[] points) {
        segments = new LineSegment[2];
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point[] subArray = {points[i], points[j], points[k], points[l]};
                        if (inLine(subArray)) {
                            Arrays.sort(subArray);
                            addSegment(subArray[0], subArray[3]);
                        }
                    }
                }
            }
        }
    }

    private void addSegment(Point p1, Point p2) {
        if (numberOfSegments == segments.length) {
            LineSegment[] temp = new LineSegment[numberOfSegments * 2];
            System.arraycopy(segments, 0, temp, 0, numberOfSegments);
            segments = temp;
        }
        segments[numberOfSegments++] = new LineSegment(p1, p2);
    }

    private boolean inLine(Point[] subArray) {
        boolean inLine = true;

        int i = 2;
        double slope = subArray[0].slopeTo(subArray[1]);
        do {
            double newSlope = subArray[0].slopeTo(subArray[i]);
            if (slope != newSlope) {
                inLine = false;
            }
            i++;

        } while (inLine && i < POINTS_NO);

        return inLine;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
