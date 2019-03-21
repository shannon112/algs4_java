import java.util.*;

public class Player implements Comparable<Player>{
private Card[] cards = new Card[7];
private String name;
private int[] values=new int[7];//all sorted
private int[] valuesS=new int[7];//all sorted
private int[] suits=new int[7];//only suit sorted
private int[] suitsF=new int[7];//only suit sorted
//---------------------------------------------------------
// DO NOT MODIFY THIS
public Player(String name) {
        this.name = name;
}
// DO NOT MODIFY THIS
public String getName() {
        return this.name;
}
// DO NOT MODIFY THIS
public void setCards(Card[] cards) {
        this.cards = cards;
}
//-----------------------------------------------------------
private void suits(Card[] input) {
        Arrays.sort(input,Card.SUIT_ORDER);//careful at here, Class.comparator
        for (int i = 0; i < 7; i++){
                suits[i] = suitToNum(input[i].getSuit());
                suitsF[i] = faceToNum(input[i].getFace());
        }
}

private void values(Card[] input) {
        Arrays.sort(input);
        for (int i = 0; i < 7; i++){
                values[i] = faceToNum(input[i].getFace());
                valuesS[i] = suitToNum(input[i].getSuit());
        }
}

private int faceToNum(String input){
        if(input.equals("A")) return 14;
        else if (input.equals("K")) return 13;
        else if (input.equals("Q")) return 12;
        else if (input.equals("J")) return 11;
        else return Integer.parseInt(input);
}

private int suitToNum(String input){
        if(input.equals("Spades")) return 4;
        else if (input.equals("Hearts")) return 3;
        else if (input.equals("Diamonds")) return 2;
        else if (input.equals("Clubs")) return 1;
        else return 0;
}
//------------------------------------------------------------------

//ranking double is AA.BBCC  AA is type, BB is FaceOrder, CC is suitOrder
public double getRanking() {
        if (isFourOfAKind() > 6.0) return isFourOfAKind();
        else if (isFullHouse() > 5.0) return isFullHouse();
        else if (isFlush() > 4.0) return isFlush();
        else if (isStraight() > 3.0) return isStraight();
        else if (isTwoPair() > 2.0) return isTwoPair();
        else if (isPair() > 1.0) return isPair();
        else if (isHighCard()>0.0) return isHighCard();
        else return 0;// WTF?
}

//complete output= 0.AABCCD....  AA is face B is suit need compare other card second, third....
public double isHighCard(){
        double result=0.0;
        int j=1;
        for (int i=6;i>=2;i--){
                result+= Math.pow(0.1,(j*3)-1)*values[i] + Math.pow(0.1,j*3)*valuesS[i];
                j++;
        }
        return result;
}

//complete output= 1.AABBCC  AA is face BB is suit1 CC is suit2
public double isPair() {
        double result = 0.0;
        for (int i = 0; i < 7-1; i++) {
                if (values[i] == values[i + 1]){
                        result = 1.0 + 0.01*values[i+1] + 0.0001*valuesS[i+1]+0.000001*valuesS[i];
                }
        }
        return result;
}

//complete output=2.AABC DDEF
public double isTwoPair() {
        double result = 0.0;
        double value1 = 0.0;
        double value1s1 = 0.0;
        double value1s2 = 0.0;
        double value2 = 0.0;
        double value2s1 = 0.0;
        double value2s2 = 0.0;
        int counter = 0; // Number of pairs.
        for (int i = 0; i < 7-1; i++) {
                if (values[i] == values[i + 1]) {
                        counter++;
                        value1 = 0.01*values[i+1];
                        value1s1= 0.001*valuesS[i+1];
                        value1s2= 0.0001*valuesS[i];
                        i++;
                }
        }
        if (counter >= 2){
                for (int i = 0; i < 7-1; i++) {
                        if ((values[i] == values[i + 1])&&(values[i]!=100*value1)) {
                                value2 =  0.000001*values[i+1];
                                value2s1= 0.0000001*valuesS[i+1];
                                value2s2= 0.00000001*valuesS[i];
                                i++;
                        }
                }
                result = 2.0 + value1+value1s1+value1s2+value2+value2s1+value2s2;
        }
        return result;
}

//complete output= 3.AABCCD....
public double isStraight() {
        double result = 0.0;
        boolean Succ=false;
        int value1=0;
        int suit1=0;
        int value2=0;
        int suit2=0;
        int value3=0;
        int suit3=0;
        int value4=0;
        int suit4=0;
        int value5=0;
        int suit5=0;
        int counter =0;
        for (int i = 0; i < 7-1; i++) {
                if (values[i] == values[i + 1] - 1) {
                        counter++;
                        value5=value4;
                        suit5=suit4;
                        value4=value3;
                        suit4=suit3;
                        value3=value2;
                        suit3=suit2;
                        value2=value1;
                        suit2=suit1;
                        value1=values[i+1];
                        suit1=valuesS[i+1];
                        if((value1==5)&&(value2==4)&&(value3==3)&&(value4==2)){
                                if(values[6]==14){
                                        counter++;
                                        value5=1;
                                        suit5=valuesS[6];
                                }
                        }
                        if(counter>=4){
                                result=3.0+Math.pow(0.1,2)*value1+Math.pow(0.1,3)*suit1;
                                result+=Math.pow(0.1,5)*value2+Math.pow(0.1,6)*suit2+ Math.pow(0.1,8)*value3+Math.pow(0.1,9)*suit3+ Math.pow(0.1,11)*value4+Math.pow(0.1,12)*suit4;
                                result+=Math.pow(0.1,14)*value5+Math.pow(0.1,15)*suit5;
                        }
                }else if (values[i]==values[i+1]){
                        suit1 = valuesS[i+1];
                } else counter = 0;
        }
        return result;
}

//complete
public double isFlush() {
        double result = 0.0;
        int tar_suit=0;
        int value1=0;
        int value2=0;
        int value3=0;
        int value4=0;
        int value5=0;
        int counter=0;
        for (int i = 0; i < 7-1; i++) {
                if (suits[i]==suits[i+1]) {
                        counter ++;
                        if(counter>=4){
                                tar_suit=suits[i+1];
                                result=4.0+0.1*tar_suit;
                                i=7;//force end
                        }
                }else counter= 0;
        }
        for (int i=0;i<7-1;i++){
                if (valuesS[i]==tar_suit){
                        value5=value4;
                        value4=value3;
                        value3=value2;
                        value2=value1;
                        value1=values[i];
                }
        }
        result+=value1*Math.pow(0.1,3)+value2*Math.pow(0.1,5)+value3*Math.pow(0.1,7)+value4*Math.pow(0.1,9)+value5*Math.pow(0.1,11);
        return result;
}

//complete output=5.AABB AA=3 be a pairs's num, BB=2 be a pairs's num
public double isFullHouse() {
        double result = 0.0;
        boolean three = false;
        boolean two = false;
        double value3=0.0;
        double value2=0.0;
        for (int i = 0; i < 7 - 2; i++) {
                if (values[i] == values[i + 1] && values[i] == values[i + 2]){
                        three = true;
                        value3=0.01*values[i+2];
                }
        }
        if(three){
                for(int i=0;i<7-1;i++){
                        if((values[i]==values[i+1])&&(values[i]!= value3*100)){
                                two=true;
                                value2=0.0001*values[i+1];
                        }
                }
        }
        if (three && two) result = 5.0 + value2+value3;
        return result;
}

//complete output= 06.XX  XX is num of 4 same
public double isFourOfAKind() {
        for (int i = 0; i < 7- 3; i++) {
                if ((values[i] == values[i + 1])&&(values[i] == values[i + 2]) && (values[i] == values[i + 3])) {
                        return 6.0 + 0.01*values[i];
                }
        }
        return 0;
}

public int compareTo(Player that) {
        this.values(this.cards);
        this.suits(this.cards);
        double thisRank = this.getRanking();
        that.values(that.cards);
        that.suits(that.cards);
        double thatRank = that.getRanking();

        if(thisRank>thatRank) return +1;
        else if(thisRank<thatRank) return -1;
        else return 0;
}
public static void main(String[] args){
}
}
