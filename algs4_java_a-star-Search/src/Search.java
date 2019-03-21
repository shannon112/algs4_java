//package com.kesar.a;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.Arrays;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * ClassName: AStar
 * @Description: A星算法
 * @author kesar
 * https://github.com/ClaymanTwinkle/astar
 * @modified by Shannon b03505011
 */

public class Search{

public final static int BAR = 1;         // 障碍值
public final static int PATH = 2;         // 路径
public final static int VH_length = 10;         // 横竖移动代价
public final static int Dia_length = 14;         // 斜移动代价

private static int rowNum;
private static int columeNum;
private static int[] startNum=new int[2];
private static int[] goalNum=new int[2];

Queue<Node> openList = new PriorityQueue<Node>();         // 优先队列(升序)
List<Node> closeList = new ArrayList<Node>();

//***开始算法***
public void start(MapInfo mapInfo){
        if(mapInfo==null) return;
        // clean
        openList.clear();
        closeList.clear();
        // 开始搜索
        openList.add(mapInfo.start);//input the first point first
        moveNodes(mapInfo);
}

//***移动当前结点***
private void moveNodes(MapInfo mapInfo){
        while (!openList.isEmpty()){
                Node current = openList.poll();
                closeList.add(current);
                if (isCoordInClose(mapInfo.end.coord)){
                        drawPath(mapInfo.maps, mapInfo.end);
                        break;
                }
                addNeighborNodeInOpen(mapInfo,current);
        }
}

//***在二维数组中绘制路径***
private void drawPath(int[][] maps, Node end){
        Stack<String> output = new Stack<String>();
        int counter=0;
        if(end==null||maps==null) return;
        //System.out.println("总代价：" + end.G);
        System.out.println(end.G);
        while (end != null){
                Coord c = end.coord;
                maps[c.y][c.x] = PATH;
                String point=new String((c.y+2)+","+(c.x+2));
                output.push(point);
                counter++;
                end = end.parent;
        }
        while(counter>0){
                System.out.println(output.pop());
                counter--;
        }
}

//***添加所有邻结点到open表***
private void addNeighborNodeInOpen(MapInfo mapInfo,Node current){
        int x = current.coord.x;
        int y = current.coord.y;
        //座標是(row,col)，所以第一個加入的右邊鄰居是(row,col+1)
        addNeighborNodeInOpen(mapInfo,current, x + 1, y, VH_length);// 右
        addNeighborNodeInOpen(mapInfo,current, x + 1, y + 1, Dia_length);// 右下
        addNeighborNodeInOpen(mapInfo,current, x, y + 1, VH_length);// 下
        addNeighborNodeInOpen(mapInfo,current, x - 1, y + 1, Dia_length);// 左下
        addNeighborNodeInOpen(mapInfo,current, x - 1, y, VH_length);// 左
        addNeighborNodeInOpen(mapInfo,current, x - 1, y - 1, Dia_length);// 左上
        addNeighborNodeInOpen(mapInfo,current, x, y - 1, VH_length);// 上
        addNeighborNodeInOpen(mapInfo,current, x + 1, y - 1, Dia_length);// 右上
}

//***添加一个邻结点到open表***
private void addNeighborNodeInOpen(MapInfo mapInfo,Node current, int x, int y, int value){
        if (canAddNodeToOpen(mapInfo,x,y)){
                Node end=mapInfo.end;
                Coord coord = new Coord(x, y);
                int G = current.G + value;         // 计算邻结点的G值
                Node child = findNodeInOpen(coord);
                if (child == null){
                        int H=calcH(mapInfo,end.coord,coord);         // 计算H值
                        if(isEndNode(end.coord,coord)){
                                child=end;
                                child.parent=current;
                                child.G=G;
                                child.H=H;
                        }
                        else child = new Node(coord, current, G, H);
                        openList.add(child);
                }
                else if (child.G > G){
                //else if (child.G >= G){
                        child.G = G;
                        child.parent = current;
                        //openList.add(child);
                }
        }
}

//***从Open列表中查找结点***
private Node findNodeInOpen(Coord coord){
        if (coord == null || openList.isEmpty()) return null;
        for (Node node : openList){
                if (node.coord.equals(coord)) return node;
        }
        return null;
}

//***method 1:input H的估值***
//***method 2:计算H的估值：“曼哈顿”法，坐标分别取差值相加***
private int calcH(MapInfo mapInfo, Coord end, Coord coord){
        //method 1
        return mapInfo.mapsH[coord.y][coord.x];
        //method 2
        //return Math.abs(end.x - coord.x) + Math.abs(end.y - coord.y);
}

//***判断结点是否是最终结点***
private boolean isEndNode(Coord end,Coord coord){
        return coord != null && end.equals(coord);
}

//***判断结点能否放入Open列表***
private boolean canAddNodeToOpen(MapInfo mapInfo,int x, int y){
        // 是否在地图中
        if (x < 0 || x >= mapInfo.width || y < 0 || y >= mapInfo.hight) return false;
        // 判断是否是不可通过的结点
        if (mapInfo.maps[y][x] == BAR) return false;
        // 判断结点是否存在close表
        if (isCoordInClose(x, y)) return false;
        return true;
}

//***判断坐标是否在close表中***
private boolean isCoordInClose(Coord coord){
        return coord!=null&&isCoordInClose(coord.x, coord.y);
}

//***判断坐标是否在close表中***
private boolean isCoordInClose(int x, int y){
        if (closeList.isEmpty()) return false;
        for (Node node : closeList){
                if (node.coord.x == x && node.coord.y == y) return true;
        }
        return false;
}

//***打印地图***
public static void printMap(int[][] maps,int[][] mapsH){
        //path map
        for (int i = 0; i < maps.length; i++){
                for (int j = 0; j < maps[i].length; j++){
                        //if(maps[i][j]==2) System.out.println((i+2)+","+(j+2));
                        System.out.print(maps[i][j] + " ");
                }
                System.out.println();
        }
        //H map
        System.out.println("----------------------------");
        for (int i = 0; i < mapsH.length; i++){
                for (int j = 0; j < mapsH[i].length; j++){
                        //if(maps[i][j]==2) System.out.println((i+2)+","+(j+2));
                        System.out.print(mapsH[i][j] + " ");
                }
                System.out.println();
        }
}

public static void main(String[] argv) throws Exception {
        //input data from sample0.in
        try (BufferedReader br = new BufferedReader(new FileReader(argv[0]))) {
                String[] temp = br.readLine().split(" ");
                rowNum=Integer.parseInt(temp[1]);//height
                temp = br.readLine().split(" ");
                columeNum=Integer.parseInt(temp[1]);//width
                temp = br.readLine().split(" "); temp=temp[1].split(",");
                startNum[0]=Integer.parseInt(temp[0]);//start point y = row
                startNum[1]=Integer.parseInt(temp[1]);//start point x = colume
                temp = br.readLine().split(" "); temp=temp[1].split(",");
                goalNum[0]=Integer.parseInt(temp[0]);//end point y = row
                goalNum[1]=Integer.parseInt(temp[1]);//end point x = colume

                //System.out.println(rowNum);
                //System.out.println(columeNum);
                //System.out.println(startNum[0]); System.out.println(startNum[1]);
                //System.out.println(goalNum[0]); System.out.println(goalNum[1]);

                //save as a 2D map without 4 edge of "nn"
                String[] row = br.readLine().split(" ");//we dont use first row and last row
                rowNum=rowNum-2;
                columeNum=columeNum-2;
                int[][] maps = new int[rowNum][columeNum];
                int[][] mapsH = new int[rowNum][columeNum];
                for(int i=0; i<rowNum; i++) {
                        row = br.readLine().split(" ");
                        for(int j=1; j<row.length-1; j++) {//skip left and right "nn"
                                if(row[j].equals("nn")){
                                        maps[i][j-1]=1;
                                        mapsH[i][j-1]=99999;
                                }
                                else{
                                        maps[i][j-1]=0;
                                        mapsH[i][j-1]=Integer.valueOf(row[j]);
                                }
                        }
                }
                //row = br.readLine().split(" ");//we dont use last row
                MapInfo info=new MapInfo(maps, mapsH, columeNum, rowNum, new Node(startNum[1]-2, startNum[0]-2), new Node(goalNum[1]-2,goalNum[0]-2)); //Noed=(colume,row) including 0
                new Search().start(info);
                //printMap(maps,mapsH);
        }
}

public static class Coord{//(row, colume)
        public int x;
        public int y;
        public Coord(int x, int y){
                this.x = x;
                this.y = y;
        }
        @Override
        public boolean equals(Object obj){
                if (obj == null) return false;
                if (obj instanceof Coord){
                        Coord cobj = (Coord) obj;
                        return (this.x == cobj.x) && (this.y == cobj.y);
                }
                return false;
        }
}

public static class Node implements Comparable<Node>{//coordCoord nodeParant G H
        public Coord coord;                   // 坐标
        public Node parent;                   // 父结点
        public int G;                         // G：是个准确的值，是起点到当前结点的代价
        public int H;                         // H：是个估值，当前结点到目的结点的估计代价

        public Node(int x, int y){
                this.coord = new Coord(x, y);
        }

        public Node(Coord coord, Node parent, int g, int h){
                this.coord = coord;
                this.parent = parent;
                this.G = g;
                this.H = h;
        }

        @Override
        public int compareTo(Node o){
                if (o == null) return 1;
                if ((this.G + this.H) > (o.G + o.H)) return 1;
                else if ((this.G + this.H) < (o.G + o.H)) return -1;
                else if ((this.G + this.H) == (o.G + o.H)){
                        if(this.G>o.G) return -1;      //當f值相同時，要讓 g 值大的在 delMin() 時能被優先取出
                        else if(this.G<o.G) return 1;
                }
                return 0;
        }
}

public static class MapInfo {
        public int[][] maps;                         // 二维数组的地图
        public int[][] mapsH;                        // 2D map of estimate distance
        public int width;                         // 地图的宽
        public int hight;                         // 地图的高
        public Node start;                         // 起始结点
        public Node end;                         // 最终结点
        public MapInfo(int[][] maps,int[][] mapsH, int width, int hight, Node start, Node end){
                this.maps = maps;
                this.mapsH = mapsH;
                this.width = width;
                this.hight = hight;
                this.start = start;
                this.end = end;
        }
}
}
