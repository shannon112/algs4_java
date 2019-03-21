/*
        we have 5 inner class, 2 function
        class: Node, PointDist, kdTree, PointSET, RectHV
        function: init, query.
        ref: https://github.com/maggie0/Range-Search-and-Nearest-Neighbor-Search
        edit by shannon112
*/

//***********this part is optional( in PDSA judge system you dont need to write them)***********
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.StdDraw;
//import edu.princeton.cs.algs4.RectHV;
//because the import of RectHV fail at PDSA judge system, so we make one copy as inner class
//********************************************

import java.util.Stack;
import java.util.*;
import java.lang.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class RangeSearch{

private static PointSET brute = new PointSET();
private static KdTree kdtree = new KdTree();

// please use the Paint2D from algs4.jar
public void init(Point2D[] points){
        for(int i=0;i<points.length;i++){
                kdtree.insert(points[i]);
                brute.insert(points[i]);
        }
}
// the result should be sorted accordingly to their x coordinates
public Point2D[] query(Point2D ll,Point2D ur){
        double x0 = ll.x(), y0 = ll.y();      // initial endpoint of rectangle
        double x1 = ur.x(), y1 = ur.y();      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                                Math.max(x0, x1), Math.max(y0, y1));
        int N=0;
        for (Point2D p : kdtree.range(rect)) N++;
        Point2D[] output=new Point2D[N];
        int i=0;
        for (Point2D p : kdtree.range(rect)){
                output[i]=p;
                i++;
        }
        Arrays.sort(output);
        return output;
}

private static class Node {
        private Point2D point;
        private Node left, right;
        public Node(Point2D point) {
                this.point = point;
        }
}

private static class PointDist {
        private Point2D point;
        private double sqDist;
        public PointDist(Point2D p, double sd) {
                point = p;
                sqDist = sd;
        }
}

private static class KdTree {
        private Node root;
        private int n;

        public KdTree() {     // construct an empty set of points
                root = null;
                n = 0;
        }

        public boolean isEmpty() {     // is the set empty?
                return n == 0;
        }

        public int size() {     // number of points in the set
                return n;
        }

        public void insert(Point2D p) {     // add the point to the set (if it is not already in the set)
                validation(p);
                root = insert(root, p, 0);
        }

        private Node insert(Node t, Point2D p, int level) {
                if (t == null) {
                        ++n;
                        return new Node(p);
                }
                if (t.point.equals(p)) return t;
                double cmp = level % 2 == 0 ? p.x() - t.point.x() : p.y() - t.point.y();
                if (cmp < 0)
                        t.left = insert(t.left, p, level+1);
                else
                        t.right = insert(t.right, p, level+1);
                return t;
        }

        public boolean contains(Point2D p) {     // does the set contain point p?
                validation(p);
                return contains(root, p, false);
        }

        private boolean contains(Node t, Point2D p, boolean level) {
                if (t == null) return false;
                if (t.point.equals(p)) return true;
                double cmp = level ? p.y() - t.point.y() : p.x() - t.point.x();
                if (cmp < 0)
                        return contains(t.left, p, !level);
                else
                        return contains(t.right, p, !level);
        }

