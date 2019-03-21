import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.*;

public class Onion {

private static Point2D[] originalData=null;
private static ArrayList<Integer> list = new ArrayList<Integer>();

private static void readfile(String[] inputTxt){
        try(BufferedReader br2 = new BufferedReader(new FileReader(inputTxt[0]))) {
                int num = Integer.parseInt(br2.readLine());
                originalData=new Point2D[num];
                //StdDraw.setPenRadius(0.01);
                //StdDraw.setPenColor(StdDraw.BLUE);
                for(int i=0; i<num; i++) {
                        String[] point=br2.readLine().split(" ");
                        originalData[i]= new Point2D(Double.parseDouble(point[0]),Double.parseDouble(point[1]));
                        list.add(i);
                        //System.out.println(i);
                        //originalData[i].draw();
                        //StdDraw.point(originalData[i].x(),originalData[i].y());
                        //System.out.println(originalData[i].x());
                        //System.out.println(originalData[i].y());
                }
                //System.out.println(list.size());
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

public static void main(String[] args) {
        // 1. read in the file containing N 2-dimentional points
        // 2. recursively find the convex hull of the remaining points
        // 3. count the number of convex hulls and print it
        //System.out.println("hello java!\n");
        int counter=0;
        readfile(args);
        Point2D[] hull = convex_hull(originalData);
        Point2D[] nextStorage =null;
        //for(int i=0;i<list.size();i++){
        //        System.out.println(list.get(i));
        //}
        while(true) {
                if ((hull==null)||(hull.length==1)||(hull.length==2)) break;
                //System.out.println("++");
                counter++;
                for (int i = 0; i < hull.length; i++) list.remove(new Integer(Arrays.asList(originalData).indexOf(hull[i])));
                nextStorage = new Point2D[list.size()];
                for (int i=0; i<list.size(); i++) nextStorage[i]=originalData[list.get(i)];
                hull = convex_hull(nextStorage);
        }
        System.out.println(counter);
}
}
