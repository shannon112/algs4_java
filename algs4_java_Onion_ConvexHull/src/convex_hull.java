import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Onion {

private static Point2D[] storePoint2=null;

private static void readfile(String[] inputTxt){
        try(BufferedReader br2 = new BufferedReader(new FileReader(inputTxt[0]))) {
                int num = Integer.parseInt(br2.readLine());
                storePoint2=new Point2D[num];
                //StdDraw.setPenRadius(0.01);
                //StdDraw.setPenColor(StdDraw.BLUE);
                for(int i=0; i<num; i++) {
                        String[] point=br2.readLine().split(" ");
                        storePoint2[i]= new Point2D(Double.parseDouble(point[0]),Double.parseDouble(point[1]));
                        //storePoint2[i].draw();
                        //StdDraw.point(storePoint2[i].x(),storePoint2[i].y());
                        //System.out.println(storePoint2[i].x());
                        //System.out.println(storePoint2[i].y());
                }
        }
        catch(Exception e) {e.printStackTrace();}
}

public static double cross(Point2D O, Point2D A, Point2D B) {
        return (double)((A.x() - O.x()) * (B.y() - O.y()) - (A.y() - O.y()) * (B.x() - O.x()));
        //careful that there should be double, or 0-1 will all be 0 and pass the while loop below
}

public static Point2D[] convex_hull(Point2D[] P) {
        if (P.length > 1) {
                int n = P.length, k = 0;
                Point2D[] H = new Point2D[2 * n];
                Arrays.sort(P);//the smallest y will be first and next
                //for(int i=0;i<n;i++) System.out.println(P[i]);
                // Build lower hull
                for (int i = 0; i < n; ++i) {
                        //if cross ans is negative, represent there is a cw spin
                        while (k >= 2 && cross(H[k - 2], H[k - 1], P[i]) <= 0) k--;
                        H[k++] = P[i];
                }
                // Build upper hull
                for (int i = n - 2, t = k + 1; i >= 0; i--) {
                        while (k >= t && cross(H[k - 2], H[k - 1], P[i]) <= 0) k--;
                        H[k++] = P[i];
                }
                if (k > 1) H = Arrays.copyOfRange(H, 0, k - 1); // remove non-hull vertices after k; remove k - 1 which is a duplicate
                return H;
        } else if (P.length <= 1) return P;
        else return null;
}

public static int[] ConvexHullVertex(Point2D[] a) {
        Point2D[] backup = a.clone();
        Point2D[] hull = convex_hull(a);
        int[] output = new int[hull.length];
        for (int i = 0; i < hull.length; i++) {
                if (hull[i] != null){
                        //StdDraw.setPenColor(StdDraw.RED);
                        //hull[i].draw();
                        //System.out.println(hull[i]);
                        output[i]=Arrays.asList(backup).indexOf(hull[i]);
                }
        }
        // return the index set of the ConvexHullVertex, the index is the same as the index in array a:0, 1, 2, 3, 4, ....a.length-1
        return output;
}

public static void main(String[] args) {
        // 1. read in the file containing N 2-dimentional points
        // 2. recursively find the convex hull of the remaining points
        // 3. count the number of convex hulls and print it
        System.out.println("hello java!\n");
        readfile(args);
        int[] get = ConvexHullVertex(storePoint2);
        for(int i=0;i<get.length;i++) System.out.println(get[i]);
}
}