        public void draw() {     // draw all points to standard draw
                StdDraw.setXscale(-0.05, 1.05);
                StdDraw.setYscale(-0.05, 1.05);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.001);
                StdDraw.line(0.0, 0.0, 0.0, 1.0);
                StdDraw.line(0.0, 0.0, 1.0, 0.0);
                StdDraw.line(0.0, 1.0, 1.0, 1.0);
                StdDraw.line(1.0, 0.0, 1.0, 1.0);
                draw(root, 1, 0, 0, 1, false);
        }

        private void draw(Node t, double top, double bottom, double left, double right, boolean level) {
                if (t == null) return;
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                StdDraw.point(t.point.x(), t.point.y());
                if (level) {
                        StdDraw.setPenColor(StdDraw.BLUE);
                        StdDraw.setPenRadius(0.001);
                        StdDraw.line(left, t.point.y(), right, t.point.y());
                        draw(t.left, t.point.y(), bottom, left, right, false);
                        draw(t.right, top, t.point.y(), left, right, false);
                } else {
                        StdDraw.setPenColor(StdDraw.RED);
                        StdDraw.setPenRadius(0.001);
                        StdDraw.line(t.point.x(), top, t.point.x(), bottom);
                        draw(t.left, top, bottom, left, t.point.x(), true);
                        draw(t.right, top, bottom, t.point.x(), right, true);
                }
        }

        public Iterable<Point2D> range(RectHV rect) {     // all points that are inside the rectangle
                validation(rect);
                Stack<Point2D> insidePoints = new Stack<>();
                range(root, rect, insidePoints, false);
                return insidePoints;
        }

        private void range(Node t, RectHV rect, Stack<Point2D> pointSet, boolean level) {
                if (t == null) return;
                if (rect.contains(t.point))
                        pointSet.push(t.point);
                double minBoundary = level ? rect.ymin() : rect.xmin();
                double maxBoundary = level ? rect.ymax() : rect.xmax();
                double split = level ? t.point.y() : t.point.x();
                if (minBoundary < split)
                        range(t.left, rect, pointSet, !level);
                if (maxBoundary >= split)
                        range(t.right, rect, pointSet, !level);
        }

        public Point2D nearest(Point2D p) {     // a nearest neighbor in the set to point p; null if the set is empty
                validation(p);
                if (isEmpty())
                        return null;
                double minSqDist = Double.POSITIVE_INFINITY;
                return nearest(root, p, new PointDist(null, minSqDist), false, 1, 0, 0, 1).point;
        }

        private PointDist nearest(Node t, Point2D p, PointDist nearestPoint, boolean level,
                                  double top, double bottom, double left, double right) {
                if (t == null) return nearestPoint;
                double curSqDist = t.point.distanceSquaredTo(p);
                if (Double.compare(curSqDist, nearestPoint.sqDist) < 0) {
                        nearestPoint.point = t.point;
                        nearestPoint.sqDist = curSqDist;
                }
                double cmp = level ? p.y() - t.point.y() : p.x() - t.point.x();
                Node first, second;
                double firstTop, firstBottom, firstLeft, firstRight,
                       secondTop, secondBottom, secondLeft, secondRight;
                if (cmp < 0) {
                        first = t.left;
                        second = t.right;
                        if (level) {
                                firstTop = t.point.y();
                                firstBottom = bottom;
                                secondTop = top;
                                secondBottom = t.point.y();
                                firstLeft = secondLeft = left;
                                firstRight = secondRight = right;
                        } else {
                                firstTop = secondTop = top;
                                firstBottom = secondBottom = bottom;
                                firstLeft = left;
                                firstRight = t.point.x();
                                secondLeft = t.point.x();
                                secondRight = right;
                        }
                } else {
                        first = t.right;
                        second = t.left;
                        if (level) {
                                firstTop = top;
                                firstBottom = t.point.y();
                                secondTop = t.point.y();
                                secondBottom = bottom;
                                firstLeft = secondLeft = left;
                                firstRight = secondRight = right;
                        } else {
                                firstTop = secondTop = top;
                                firstBottom = secondBottom = bottom;
                                firstLeft = t.point.x();
                                firstRight = right;
                                secondLeft = left;
                                secondRight = t.point.x();
                        }
                }
                nearestPoint = nearest(first, p, nearestPoint, !level, firstTop, firstBottom, firstLeft, firstRight);
                RectHV secondRect = new RectHV(secondLeft, secondBottom, secondRight, secondTop);
                double sqDistToSecond = secondRect.distanceSquaredTo(p);
                if (sqDistToSecond < nearestPoint.sqDist)
                        nearestPoint = nearest(second, p, nearestPoint, !level, secondTop, secondBottom, secondLeft, secondRight);
                return nearestPoint;
        }

        private void validation(Object that) {
                if (that == null)
                        throw new NullPointerException("Null argument");
        }
}

private static class PointSET {
        private TreeSet<Point2D> pointSet;

        public PointSET() {     // construct an empty set of points
                pointSet = new TreeSet<>();
        }

        public boolean isEmpty() {     // is the set empty?
                return pointSet.isEmpty();
        }

        public int size() {     // number of points in the set
                return pointSet.size();
        }

        public void insert(Point2D p) {     // add the point to the set (if it is not already in the set)
                validation(p);
                pointSet.add(p);
        }

        public boolean contains(Point2D p) {     // does the set contain point p?
                validation(p);
                return pointSet.contains(p);
        }

        public void draw() {     // draw all points to standard draw
                StdDraw.setXscale(-0.05, 1.05);
                StdDraw.setYscale(-0.05, 1.05);
                for (Point2D p : pointSet) {
                        StdDraw.point(p.x(), p.y());
                }
        }

        public Iterable<Point2D> range(RectHV rect) {     // all points that are inside the rectangle
                validation(rect);
                Stack<Point2D> insidePoints = new Stack<>();
                for (Point2D p : pointSet) {
                        if (rect.contains(p))
                                insidePoints.push(p);
                }
                return insidePoints;
        }

        public Point2D nearest(Point2D p) {     // a nearest neighbor in the set to point p; null if the set is empty
                validation(p);
                if (isEmpty())
                        return null;
                double minSqrDist = Double.POSITIVE_INFINITY;
                Point2D nearestPoint = null;
                for (Point2D point : pointSet) {
                        if (Double.compare(point.distanceSquaredTo(p), minSqrDist) < 0) {
                                minSqrDist = point.distanceSquaredTo(p);
                                nearestPoint = point;
                        }
                }
                return nearestPoint;
        }

