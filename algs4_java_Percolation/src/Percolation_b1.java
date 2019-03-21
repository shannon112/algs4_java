import java.io.FileReader;
import java.io.BufferedReader;
//import algs4.*;

public class Percolation{
    public static int[][] dbOfPoint=null;
    public static int pointCounter=-1;
    public static int matixSize=0;
    public static int serialmatrixSize=0;
    public static int[][] map=null; 
    public static int[] serialMap=null; //not include 0 starting from 1
    //public static int[] serialMap2=null;//the map that find another block open which can connect top and bot
    public static WeightedQuickUnionUF test=null;
    public static WeightedQuickUnionUF test2=null;

    public Percolation(int n){
    }

    public static void checkHowMany(String[] inputTxt){
        //Read file and check how many point need to open
        try(BufferedReader br1 = new BufferedReader(new FileReader(inputTxt[0]))){
            while(true){
                String counterdata=br1.readLine();
                if  (counterdata==null){
                    break;
                }
                pointCounter++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{}
    }
    
    public static void saveToDB(String[] inputTxt){
        //Read file and put data to database
        try(BufferedReader br2 = new BufferedReader(new FileReader(inputTxt[0]))){
            String data = br2.readLine();
            matixSize = Integer.parseInt(data);
            //System.out.println(N);
            dbOfPoint=new int[pointCounter][2];
            for(int i=0;i<pointCounter;i++){
                String[] data2=br2.readLine().split(",");
                dbOfPoint[i][0]=Integer.valueOf(data2[0]);
                dbOfPoint[i][1]=Integer.valueOf(data2[1]);
                //System.out.printf("%d %d\n", dbOfPoint[i][0],dbOfPoint[i][1]); 
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{}
    }

    public static void printMap(){
    //map[x][y]
        for (int i=0;i<matixSize;i++){
            for(int j=0;j<matixSize;j++){
                System.out.printf("%d ",map[i][j]);
            }
            System.out.println();
        }
    }

    public static void printserialMap(){
    //map[x][y]
        for (int i=0;i<serialmatrixSize;i++){
            System.out.printf("%d ",serialMap[i]);
        }
        System.out.println();
    }

    public static void openBlockInMap(){
        for(int i =0; i<pointCounter;i++){
            map[dbOfPoint[i][0]-1][dbOfPoint[i][1]-1]=1;
        }
    }

    public static void openBlockInserialMap(){
        for(int i =0; i<pointCounter;i++){
            int j= matixSize*(dbOfPoint[i][0]-1)+dbOfPoint[i][1];
            serialMap[j]=1;
        }
    }

    public static void unionAll(){
    //for each position in map->judge if i position is open or not->
    //if nearby+1-1+3-3 position is out of range->if nearby is open->
    //union that!
        for (int i =1; i<serialmatrixSize;i++){
            if(serialMap[i]==1){
                /*if ((i-1)>=1){
                    if(serialMap[i-1]==1){
                        test.union(i,i-1);
                    }
                }*/if((i+1)<=(serialmatrixSize-1)){
                    if(serialMap[i+1]==1){
                        test.union(i,i+1);
                    }
                }if((i+matixSize)<=(serialmatrixSize-1)){
                    if(serialMap[i+matixSize]==1){
                        test.union(i,i+matixSize);
                    }
                }/*if((i-matixSize)>=1){
                    if(serialMap[i-matixSize]==1){
                        test.union(i,i-matixSize);
                    }
                }*/
            }
        }
    }

    public static void unionAll2(){
    //for each position in map->judge if i position is open or not->
    //if nearby+1-1+3-3 position is out of range->if nearby is open->
    //union that!
        for (int i =1; i<serialmatrixSize;i++){
            if(serialMap[i]==1){
                /*if ((i-1)>=1){
                    if(serialMap[i-1]==1){
                        test2.union(i,i-1);
                    }
                }*/if((i+1)<=(serialmatrixSize-1)){
                    if(serialMap[i+1]==1){
                        test2.union(i,i+1);
                    }
                }if((i+matixSize)<=(serialmatrixSize-1)){
                    if(serialMap[i+matixSize]==1){
                        test2.union(i,i+matixSize);
                    }
                }/*if((i-matixSize)>=1){
                    if(serialMap[i-matixSize]==1){
                        test2.union(i,i-matixSize);
                    }
                }*/
            }
        }
    }    

    public static int printConnected(){
        for(int i =1;i<matixSize+1;i++){
            for(int j=0;j<matixSize;j++){
                if(test.connected(i,(1+matixSize*(matixSize-1)+j)))
                    return 0;
            }
        }
        return -1;
    }

    public static int printConnected2(){
        for(int i =1;i<matixSize+1;i++){
            for(int j=0;j<matixSize;j++){
                if(test2.connected(i,(1+matixSize*(matixSize-1)+j)))
                    return 0;
            }
        }
        return -1;
    }

    public static String serialToMatrix(int serialNum){
        //System.out.println(serialNum);
        int x=serialNum%matixSize;
        if (x==0){
            x=matixSize;
        }
        int y=(serialNum/matixSize)+1;
        String output=Integer.toString(y)+","+Integer.toString(x);
        return output;
    }

    public static String chanceToConnected(){
        for(int i=1;i<serialmatrixSize;i++){
            if(serialMap[i]==0){
                serialMap[i]=1;
                test2=new WeightedQuickUnionUF(serialmatrixSize+1);
                unionAll2();
                if (printConnected2()==0){
                    //System.out.printf("got it ! %d \n",i);
                    return serialToMatrix(i);
                }
                serialMap[i]=0;
            }
        }
        return null;
    }

    public static void main(String[] args)  {
        checkHowMany(args);
        saveToDB(args);

        serialmatrixSize=matixSize*matixSize+1;
        serialMap=new int[serialmatrixSize];
        openBlockInserialMap();

        test=new WeightedQuickUnionUF(serialmatrixSize+1);
        unionAll();

        //System.out.println("---print result---");
        if (printConnected()==0){
            System.out.println("0");
        }else{
            String temp= chanceToConnected();//use temp to avoid run it twice below
            if(temp==null){
                System.out.println("-1");
            }else{
                System.out.println(temp);
            }
        }
        /*
        map=new int[matixSize][matixSize];
        openBlockInMap();
        printMap();
        printserialMap();
        
        
        for(int i=1;i<serialmatrixSize;i++){
            System.out.printf("%d ",i);
        }
        System.out.println();
        for(int i=1;i<serialmatrixSize;i++){
            System.out.printf("%d ",test.find(i));
        }
        */

        // initialization of a Percolation data structure
        //Percolation percolation = new Percolation(matixSize);
        /*
        *  [hint]
        *  you can refer UF.java or WeightedQuickUnionUF.java here  http://algs4.cs.princeton.edu/code/
        */
        
    }
}

