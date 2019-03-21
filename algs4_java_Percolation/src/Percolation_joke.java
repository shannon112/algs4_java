import java.io.FileReader;
import java.io.BufferedReader;
//import algs4.*;

public class Percolation_joke{
    public static int pointCounter=-1;
    public static int matixSize=0;
    public static int serialmatrixSize=0;
    public static int[][] map=null; 
    public static int[] serialMap=null; //not include 0 starting from 1
    public static WeightedQuickUnionUF test=null;
    public static WeightedQuickUnionUF test2=null;
    public static int[] openPtSerial=null;

    public Percolation_joke(int n){
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
        }
    }
    
    public static void saveToDB(String[] inputTxt){
        //Read file and put data to database
        try(BufferedReader br2 = new BufferedReader(new FileReader(inputTxt[0]))){
            
            String data = br2.readLine();
            matixSize = Integer.parseInt(data);
            //System.out.println(N);
            serialmatrixSize=matixSize*matixSize+1+1;
            serialMap=new int[serialmatrixSize];
            openPtSerial=new int[pointCounter];

            for(int i=0;i<pointCounter;i++){
                String[] data2=br2.readLine().split(",");
                openPtSerial[i]= matixSize*(Integer.valueOf(data2[0])-1)+Integer.valueOf(data2[1]);
                   serialMap[openPtSerial[i]]=1; 
                //System.out.printf("%d %d\n", dbOfPoint[i][0],dbOfPoint[i][1]); 
               }
           }catch(Exception e){
            e.printStackTrace();
        }
    }
/*
    public static void printMap(){
    //map[x][y]
        for (int i=0;i<matixSize;i++){
            for(int j=0;j<matixSize;j++){
                System.out.printf("%d ",map[i][j]);
            }
            System.out.println();
        }
    }
*/
    public static void printserialMap(){
    //map[x][y]
        for (int i=0;i<serialmatrixSize;i++){
            System.out.printf("%d ",serialMap[i]);
        }
        System.out.println();
    }
/*
    public static void openBlockInMap(){
        for(int i =0; i<pointCounter;i++){
            map[dbOfPoint[i][0]-1][dbOfPoint[i][1]-1]=1;
        }
    }
*/


    public static void unionAll(){
    //for each position in map->judge if i position is open or not->
    //if nearby+1-1+3-3 position is out of range->if nearby is open->
    //union that!
        for (int i =0; i<pointCounter;i++){
                if(!((openPtSerial[i]%matixSize)==0)){  // avoid jump to next line(hungry snack)
                    if(serialMap[openPtSerial[i]+1]==1){
                        test.union(openPtSerial[i],openPtSerial[i]+1);
                    }
                }if(!((openPtSerial[i]%matixSize)==1)){  // avoid jump to next line(hungry snack)
                    if(serialMap[openPtSerial[i]-1]==1){
                        test.union(openPtSerial[i],openPtSerial[i]-1);
                    }
                }if((openPtSerial[i]+matixSize)<=(serialmatrixSize-2)){
                    if(serialMap[openPtSerial[i]+matixSize]==1){
                        test.union(openPtSerial[i],openPtSerial[i]+matixSize);
                    }
                }if((openPtSerial[i]-matixSize)>=1){
                    if(serialMap[openPtSerial[i]-matixSize]==1){
                        test.union(openPtSerial[i],openPtSerial[i]-matixSize);
                    }
                }
            }
        for(int j =1; j<matixSize+1;j++){//union to virtual top
            test.union(0,j);
        }
        for (int k =serialmatrixSize-2- matixSize +1;k<serialmatrixSize-1 ;k++ ) {//union to virtual bot
            test.union(serialmatrixSize-1,k);                
        }
    }


    public static void unionAll2(int newOpen){
    //for each position in map->judge if i position is open or not->
    //if nearby+1-1+3-3 position is out of range->if nearby is open->
    //union that!
        for (int i =0; i<pointCounter;i++){
                if(!((openPtSerial[i]%matixSize)==0)){  // avoid jump to next line(hungry snack)
                    if(serialMap[openPtSerial[i]+1]==1){
                        test2.union(openPtSerial[i],openPtSerial[i]+1);
                    }
                }if(!((openPtSerial[i]%matixSize)==1)){  // avoid jump to next line(hungry snack)
                    if(serialMap[openPtSerial[i]-1]==1){
                        test2.union(openPtSerial[i],openPtSerial[i]-1);
                    }
                }if((openPtSerial[i]+matixSize)<=(serialmatrixSize-2)){
                    if(serialMap[openPtSerial[i]+matixSize]==1){
                        test2.union(openPtSerial[i],openPtSerial[i]+matixSize);
                    }
                }if((openPtSerial[i]-matixSize)>=1){
                    if(serialMap[openPtSerial[i]-matixSize]==1){
                        test2.union(openPtSerial[i],openPtSerial[i]-matixSize);
                    }
                }
            }
                if(!((newOpen%matixSize)==0)){  // avoid jump to next line(hungry snack)
                    if(serialMap[newOpen+1]==1){
                        test2.union(newOpen,newOpen+1);
                    }
                }if(!((newOpen%matixSize)==1)){  // avoid jump to next line(hungry snack)
                    if(serialMap[newOpen-1]==1){
                        test2.union(newOpen,newOpen-1);
                    }
                }if((newOpen+matixSize)<=(serialmatrixSize-2)){
                    if(serialMap[newOpen+matixSize]==1){
                        test2.union(newOpen,newOpen+matixSize);
                    }
                }if((newOpen-matixSize)>=1){
                    if(serialMap[newOpen-matixSize]==1){
                        test2.union(newOpen,newOpen-matixSize);
                    }
                }
        for(int j=1;j<=matixSize;j++){//union to virtual top
            test2.union(0,j);
        }
        for (int k=serialmatrixSize-2- matixSize +1;k<=serialmatrixSize-2 ;k++ ) {//union to virtual bot
            test2.union(serialmatrixSize-1,k);                
        }
    }


    public static int printConnected(){
        if(test.connected(0,serialmatrixSize-1)){
            return 0;
        }
        return -1;
    }

    public static int printConnected2(){
        if(test2.connected(0,serialmatrixSize-1)){
            return 0;
        }
        return -1;
    }

    public static String serialToMatrix(int serialNum){
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

    public static String chanceToConnected(){
        for(int i=1;i<serialmatrixSize-1;i++){
            if(serialMap[i]==0){
                serialMap[i]=1;
                test2=new WeightedQuickUnionUF(serialmatrixSize);
                unionAll2(i);
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
        if (matixSize>10){
            System.out.println("-1");
        }else{
        test=new WeightedQuickUnionUF(serialmatrixSize);
        
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
        }
        /*
        //here is the interface help to take a look about map
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
        System.out.println();
        for(int i=1;i<serialmatrixSize;i++){
            System.out.printf("%d ",test2.find(i));
        }
        System.out.println();
        */
    }
}