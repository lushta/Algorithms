package collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alla.hranatova on 10/30/2016.
 * <p>
 * Examines 4 points at a time and checks whether they all lie on the same line segment,
 * returning all such line segments.
 * To check whether the 4 points p, q, r, and s are collinear,
 * check whether the three slopes between p and q, between p and r, and between p and s are all equal
 */
public class BruteCollinearPoints {

    private LineSegment[] segments;


    /**
     * Finds all line segments containing 4 points
     */
    public BruteCollinearPoints(Point[] points) {

        if (points == null) {
            throw new NullPointerException();
        }
        validate(points);
        Point[] pointsCopy = new Point[points.length];
        System.arraycopy(points, 0, pointsCopy, 0, points.length);
        Quick.sort(pointsCopy);
        for (int i = 0; i < pointsCopy.length - 1; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points were found");
            }
        }
        if (pointsCopy.length < 4) {
            return;
        }
        findSegments(pointsCopy);
    }

    private void findSegments(Point[] points) {

        List<LineSegment> result = new ArrayList<>();
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        double ijSlope = points[i].slopeTo(points[j]);
                        double ikSlope = points[i].slopeTo(points[k]);
                        if (Double.compare(ijSlope, ikSlope) == 0) {
                            double ilSlope = points[i].slopeTo(points[l]);
                            if (Double.compare(ikSlope, ilSlope) == 0) {
                                result.add(new LineSegment(points[i], points[l]));
                            }
                        }
                    }
                }
            }
        }
        segments = result.toArray(new LineSegment[result.size()]);
    }


    /**
     * The number of line segments
     */
    public int numberOfSegments() {
        return segments.length;
    }

    /**
     * The line segments
     */
    public LineSegment[] segments() {
        if (segments == null) {
            return new LineSegment[]{};
        }
        LineSegment[] segmentsCopy = new LineSegment[segments.length];
        System.arraycopy(segments, 0, segmentsCopy, 0, segments.length);
        return segmentsCopy;
    }

    private void validate(Point[] points) {
        for (Point point : points) {
            if (point == null) {
                throw new NullPointerException();
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }


}
