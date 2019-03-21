import java.lang.*;
import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class mapping{

private static double smallestY=1;
private static double smallestYsX=0;
private static int smallestYsIndex=0;
private static Point2D[] storePoint2=null;
private static int num=0;

private void readfile(String[] inputTxt){
        try(BufferedReader br2 = new BufferedReader(new FileReader(inputTxt[0]))) {
                num = Integer.parseInt(br2.readLine());
                storePoint2=new Point2D[num];

                StdDraw.setPenRadius(0.01);
                StdDraw.setPenColor(StdDraw.BLUE);

                for(int i=0; i<num; i++) {
                        String[] point=br2.readLine().split(" ");
                        storePoint2[i]= new Point2D(Double.parseDouble(point[0]),Double.parseDouble(point[1]));
                        storePoint2[i].draw();
                        //StdDraw.point(storePoint2[i].x(),storePoint2[i].y());
                        //System.out.println(storePoint2[i].x());
                        //System.out.println(storePoint2[i].y());
                        if((storePoint2[i].y())<smallestY){
                                smallestY=storePoint2[i].y();
                                smallestYsX=storePoint2[i].x();
                                smallestYsIndex=i;
                        }
                }
        }
        catch(Exception e) {
                e.printStackTrace();
        }
}
public static void main(String[] args){
        System.out.println("hello java!\n");
        mapping m=new mapping();
        m.readfile(args);
        /*
        for (int i=0;i<10;i++){
                if(i==smallestYsIndex){
                        //nothing
                }else{
                        float cos=-smallestYsX+storePoint[i][0];
                        float sin=-smallestY+storePoint[i][1];
                        float angle=(float)(Math.atan(sin/cos)*(1/Math.PI)*180.0);
                        if(angle<0.0) angle=angle+(float)180.0;
                        System.out.printf("index= %d, (x,y)=(%f,%f), angle= %f\n",i,(float)storePoint[i][0],(float)storePoint[i][1],angle);
                }
        }
        */
        Arrays.sort(storePoint2);
        System.out.printf("the smallest y is %.2f,%.2f\n",storePoint2[0].x(),storePoint2[0].y());
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.line(0, 0.5, 1, 0.5);
        StdDraw.line(0.5, 0, 0.5, 1);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(smallestYsX,smallestY);
}
}
