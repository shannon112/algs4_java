import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Arrays;
//import algs4.*;


public class Percolation{
    public static int pointCounter=-1;
    public static int matixSize=0;
    public static int serialmatrixSize=0;
    //public static int[][] map=null; 
    //public static int[] serialMap=null; //not include 0 starting from 1
    public static UF test=null;
    public static UF test2=null;
    public static int[] openPtSerial=null;

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
        }
    }
    
    public static void saveToDB(String[] inputTxt){
        //Read file and put data to database
        try(BufferedReader br2 = new BufferedReader(new FileReader(inputTxt[0]))){

            String data = br2.readLine();
            matixSize = Integer.parseInt(data);
            //System.out.println(N);
            serialmatrixSize=matixSize*matixSize+1+1;
            //serialMap=new int[serialmatrixSize];
            openPtSerial=new int[pointCounter];

            for(int i=0;i<pointCounter;i++){
                String[] data2=br2.readLine().split(",");
                openPtSerial[i]= matixSize*(Integer.valueOf(data2[0])-1)+Integer.valueOf(data2[1]);
                //serialMap[openPtSerial[i]]=1; 
                //System.out.printf("%d %d\n", dbOfPoint[i][0],dbOfPoint[i][1]); 
            }
            Arrays.sort(openPtSerial);
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
    /*
    public static void printserialMap(){
    //map[x][y]
        for (int i=0;i<serialmatrixSize;i++){
            System.out.printf("%d ",serialMap[i]);
        }
        System.out.println();
    }
    */
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
                for(int j =1; j<matixSize+1;j++){//union to virtual top
                    int topline = Arrays.binarySearch(openPtSerial,j);
                    if (!(topline<0)){
                        test.union(topline,pointCounter);
                    }
                }

        for (int k =serialmatrixSize-2- matixSize +1;k<serialmatrixSize-1 ;k++ ) {//union to virtual bot
         int botline = Arrays.binarySearch(openPtSerial,k);
         if(!(botline<0)){
            test.union(botline,pointCounter+1);
        }
    }
    for (int i =0; i<pointCounter;i++){
                if(!((openPtSerial[i]%matixSize)==0)){  // avoid jump to next line(hungry snack)
                    int right = Arrays.binarySearch(openPtSerial,openPtSerial[i]+1);
                    if(!(right<0)){
                        test.union(i,right);
                    }
                }if(!((openPtSerial[i]%matixSize)==1)){  // avoid jump to next line(hungry snack)
                    int left = Arrays.binarySearch(openPtSerial,openPtSerial[i]-1);
                    if(!(left<0)){
                        test.union(i,left);
                    }
                }if((openPtSerial[i]+matixSize)<=(serialmatrixSize-2)){
                    int top = Arrays.binarySearch(openPtSerial,openPtSerial[i]+matixSize);
                    if(!(top<0)){
                        test.union(i,top);
                    }
                }if((openPtSerial[i]-matixSize)>=1){
                    int bot = Arrays.binarySearch(openPtSerial,openPtSerial[i]-matixSize);
                    if(!(bot<0)){
                        test.union(i,bot);
                    }
                }
            }


        }

        public static void unionAll2(int newOpen){
    //for each position in map->judge if i position is open or not->
    //if nearby+1-1+3-3 position is out of range->if nearby is open->
    //union that!
            for(int j =1; j<matixSize+1;j++){//union to virtual top
                int topline = Arrays.binarySearch(openPtSerial,j);
                if (!(topline<0)){
                    test.union(topline,pointCounter);
                }
                if (newOpen==j){
                   test.union(pointCounter+2,pointCounter);               
               }
           }

        for (int k =serialmatrixSize-2- matixSize +1;k<serialmatrixSize-1 ;k++ ) {//union to virtual bot
         int botline = Arrays.binarySearch(openPtSerial,k);
         if(!(botline<0)){
            test.union(botline,pointCounter+1);
        }if (newOpen==k){
           test.union(pointCounter+2,pointCounter+1);               
       }
   }

                if(!((newOpen%matixSize)==0)){  // avoid jump to next line(hungry snack)
                    int nright = Arrays.binarySearch(openPtSerial,newOpen+1);
                    if(!(nright<0)){
                        test.union(pointCounter+2,nright);
                    }
                }if(!((newOpen%matixSize)==1)){  // avoid jump to next line(hungry snack)
                    int nleft = Arrays.binarySearch(openPtSerial,newOpen-1);
                    if(!(nleft<0)){
                        test.union(pointCounter+2,nleft);
                    }
                }if((newOpen+matixSize)<=(serialmatrixSize-2)){
                    int ntop = Arrays.binarySearch(openPtSerial,newOpen+matixSize);
                    if(!(ntop<0)){
                        test.union(pointCounter+2,ntop);
                    }
                }if((newOpen-matixSize)>=1){
                    int nbot = Arrays.binarySearch(openPtSerial,newOpen-matixSize);
                    if(!(nbot<0)){
                        test.union(pointCounter+2,nbot);
                    }
                }

                for (int i =0; i<pointCounter;i++){

                if(!((openPtSerial[i]%matixSize)==0)){  // avoid jump to next line(hungry snack)
                    int right = Arrays.binarySearch(openPtSerial,openPtSerial[i]+1);
                    if(!(right<0)){
                        test.union(i,right);
                    }
                }if(!((openPtSerial[i]%matixSize)==1)){  // avoid jump to next line(hungry snack)
                    int left = Arrays.binarySearch(openPtSerial,openPtSerial[i]-1);
                    if(!(left<0)){
                        test.union(i,left);
                    }
                }if((openPtSerial[i]+matixSize)<=(serialmatrixSize-2)){
                    int top = Arrays.binarySearch(openPtSerial,openPtSerial[i]+matixSize);
                    if(!(top<0)){
                        test.union(i,top);
                    }
                }if((openPtSerial[i]-matixSize)>=1){
                    int bot = Arrays.binarySearch(openPtSerial,openPtSerial[i]-matixSize);
                    if(!(bot<0)){
                        test.union(i,bot);
                    }
                }
            }




        }


        public static int printConnected(){
            if(test.connected(pointCounter,pointCounter+1)){
                return 0;
            }
            return -1;
        }

        public static int printConnected2(){
            if(test.connected(pointCounter,pointCounter+1)){
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
                int wantNew = Arrays.binarySearch(openPtSerial,i);
                if(wantNew<0){//notice!!!there is search for those num that NOT in openPtSerial
                    test=new UF(pointCounter+3);
                    unionAll2(i);
                    if (printConnected2()==0){
                    //System.out.printf("got it ! %d \n",i);
                        return serialToMatrix(i);
                    }
                }
            }
            return null;
        }

        public static void main(String[] args)  {
            checkHowMany(args);
            saveToDB(args);       
        test=new UF(pointCounter+2);//include {openPtSerial, top, bot}
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
        for (int i=0;i<pointCounter;i++){
            System.out.printf("%d ",Arrays.binarySearch(openPtSerial,openPtSerial[i]));
        }
        System.out.println();
        System.out.println("number stored inside:");
        for(int i=0; i<pointCounter;i++){
            System.out.printf("%d, ",openPtSerial[i]);
        }
        System.out.println();
        System.out.println("union of each index test1:");
        for (int i=0;i<pointCounter+2;i++){
            System.out.printf("%d ",test.find(i));
        }
        System.out.println();
        System.out.println("union of each index test2:");

        for (int i=0;i<pointCounter+3;i++){
            System.out.printf("%d ",test.find(i));
        }
        System.out.println();
        
        */
    }
}
