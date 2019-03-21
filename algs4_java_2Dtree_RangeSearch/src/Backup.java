import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import java.util.*;
import java.lang.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class Backup {

	// please use the Paint2D from algs4.jar
	public void init(Point2D[] points){

	}

	// the result should be sorted accordingly to their x coordinates
	public Point2D[] query(Point2D ll,Point2D ur){
		Point2D[] result= new Point2D[20];
		return result;
	}

	public static void main(String[] argv) throws Exception {
		//input data from sample0.in
		try (BufferedReader br = new BufferedReader(new FileReader(argv[0]))) {
			Point2D[] inputPoint= new Point2D[20];//<<<<<<<<<<<<<<here need to manipulate input
			int i=0;
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(0.01);
			for(String in = br.readLine(); in != null; in = br.readLine()) {
				String[] temp = in.split(" ");
				inputPoint[i]=new Point2D(Double.parseDouble(temp[0]),Double.parseDouble(temp[1]));
				inputPoint[i].draw();
				i++;
			}
		}
	}
}
