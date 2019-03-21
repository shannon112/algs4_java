import java.util.*;

public class Player implements Comparable<Player>{
private Card[] cards = new Card[7]; //self-own two cards and community cards
private Card[] selects=new Card[5]; //self-own two cards plus C5get3 community cards

private String name; //player

private int[] values=new int[5];//all sorted (face and suit) array ->face value
private int[] valuesS=new int[5];//all sorted (face and suit) array ->suit value
private int[] suits=new int[5];//only suit sorted array ->suit value
private int[] suitsF=new int[5];//only suit sorted array ->face value
//---------------------------------------------------------
//Fixed---------------------------------------------------------
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
//GetData-----------------------------------------------------------
//-----------------------------------------------------------
private void suits(Card[] input) {
        Arrays.sort(input,Card.SUIT_ORDER);//careful at here, Class.comparator
        for (int i = 0; i < 5; i++) {
                suits[i] = suitToNum(input[i].getSuit());
                suitsF[i] = faceToNum(input[i].getFace());
        }
}

private void values(Card[] input) {
        Arrays.sort(input);
        for (int i = 0; i < 5; i++) {
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

//-----------------------------------------------------------
//Ranking-----------------------------------------------------------
//-----------------------------------------------------------
public double getRanking() {
        if (isFourOfAKind() > 6.0) return isFourOfAKind();
        else if (isFullHouse() > 5.0) return isFullHouse();
        else if (isFlush() > 4.0) return isFlush();
        else if (isStraight() > 3.0) return isStraight();
        else if (isTwoPair() > 2.0) return isTwoPair();
        else if (isPair() > 1.0) return isPair();
        else if (isHighCard()>0.0) return isHighCard();
        else return 0; // WTF?
}

//complete output= 0.AABCCD....  AA is max face B is A's suit, CC is second....
public double isHighCard(){
        double result=0.0;
        result=0.0+
                Math.pow(0.1,2)*values[4]+Math.pow(0.1,3)*valuesS[4]+
                Math.pow(0.1,5)*values[3]+Math.pow(0.1,6)*valuesS[3]+
                Math.pow(0.1,8)*values[2]+Math.pow(0.1,9)*valuesS[2]+
                Math.pow(0.1,11)*values[1]+Math.pow(0.1,12)*valuesS[1]+
                Math.pow(0.1,14)*values[0]+Math.pow(0.1,15)*valuesS[0];
        return result;
}

//complete output= 1.AABC  AA is face B is suit1 C is suit2
public double isPair() {
        double result = 0.0;
        for (int i = 0; i < 5-1; i++) {
                if (values[i] == values[i + 1]) {
                        result = 1.0 + 0.01*values[i+1] + 0.001*valuesS[i+1]+0.0001*valuesS[i];
                }
        }
        return result;
}

//complete output=2.AABB AA is max pair face, BB is second pair face
public double isTwoPair() {
        double result = 0.0;
        double value1 = 0.0;
        double value2 = 0.0;
        int counter = 0; // Number of pairs.
        for (int i = 0; i < 5-1; i++) {
                if (values[i] == values[i + 1]) {
                        counter++;
                        if(counter==1) value1 = 0.01*values[i];
                        else if(counter==2){
                                value2 = 0.0001*values[i];
                                result = 2.0 + value1+value2;
                        }
                        i++; //skip i+1 term
                }
        }
        return result;
}

//complete output= 3.AABCCD....  AA is max face B is A's suit, CC is second....
public double isStraight() {
        double result = 0.0;
        //boolean Succ=false;
        int counter =0;
        for (int i = 0; i < 5-1; i++) {
                if (values[i] == values[i + 1] - 1) counter++;
                else counter=0;
        }
        if (counter==4) {
                //Succ=true;
                result=3.0+
                        Math.pow(0.1,2)*values[4]+Math.pow(0.1,3)*valuesS[4]+
                        Math.pow(0.1,5)*values[3]+Math.pow(0.1,6)*valuesS[3]+
                        Math.pow(0.1,8)*values[2]+Math.pow(0.1,9)*valuesS[2]+
                        Math.pow(0.1,11)*values[1]+Math.pow(0.1,12)*valuesS[1]+
                        Math.pow(0.1,14)*values[0]+Math.pow(0.1,15)*valuesS[0];
        }else if(counter==3) {
                if((values[4]==14)&&(values[3]==5)&&(values[2]==4)&&(values[1]==3)&&(values[0]==2)) {
                        //Succ=true;
                        result=3.0+
                                Math.pow(0.1,2)*values[3]+Math.pow(0.1,3)*valuesS[3]+
                                Math.pow(0.1,5)*values[2]+Math.pow(0.1,6)*valuesS[2]+
                                Math.pow(0.1,8)*values[1]+Math.pow(0.1,9)*valuesS[1]+
                                Math.pow(0.1,11)*values[0]+Math.pow(0.1,12)*valuesS[0]+
                                Math.pow(0.1,14)*1.0+Math.pow(0.1,15)*valuesS[4];//values[4]==14 as 1
                }
        }else{
                //Succ=false;
        }
        return result;
}

//complete output= 4.AABCCD....  AA is max face B is A's suit, CC is second....
/*another method
public double isFlush() {
        double result = 0.0;
        boolean isFlush=false;
        if(suits[0]==suits[4]) isFlush=true;
        if(isFlush) {
                result=4.0+
                        Math.pow(0.1,2)*values[4]+Math.pow(0.1,3)*valuesS[4]+
                        Math.pow(0.1,5)*values[3]+Math.pow(0.1,6)*valuesS[3]+
                        Math.pow(0.1,8)*values[2]+Math.pow(0.1,9)*valuesS[2]+
                        Math.pow(0.1,11)*values[1]+Math.pow(0.1,12)*valuesS[1]+
                        Math.pow(0.1,14)*values[0]+Math.pow(0.1,15)*valuesS[0];
        }
        return result;
}
*/
//old method
public double isFlush() {
        double result = 0.0;
        int counter=0;
        for (int i = 0; i < 5-1; i++) {
                if (suits[i]==suits[i+1]) counter++;
                else counter= 0;
        }
        if(counter>=4) {
                result=4.0+
                        Math.pow(0.1,2)*values[4]+Math.pow(0.1,3)*valuesS[4]+
                        Math.pow(0.1,5)*values[3]+Math.pow(0.1,6)*valuesS[3]+
                        Math.pow(0.1,8)*values[2]+Math.pow(0.1,9)*valuesS[2]+
                        Math.pow(0.1,11)*values[1]+Math.pow(0.1,12)*valuesS[1]+
                        Math.pow(0.1,14)*values[0]+Math.pow(0.1,15)*valuesS[0];
        }
        return result;
}


//complete output=5.AABB AA=3cards as a pairs's face, BB=2cards as a pairs's face
public double isFullHouse() {
        double result = 0.0;
        boolean three = false;
        boolean two = false;
        double value3=0.0;
        double value2=0.0;
        int third=0;
        for (int i = 0; i < 5 - 2; i++) {
                if ((values[i] == values[i + 1]) && (values[i] == values[i + 2])) {
                        three = true;
                        third=values[i];
                        value3=0.01*values[i];
                }
        }
        if(three) {
                for(int i=0; i<5-1; i++) {
                        if((values[i]==values[i+1])&&(!(values[i]== third))) { //Using third and !(==) find the bug
                                two=true;
                                value2=0.0001*values[i];
                                result = 5.0 + value2 + value3;
                        }
                }
        }
        return result;
}

//complete output= 06.XX  XX is face of 4 same
/*another method
public double isFourOfAKind() {
        if(values[0] == values[3]) return 6.0 + 0.01*values[0];
        else if(values[1] == values[4]) return 6.0 + 0.01*values[1];
        else return 0;
}
*/
//old method
public double isFourOfAKind() {
        for (int i = 0; i < 5- 3; i++) {
                if ((values[i] == values[i + 1])&&(values[i] == values[i + 2]) && (values[i] == values[i + 3])) {
                        return 6.0 + 0.01*values[i];
                }
        }
        return 0;
}


public int compareTo(Player that) {
        int[][] select= new int[10][3];//C5get3 all possible selcet
        select[0][0]=2; select[0][1]=3; select[0][2]=4;
        select[1][0]=2; select[1][1]=3; select[1][2]=5;
        select[2][0]=2; select[2][1]=3; select[2][2]=6;
        select[3][0]=2; select[3][1]=4; select[3][2]=5;
        select[4][0]=2; select[4][1]=4; select[4][2]=6;
        select[5][0]=2; select[5][1]=5; select[5][2]=6;
        select[6][0]=3; select[6][1]=4; select[6][2]=5;
        select[7][0]=3; select[7][1]=4; select[7][2]=6;
        select[8][0]=3; select[8][1]=5; select[8][2]=6;
        select[9][0]=4; select[9][1]=5; select[9][2]=6;

        double thisRank=0.0;
        double thisFinalRank=0.0;
        for(int i=0; i<10; i++) {
                this.selects[0]=this.cards[0];
                this.selects[1]=this.cards[1];
                this.selects[2]=this.cards[select[i][0]];
                this.selects[3]=this.cards[select[i][1]];
                this.selects[4]=this.cards[select[i][2]];
                this.values(this.selects);
                this.suits(this.selects);
                thisRank = this.getRanking();
                if(thisRank>thisFinalRank) thisFinalRank = thisRank;
        }

        double thatRank=0.0;
        double thatFinalRank=0.0;
        for(int i=0; i<10; i++) {
                that.selects[0]=that.cards[0];
                that.selects[1]=that.cards[1];
                that.selects[2]=that.cards[select[i][0]];
                that.selects[3]=that.cards[select[i][1]];
                that.selects[4]=that.cards[select[i][2]];
                that.values(that.selects);
                that.suits(that.selects);
                thatRank = that.getRanking();
                if(thatRank>thatFinalRank) thatFinalRank = thatRank;
        }

        if(thisFinalRank>thatFinalRank) return +1;
        else if(thisFinalRank<thatFinalRank) return -1;
        else return 0;
}
public static void main(String[] args){
}

}
