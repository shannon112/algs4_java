import java.util.Comparator;
import java.util.*;
public class Card implements Comparable<Card> {

private String face;  // should be one of [A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K]
private String suit;  // should be one of [Spades, Hearts, Diamonds, Clubs]

public static final Comparator<Card> SUIT_ORDER = new SuitOrder();
public static final Comparator<Card> FACE_ORDER = new FaceOrder();

// DO NOT MODIFY THIS
public Card(String face, String suit){
	this.face = face;
	this.suit = suit;
}

// DO NOT MODIFY THIS
public String getFace(){
	return this.face;
}

// DO NOT MODIFY THIS
public String getSuit(){
	return this.suit;
}

// TODO
public int compareTo(Card that) {
	// complete this function so the Card can be sorted
	// (you must consider both face and suit)
	int compareFace=FACE_ORDER.compare(this,that);
	if (compareFace>0) return +1;
	else if (compareFace<0) return -1;
	else{
		int compareSuit=SUIT_ORDER.compare(this,that);
		if (compareSuit>0) return +1;
		else if (compareSuit<0) return -1;
		else return 0;
	}
}

// TODO
private static class FaceOrder implements Comparator<Card> {
	public static int tranToInt(String input){
		if(input.equals("A")) return 14;
		else if (input.equals("K")) return 13;
		else if (input.equals("Q")) return 12;
		else if (input.equals("J")) return 11;
		else return Integer.parseInt(input);
	}
	public int compare(Card c1, Card c2) {
		// complete this function so the Card can be sorted according to the suit
		int c1Face=tranToInt(c1.getFace());
		int c2Face=tranToInt(c2.getFace());
		if(c1Face>c2Face) return +1;
		else if(c1Face<c2Face) return -1;
		else return 0;
	}
}

private static class SuitOrder implements Comparator<Card> {
	public static int tranToInt(String input){
		if(input.equals("Spades")) return 4;
		else if (input.equals("Hearts")) return 3;
		else if (input.equals("Diamonds")) return 2;
		else if (input.equals("Clubs")) return 1;
		else return 0;
	}
	public int compare(Card c1, Card c2) {
		// complete this function so the Card can be sorted according to the suit
		int c1Suit=tranToInt(c1.getSuit());
		int c2Suit=tranToInt(c2.getSuit());
		if(c1Suit>c2Suit) return +1;
		else if(c1Suit<c2Suit) return -1;
		else return 0;
	}
}

public static void main(String[] args){
	Card[] set=new Card[5];
	set[1] = new Card("J","Hearts");
	set[2] = new Card("Q","Diamonds");
	set[3] = new Card("5","Diamonds");
	set[0] = new Card("A","Spades");
	set[4] = new Card("6","Clubs");
	Arrays.sort(set);
	for(int i=0;i<5;i++){
		System.out.printf("----%d----\n",i);
		System.out.println(set[i].getSuit());
		System.out.println(set[i].getFace());
	}
	Arrays.sort(set,FACE_ORDER);
	for(int i=0;i<5;i++){
		System.out.printf("----%d----\n",i);
		System.out.println(set[i].getSuit());
		System.out.println(set[i].getFace());
	}
	Arrays.sort(set,SUIT_ORDER);
	for(int i=0;i<5;i++){
		System.out.printf("----%d----\n",i);
		System.out.println(set[i].getSuit());
		System.out.println(set[i].getFace());
	}
}
}
