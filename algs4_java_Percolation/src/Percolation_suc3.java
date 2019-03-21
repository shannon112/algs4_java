import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;
//import algs4.*;


public class Percolation {
  public static int pointCounter = -1;
  public static int matixSize = 0;
  public static int serialmatrixSize = 0;
  //public static int[][] map=null;
  //public static int[] serialMap=null; //not include 0 starting from 1
  public static WeightedQuickUnionUF test = null;
  //public static int[] openPtSerial = null;
  public static List<Integer> openPtSerial = new ArrayList<>();
  public static List<Integer> mayPtSerial = new ArrayList<>();
  public Percolation(int n) {
  }

  public static void checkHowMany(String[] inputTxt) {
    //Read file and check how many point need to open
    try(BufferedReader br1 = new BufferedReader(new FileReader(inputTxt[0]))) {
      while (true) {
        String counterdata = br1.readLine();
        if  (counterdata == null) {
          break;
        }
        pointCounter++;
      }
    } 
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void saveToDB(String[] inputTxt) {
    //Read file and put data to database
    try(BufferedReader br2 = new BufferedReader(new FileReader(inputTxt[0]))) {

      String data = br2.readLine();
      matixSize = Integer.parseInt(data);
      //System.out.println(N);
      serialmatrixSize = matixSize * matixSize + 1 + 1;
      //serialMap=new int[serialmatrixSize];

      for (int i = 0; i < pointCounter; i++) {
        String[] data2 = br2.readLine().split(",");
        openPtSerial.add( matixSize * (Integer.valueOf(data2[0]) - 1) + Integer.valueOf(data2[1]));
        //serialMap[openPtSerial[i]]=1;
        //System.out.printf("%d %d\n", dbOfPoint[i][0],dbOfPoint[i][1]);
      }
      Collections.sort(openPtSerial);
    } 
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void unionAll() {
    //for each position in map->judge if i position is open or not->
    //if nearby+1-1+3-3 position is out of range->if nearby is open->
    //union that!
    for (int j = 1; j < matixSize + 1; j++) { //union to virtual top
      if (openPtSerial.contains(j)) {
        test.union(openPtSerial.indexOf(j), pointCounter+1);
      }
    }
    for (int k = serialmatrixSize - 2 - matixSize + 1; k < serialmatrixSize - 1 ; k++ ) { //union to virtual bot
      if (openPtSerial.contains(k)) {
        test.union(openPtSerial.indexOf(k), pointCounter + 2);
      }
    }
    for (int i = 0; i < pointCounter; i++) {
      int value=openPtSerial.get(i);
      if (!((value % matixSize) == 0)) { // avoid jump to next line(hungry snack)
        if (openPtSerial.contains(value+1) ) {
          test.union(i, openPtSerial.indexOf(value+1));
        }else{mayPtSerial.add(value+1);}
      }
      if ((value + matixSize) <= (serialmatrixSize - 2)) {
        if (openPtSerial.contains(value+matixSize)) {
          test.union(i, openPtSerial.indexOf(value+matixSize));
        }else{mayPtSerial.add(value+matixSize);}
      }
    }


  }

  public static void printConnected() {
    boolean det = test.connected(pointCounter+1, pointCounter + 2);
    if (det) {
      System.out.println("0");
    } 
    else {
      String temp = chanceToConnected(); //use temp to avoid run it twice below
      if (temp == null) {
        System.out.println("-1");
      } 
      else {
        System.out.println(temp);
      }
    }
  }


  public static String serialToMatrix(int serialNum) {
    //System.out.println(serialNum);
    int x = serialNum % matixSize;
    int y = (serialNum / matixSize) + 1;
    if (x == 0) {
      x = matixSize;
      y -= 1;
    }
    String output = Integer.toString(y) + "," + Integer.toString(x);
    return output;
  }

  public static String chanceToConnected() {
    for (int i = 0; i < mayPtSerial.size(); i++) {
        int may=mayPtSerial.get(i);
      //if (!(openPtSerial.contains(may))) { //notice!!!there is search for those num that NOT in openPtSerial
        test = new WeightedQuickUnionUF(pointCounter + 3);
        openPtSerial.add(may);
        Collections.sort(openPtSerial);
        unionAll();
        if (test.connected(pointCounter+1, pointCounter + 2)) {
          //System.out.printf("got it ! %d \n",i);
          return serialToMatrix(may);
        }
        else{
          openPtSerial.remove(openPtSerial.indexOf(may));
        }
      }
    
    return null;
  }

  public static void main(String[] args) {
    checkHowMany(args);
    saveToDB(args);
    test = new WeightedQuickUnionUF(pointCounter + 3); //include {openPtSerial, new, top, bot}
    unionAll();   
    printConnected();
     
    /*
      for (int i=0; i< openPtSerial.size();i++) {
     System.out.println("Number = " + openPtSerial.get(i));
     }
     */
  }
}


