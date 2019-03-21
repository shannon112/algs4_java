import java.util.Arrays;
import java.io.FileReader;
import java.io.BufferedReader;

public class Search {
private static int rowNum;
private static int columeNum;
private static int[] startNum=new int[2];
private static int[] goalNum=new int[2];

private final static int VH_length = 10;
private final static int Dia_length = 14;
//private MinPQ<> ;

public class GraphNode {
    public GraphNode[] neighbors;
    public int value;
    public boolean visited;

    public GraphNode(int v) {
        this.value = v;
        this.visited = false;
    }
}

public static void main(String[] argv) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(argv[0]))) {
                String[] temp = br.readLine().split(" ");
                rowNum=Integer.parseInt(temp[1]);
                temp = br.readLine().split(" ");
                columeNum=Integer.parseInt(temp[1]);
                temp = br.readLine().split(" "); temp=temp[1].split(",");
                startNum[0]=Integer.parseInt(temp[0]); startNum[1]=Integer.parseInt(temp[1]);
                temp = br.readLine().split(" "); temp=temp[1].split(",");
                goalNum[0]=Integer.parseInt(temp[0]); goalNum[1]=Integer.parseInt(temp[1]);

                System.out.println(rowNum);
                System.out.println(columeNum);
                System.out.println(startNum[0]); System.out.println(startNum[1]);
                System.out.println(goalNum[0]); System.out.println(goalNum[1]);
        }
}
}
