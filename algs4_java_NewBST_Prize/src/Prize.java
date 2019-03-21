import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class Prize {
    private static NewBST<Integer,String> myBST=new NewBST<Integer,String>();//predecessor successor (Key key, int m)
    private static int  N,M,W;
    private static ArrayList<String> failPeople = new ArrayList<String>();
    private static ArrayList<String> secondPrizePeople = new ArrayList<String>();

    public static void secondPrize(){
            for(int i=1;i<=M;i++){
                    if(secondPrizePeople.contains(myBST.predecessor(W,i))==false) secondPrizePeople.add(myBST.predecessor(W,i));
                    if(secondPrizePeople.contains(myBST.successor(W,i))==false) secondPrizePeople.add(myBST.successor(W,i));
            }
    }

    public static void printResult(){
            if(failPeople.isEmpty()==false){//print failers
                    //Collections.sort(failPeople);//you dont need to sort here
                    for(int i=0;i<failPeople.size();i++) System.out.println(failPeople.get(i)+" "+"fail");
            }
            System.out.println(myBST.get(W));//print winner
            if(secondPrizePeople.isEmpty()==false){//print second
                    Collections.sort(secondPrizePeople);
                    for(int i=0;i<secondPrizePeople.size();i++) System.out.println(secondPrizePeople.get(i));
            }
    }

    public static void main(String[] argv) throws Exception {
            //input data from sample0.in
            try (BufferedReader br = new BufferedReader(new FileReader(argv[0]))) {
                    String[] temp = br.readLine().split(" ");
                    N=Integer.parseInt(temp[0]);
                    M=Integer.parseInt(temp[1]);
                    W=Integer.parseInt(temp[2]);
                    for(String in = br.readLine(); in != null; in = br.readLine()) {
                            temp = in.split(" ");
                            if(myBST.contains(Integer.parseInt(temp[1]))) failPeople.add(temp[0]);
                            else if((Integer.parseInt(temp[1])>500*N)||(Integer.parseInt(temp[1])<-1*500*N)) failPeople.add(temp[0]);
                            else myBST.put(Integer.parseInt(temp[1]),temp[0]);
                    }
                    secondPrize();
                    printResult();
            }
    }
}
