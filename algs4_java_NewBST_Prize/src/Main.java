public class Main{
public static void main(String[] args){
        NewBST<Integer,Character> bst=new NewBST<Integer,Character>();
        bst.put(7,'a');
        bst.put(4,'f');
        bst.put(-6,'h');
        bst.put(9,'r');
        bst.put(84,'e');
        for (int key : bst.keys()){
        //for (int key : bst.levelOrder()){
                System.out.println(key);
        }
        for (int key : bst.keys(bst.min(),4)){
        //for (int key : bst.levelOrder()){
                System.out.println(key);
        }
        System.out.println(".........................");
        System.out.println(bst.rank(4));//list key less than 4 not include 4
        System.out.println(bst.floor(4));//list one key less than 4 include 4
        System.out.println(".........................");
        System.out.println(bst.select(0));//select number what of smallest key and output its key start from 0
        System.out.println(".........................");
        System.out.println(bst.predecessor(7,2));
        System.out.println(bst.predecessor(4,2));
        System.out.println(bst.predecessor(84,0));
        System.out.println(bst.predecessor(6,0));
        System.out.println(".........................");
        System.out.println(bst.size(9,bst.max()));
        System.out.println(".........................");
        System.out.println(bst.successor(7,2));
        System.out.println(bst.successor(4,4));
        System.out.println(bst.successor(84,0));
        System.out.println(bst.successor(6,0));
}
}
