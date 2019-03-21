import java.lang.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class mapping{

private static float storePoint[][]= new float[10][2];
private static float smallestY=1;
private static float smallestYsX=0;
private static int smallestYsIndex=0;
private static void readfile(){
        try(BufferedReader br2 = new BufferedReader(new FileReader(inputTxt[0]))) {
                int num = (int)br2.readLine();
                for(int i=0; i<num; i++) {
                        String[] point=br2.readLine().split(",");
                }
        }
        catch(Exception e) {
                e.printStackTrace();
        }
}
public static void main(String[] args){    // you can test your code here; we won't execute your main functio
        System.out.println("hello java!\n");
        readfile();
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLUE);
        for(int i=0;i<10;i++){
                storePoint[i][0]=(float)StdRandom.uniform();//set x
                storePoint[i][1]=(float)StdRandom.uniform();//set y
                StdDraw.point(storePoint[i][0],storePoint[i][1]);
                if(storePoint[i][1]<smallestY){
                        smallestY=storePoint[i][1];
                        smallestYsX=storePoint[i][0];
                        smallestYsIndex=i;
                }
        }
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
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.line(0, 0.5, 1, 0.5);
        StdDraw.line(0.5, 0, 0.5, 1);
        System.out.printf("the smallest y is %.2f,%.2f\n",smallestY,smallestYsX);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(smallestYsX,smallestY);
}
}
