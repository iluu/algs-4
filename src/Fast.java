import java.util.Arrays;

public class Fast {
    private final Point[] points;
    private static final int MIN_NO_POINTS = 3;

    Fast(Point[] points) {
        this.points = points;
        findCollinear();
    }

    private void findCollinear() {
        for (int iter = 0; iter < points.length; iter++) {
            StdOut.println("iter: " + iter);
            Arrays.sort(points, points[iter].SLOPE_ORDER);
            for (int i = 0; i < points.length-2; i++) {
                int j = i + 1;

                boolean colinear = true;
                StdOut.println(i + " " + points[i] + " -> " + j + " " + points[j] + " slope: " + points[i].slopeTo(points[j]));
                double slope = points[i].slopeTo(points[j++]);
                do {
                    double newSlope = points[i].slopeTo(points[j]);
                    StdOut.println(i + " " + points[i] + " -> " + j + " " + points[j] + " slope: " + points[i].slopeTo(points[j]));
                    if (slope != newSlope) {
                        colinear = false;
                    }
                } while (colinear && ++j < points.length);

                if ((j - i) > MIN_NO_POINTS) {
                    drawPoints(i, j);
                    i = j + 1;
                } else {
                    i = j - 1;
                }
            }
        }
    }

    private void drawPoints(int start, int end) {
        Arrays.sort(points, start, end);
        points[start].drawTo(points[end - 1]);
        StringBuilder sb = new StringBuilder(points[start].toString());
        for (int i = start + 1; i < end; i++) {
            sb.append(" -> ");
            sb.append(points[i].toString());
        }
        StdOut.println(sb.toString());
    }

    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        String fileName = args[0];
        In in = new In(fileName);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
            points[i].draw();
        }

        StdDraw.setPenRadius();  // make the points a bit larger
        new Fast(points);
    }
}
