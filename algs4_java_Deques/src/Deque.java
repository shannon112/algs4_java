import java.util.*;
import java.lang.*;

//You need to finish the following Deque API.
//There are exceptions need to be handled(eg. NullPointerException)
//and performance requirements. Read hw3.html cardfully and thoroughly!

public class Deque<Item> implements Iterable<Item> {
private int n;
private Node first;
private Node last;

private class Node {
private Item item;
private Node next;
private Node before;
}

public Deque(){    // construct an empty deque
        first=null;
        last=null;
        n=0;
        //assert check();
}

public boolean isEmpty(){    // is the deque empty?
        return first==null;
}

public int size(){    // return the number of items on the deque
        return n;
}

public void addFirst(Item item){    // add the item to the front
        if(item==null) throw new NullPointerException("cant input null");
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.before = null;
        if (oldfirst==null) {
                last = first; //if it's just start
                first.next = null;
        }else oldfirst.before = first;
        n++;
}

public void addLast(Item item){    // add the item to the end
        if(item==null) throw new NullPointerException("cant input null");
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.before = oldlast;
        if (isEmpty()) {
                first = last; //if it's just start
                last.before=null;
        }else oldlast.next = last;
        n++;
}

public Item peekFirst(){    // return the item from the front, return null if the deque is empty.
        if (isEmpty()) return null;
        return first.item;
}

public Item peekLast(){    // return the item from the end, return null if the deque is empty.
        if (isEmpty()) return null;
        return last.item;
}

public Item removeFirst(){    // remove and return the item from the front
        if (isEmpty()) throw new NoSuchElementException("underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = null;   // to avoid loitering
        else first.before = null;
        return item;
}

public Item removeLast(){    // remove and return the item from the end
        if (isEmpty()) throw new NoSuchElementException("underflow");
        Item item = last.item;
        last = last.before;
        n--;
        if (last==null) first = null;   // to avoid loitering
        else last.next=null;
        return item;
}

public Iterator <Item> iterator(){    // return an iterator over items in order from front to end
        return new ListIterator();
}

// an iterator, doesn't implement remove() since it's optional
private class ListIterator implements Iterator<Item> {
private Node current = first;
public boolean hasNext()  {
        return current != null;
}
public void remove()      {
        throw new UnsupportedOperationException();
}
public Item next() {
        if (!hasNext()) throw new NoSuchElementException();
        Item item = current.item;
        current = current.next;
        return item;
}
}


public static void main(String[] args){    // you can test your code here; we won't execute your main function.

}
}


/*
   VThrow a java.lang.NullPointerException if the client attempts to add a null item.
   VThrow a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque.
   VThrow a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator.
   VThrow a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.
 */