        private void validation(Object that) {
                if (that == null) throw new NullPointerException("Null argument");
        }
}

private static class RectHV {
    private final double xmin, ymin;   // minimum x- and y-coordinates
    private final double xmax, ymax;   // maximum x- and y-coordinates
    public RectHV(double xmin, double ymin, double xmax, double ymax) {
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
        if (Double.isNaN(xmin) || Double.isNaN(xmax)) {
            throw new IllegalArgumentException("x-coordinate is NaN: " + toString());
        }
        if (Double.isNaN(ymin) || Double.isNaN(ymax)) {
            throw new IllegalArgumentException("y-coordinate is NaN: " + toString());
        }
	if (xmax < xmin) {
            throw new IllegalArgumentException("xmax < xmin: " + toString());
        }
        if (ymax < ymin) {
            throw new IllegalArgumentException("ymax < ymin: " + toString());
        }
    }
    public double xmin() {
        return xmin;
    }
    public double xmax() {
        return xmax;
    }
    public double ymin() {
        return ymin;
    }
    public double ymax() {
        return ymax;
    }
    public double width() {
        return xmax - xmin;
    }
    public double height() {
        return ymax - ymin;
    }
    public boolean intersects(RectHV that) {
        return this.xmax >= that.xmin && this.ymax >= that.ymin
            && that.xmax >= this.xmin && that.ymax >= this.ymin;
    }
    public boolean contains(Point2D p) {
        return (p.x() >= xmin) && (p.x() <= xmax)
            && (p.y() >= ymin) && (p.y() <= ymax);
    }
    public double distanceTo(Point2D p) {
        return Math.sqrt(this.distanceSquaredTo(p));
    }
    public double distanceSquaredTo(Point2D p) {
        double dx = 0.0, dy = 0.0;
        if      (p.x() < xmin) dx = p.x() - xmin;
        else if (p.x() > xmax) dx = p.x() - xmax;
        if      (p.y() < ymin) dy = p.y() - ymin;
        else if (p.y() > ymax) dy = p.y() - ymax;
        return dx*dx + dy*dy;
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        RectHV that = (RectHV) other;
        if (this.xmin != that.xmin) return false;
        if (this.ymin != that.ymin) return false;
        if (this.xmax != that.xmax) return false;
        if (this.ymax != that.ymax) return false;
        return true;
    }
    @Override
    public int hashCode() {
        int hash1 = ((Double) xmin).hashCode();
        int hash2 = ((Double) ymin).hashCode();
        int hash3 = ((Double) xmax).hashCode();
        int hash4 = ((Double) ymax).hashCode();
        return 31*(31*(31*hash1 + hash2) + hash3) + hash4;
    }
    @Override
    public String toString() {
        return "[" + xmin + ", " + xmax + "] x [" + ymin + ", " + ymax + "]";
    }
    public void draw() {
        StdDraw.line(xmin, ymin, xmax, ymin);
        StdDraw.line(xmax, ymin, xmax, ymax);
        StdDraw.line(xmax, ymax, xmin, ymax);
        StdDraw.line(xmin, ymax, xmin, ymin);
    }
}

public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
                // initialize the data structures with N points from standard input
                for(String in = br.readLine(); in != null; in = br.readLine()) {
                        String[] temp = in.split(" ");
                        double x = Double.parseDouble(temp[0]);
                        double y = Double.parseDouble(temp[1]);
                        Point2D p = new Point2D(x, y);
                        kdtree.insert(p);
                        brute.insert(p);
                }

                double x0 = 0.2, y0 = 0.3;      // initial endpoint of rectangle
                double x1 = 0.7, y1 = 0.8;      // current location of mouse
                boolean isDragging = false;     // is the user dragging a rectangle

                // draw the points
                StdDraw.clear();
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                brute.draw();
                StdDraw.show();

                RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                                         Math.max(x0, x1), Math.max(y0, y1));

                // draw the rectangle
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius();
                rect.draw();

                // draw the range search results for brute-force data structure in red
                StdDraw.setPenRadius(0.03);
                StdDraw.setPenColor(StdDraw.RED);
                for (Point2D p : brute.range(rect)) p.draw();

                // draw the range search results for kd-tree in blue
                StdDraw.setPenRadius(.02);
                StdDraw.setPenColor(StdDraw.BLUE);
                for (Point2D p : kdtree.range(rect)){
                        p.draw();
                        System.out.printf("( %f , %f )\n",p.x(),p.y());
                }
                StdDraw.show();

        }
}
}
