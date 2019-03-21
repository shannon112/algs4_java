# algs4_java_NewBST_Prize

# Assignment 7-1: NewBST  
  
Finding a given node x's inorder predecessors and successors is useful in many applications.
However, they are not implemented in Class BST from algs4.jar. Let's do it by ourselves.  
   
In this assignment, you need to implement a class "NewBST.java".  
Try to inherit BST.java in algs4.jar so you can find a node x's predecessors and successors, following the API below:  
   
public class NewBST<Key extends Comparable<Key>, Value> extends BST{  
    public Value predecessor(Key key, int m){  
    }  
    public Value successor (Key key, int m){   
    }  
}  
   
Rules:  
  
1.Return the m-th predecessor or successor.   
2.If there is no such element, go to the other end and search, as picture b.  
3.If the key does not exist, return null.  
   
For example, in the NewBST as picture a (key: int, value: char).  
  
predecessor(7, 2)=h; predecessor(4, 2)=e; predecessor(84, 0)=e; predecessor(6, 0)=null
  
We won't execute main() in this program.  
hw7-1.zip  
  
# Assignment 7-2: Prize
   
There is a festival in PDSA city called LOTTO festival.  
On this day, every citizen of this city can assign for a lucky draw.  
The procedures are all on Net, as following:  
1.  Total N people can join. N>0  
2.  Enter your name and a number X. -500N<=X<=500N. X must not been chosen by another person yet.(If so, the computer will tell you you fail. Then you can try with another X.)  
3.  After N peopleregistered(?), a winning number W will be chosen among participants�� X.   
The person with that number wins the first prize. M people with larger X and M people with smaller X that are nearest to W win the second prize.  
  
In this assignment, you need to write a program "Prize.java", which reads N, M, W, names and their corresponding X. Print winners of the game.  
Use the Class NewBST you��ve built in 7-1 to store data and search, copy the class to this program.  
   
Example Input, output  
1. There are two special cases as the table.  
2. It's promised that all the input constraints are followed (e.g. don't worry if N is positive).  
   
Example Input  
// First line gives N, M, W.  
// Followings are names and their number.  
Example Output  
// First line print the name of the person who wins first prize.  
// Followings print names of people who win second prize alphabetically.  
// If a number is already chosen, print (Name) fail.  
# ex1.Simple  
5 1 5  
Eddy 8  
Alan 21  
Scott 5  
Alex 25  
Stanly 1  
  
# ex1.Simple output
Scott  
Eddy  
Stanly  
  
# ex2.Only p (p<M) people has smaller/bigger X than the winner.
6 2 5  
Eddy 8  
Alan 21  
Scott 5  
Alex 25  
Stanly 1  
Joe 17  
  
# ex2.Turn to the other end and find people with the M-p biggest/smallest X.
In this case, 1 person has smaller X than Scott.  
Scott  
Alex  
Eddy  
Joe  
Stanly  
  
# ex3.Duplicate X
5 1 5  
Eddy 8  
Scott 5  
Alex 25  
Raj 25  
Dan 5  
Alan 21  
Joe 17  
  
# ex3.Print fail message, read until there are N people and output
Raj fail  
Dan fail  
Scott  
Alex  
Eddy  
