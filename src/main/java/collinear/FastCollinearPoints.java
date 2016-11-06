package collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alla.hranatova on 11/05/2016.
 * <p>
 * Program to recognize line patterns in a given set of points.
 * <p>
 * <p>
 * The order of growth of the running time of the program is n2 log n in the worst case
 * and it uses space proportional to n plus the number of line segments returned.
 * <p>
 */
public class FastCollinearPoints {

    private List<LineSegment> segments = new ArrayList<>();

    /**
     * Finds all line segments containing 4 or more points
     */
    public FastCollinearPoints(Point[] points) {
        validate(points);
        Point[] pointsCopy = new Point[points.length];
        System.arraycopy(points, 0, pointsCopy, 0, points.length);
        Quick.sort(pointsCopy);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points were found");
            }
        }
        if (points.length < 4) {
            return;
        }
        Map<Double, List<Point>> slopeEndpointsMap = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            findSegments(pointsCopy, points[i], slopeEndpointsMap);
        }
    }

    /**
     * Given a point p, the following method determines whether p participates in a set of 4 or more collinear points:
     * Think of p as the origin.
     * For each other point q, determine the slope it makes with p.
     * Sort the points according to the slopes they makes with p.
     * Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p.
     * If so, these points, together with p, are collinear.
     */
    private void findSegments(Point[] points, Point origin,
                              Map<Double, List<Point>> slopeEndpointsMap) {
        Arrays.sort(points, origin.slopeOrder());
        List<Point> currentSegment = new ArrayList<>();
        double previousSlope = Double.NEGATIVE_INFINITY;
        for (int i = 1; i < points.length; i++) {
            double slope = origin.slopeTo(points[i]);
            if (Double.compare(slope, previousSlope) == 0) {
                currentSegment.add(points[i]);
                if (currentSegment.size() > 2 && i == points.length - 1) {
                    currentSegment.add(origin);
                    addSegment(currentSegment, slope, slopeEndpointsMap);
                }
            } else if (currentSegment.size() > 2) {
                currentSegment.add(origin);
                addSegment(currentSegment, previousSlope, slopeEndpointsMap);
                currentSegment.clear();
            } else {
                currentSegment.clear();
                currentSegment.add(points[i]);
            }
            previousSlope = slope;
        }
    }

    private void addSegment(List<Point> newSegment, double slope,
                            Map<Double, List<Point>> slopeEndpointsMap) {
        Collections.sort(newSegment);
        Point first = newSegment.get(0);
        Point last = newSegment.get(newSegment.size() - 1);
        List<Point> foundEndpoints = slopeEndpointsMap.get(slope);
        if (foundEndpoints == null) {
            foundEndpoints = new ArrayList<>();
            foundEndpoints.add(last);
            slopeEndpointsMap.put(slope, foundEndpoints);
            segments.add(new LineSegment(first, last));
        } else {
            boolean isSegmentAlreadyAdded = false;
            for (Point endpoint : foundEndpoints) {
                if (last.compareTo(endpoint) == 0) {
                    isSegmentAlreadyAdded = true;
                    break;
                }
            }
            if (!isSegmentAlreadyAdded) {
                foundEndpoints.add(last);
                segments.add(new LineSegment(first, last));
            }
        }
    }

    /**
     * The number of line segments
     */
    public int numberOfSegments() {
        return segments.size();
    }

    /**
     * The line segments
     */
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    private void validate(Point[] points) {

        if (points == null) {
            throw new NullPointerException();
        }
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
