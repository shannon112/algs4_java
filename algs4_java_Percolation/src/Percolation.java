/*
This is Shannon's algs4 hw1 at NTU BIME 106-1 PDSA class

- Be careful of using arrayList to store data, because it may cause N when you find or get something in it
- DO NOT use unionall every time when open a block that may get  Percolation, try to think what is the 
   condition of Percolation when open one more box, and be careful of that thinking
- DO NOT open the file twice to confirm N inside, try to think another way
- Be careful of dealing with matrix change line effect, +1 may go through the right wall, and same as -1 and left wall
- Remember to use object orientation Class.function to write the program
- Including the .jar file only need to specify the classpath on terminal command line, DO NOT write import here

*/

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;

public class Percolation{
  public int pointCounter=-1;
  public int matixSize=0;
  public  int serialmatrixSize=0;
  public  int[] serialMap=null; //not include 0 starting from 1
  public  UF test=null;

  public Percolation(){
  }
  public  void saveToDB(String[] inputTxt){
    //Read file and put data to database
    try(BufferedReader br2 = new BufferedReader(new FileReader(inputTxt[0]))){
      String data = br2.readLine();
      matixSize = Integer.parseInt(data);
      serialmatrixSize=matixSize*matixSize+1;
      serialMap=new int[serialmatrixSize];
      for(int i=0;i<serialmatrixSize;i++){
        data=br2.readLine();
        if  (data==null){
          break;
        }
        pointCounter++;
        String[] data2=data.split(",");
        int j=matixSize*(Integer.valueOf(data2[0])-1)+Integer.valueOf(data2[1]);
        serialMap[j]=1;
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public  void unionAll(){
    //for each position in map->judge if i position is open or not->
    //if nearby+1-1+3-3 position is out of range->if nearby is open->
    //union that!
    for (int i =1; i<serialmatrixSize;i++){
      if(serialMap[i]==1){
        if(!((i%matixSize)==0)){  // avoid jump to next line(hungry snack)
          if(serialMap[i+1]==1){
            test.union(i,i+1);
          }/*else{mayPtSerial.add(i+1);}*/
        }
        if((i+matixSize)<=(serialmatrixSize-1)){
          if(serialMap[i+matixSize]==1){
            test.union(i,i+matixSize);
          }/*else{mayPtSerial.add(i+matixSize);}*/
        }
        if((i>=1)&&(i<=matixSize)){
          test.union(i,0);
        }
        if((i>(matixSize*(matixSize-1)))&&(i<serialmatrixSize)){
          test.union(i,serialmatrixSize);
        }
      }
    }
  }

  public  int printConnected(){
    if(test.connected(0,serialmatrixSize)){
      return 0;
    }
    return -1;
  }

  public  String serialToMatrix(int serialNum){
    //System.out.println(serialNum);
    int x=serialNum%matixSize;
    int y=(serialNum/matixSize)+1;
    if (x==0){
      x = matixSize;
      y -= 1;
    }
    String output=Integer.toString(y)+","+Integer.toString(x);
    return output;
  }

  public  String chanceToConnected(){
    int top=test.find(0);
    int bot=test.find(serialmatrixSize);
    //for(int i=0;i<mayPtSerial.size();i++){
    //int target=mayPtSerial.get(i);
    // if((target>=1)&&(target<serialmatrixSize)){
    for(int target=1;target<serialmatrixSize;target++){
      int counterN=0;
      int get1=serialmatrixSize+1;
      int get2=serialmatrixSize+1;
      int get3=serialmatrixSize+1;
      int get4=serialmatrixSize+1;
      if(serialMap[target]==0){
        if(!((target%matixSize)==0)){  // avoid jump to next line(hungry snack)
          if(serialMap[target+1]==1){
            counterN++;
            get1=target+1;
          }
        }
        if((target+matixSize)<=(serialmatrixSize-1)){
          if(serialMap[target+matixSize]==1){
            counterN++;
            get2=target+matixSize;
          }
        }
        if(!((target%matixSize)==1)){  // avoid jump to next line(hungry snack)
          if(serialMap[target-1]==1){
            counterN++;
            get3=target-1;
          }
        }
        if((target-matixSize)>=1){  // avoid jump to next line(hungry snack)
          if(serialMap[target-matixSize]==1){
            counterN++;
            get4=target-matixSize;
          }
        }         
      }
      if(counterN>=1){
        int gget1=test.find(get1);
        int gget2=test.find(get2);
        int gget3=test.find(get3);
        int gget4=test.find(get4);
        if (gget1==top||gget2==top||gget3==top||gget4==top||((target>=1)&&(target<=matixSize))){
          if (gget1==bot||gget2==bot||gget3==bot||gget4==bot||((target>matixSize*(matixSize-1))&&(target<serialmatrixSize))){
            return serialToMatrix(target);
          }
        }
      }
    }
    return null;
  }


  public static void main(String[] args)  {
    Percolation P =new Percolation();
    P.saveToDB(args);
    //    if(P.matixSize>10){
    //      System.out.println("-1");
    //    }
    //    else{
    P.test=new UF(P.serialmatrixSize+2);
    P.unionAll();

    //System.out.println("---print result---");
    if (P.printConnected()==0){
      System.out.println("0");
    }
    else{
      String temp= P.chanceToConnected();//use temp to avoid run it twice below
      if(temp==null){
        //System.out.println("-1");
        System.out.println("-1");
      }
      else{
        System.out.println(temp);
        //System.out.println("-1");
      }
      //      }
    }/*
    for(int i=0;i<P.serialmatrixSize+2;i++){
      System.out.printf("%d ",P.test.find(i));
    }
    System.out.println();*/
  }
}


