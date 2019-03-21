# algs4_java_A-Search

This code is ref from https://github.com/ClaymanTwinkle/astar.  << Take a look  
Concept: http://blog.minstrel.idv.tw/2004/12/star-algorithm.html  
And did some modifies to it to fit this homework's input and rules.  
  
A* search algorithm is a well-known algorithm for pathfinding and graph traversal.Based on its main idea, there are three important values which are f, g, and h you must concern.The g value is the real distance you go step by step from the start point to the current point.The h value is an estimated distance from the current point to the goal point.And the f value is the sum of g and h, which is the main target you need to minimize to get the best solution.   Video instruction: link  
  
Assignment  
  
In this assignment, you need to implement a class "Search.java" to solve a shortest path problem using A* search algorithm.Please follow the pseudocode in the link and try to finish the rest of the class.You need to use the MinPQ in algs4.jar in your program.The h value of each point is given as an input, so you don't need to worry about this part.  
  
public class Search {  
  
    private final static int VH_length = 10; // The length of vertical and horizontal move is 10.  
    private final static int Dia_length = 14; // The length of diagonal move is 14.  
    //private MinPQ<>  
  
}  
  
Note:  
1. You must use the MinPQ to implement your program.  
2. Adding the neighbors in the order of right -> lower right -> down -> lower left -> left -> upper left -> up -> upper right.  
3. If there's two point with the same f value, make the point with larger g value have the priority in delMin.  
  
