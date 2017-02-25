import java.util.Arrays;

public class Brute {
    private Point[] points;
    private int MIN_POINTS_NO = 4;

    public Brute(Point[] points) {
        this.points = points;
        findCollinearSegments();
    }

    private void findCollinearSegments() {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point[] subArray = {points[i], points[j], points[k], points[l]};
                        if (inLine(subArray)) {
                            print(subArray);
                            draw(subArray);
                        }
                    }
                }
            }
        }
    }

    private void draw(Point[] array) {
        Arrays.sort(array);
        array[0].drawTo(array[3]);
    }

    private void print(Point[] array) {
        StringBuilder sb = new StringBuilder(array[0].toString());
        for (int i = 1; i < array.length; i++) {
            sb.append(" -> ");
            sb.append(array[i].toString());
        }
        StdOut.println(sb.toString());
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

        } while (inLine && i < MIN_POINTS_NO);

        return inLine;
    }

    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        String fileName = args[0];
        In in = new In(fileName);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
            points[i].draw();
        }

        new Brute(points);
    }
}
