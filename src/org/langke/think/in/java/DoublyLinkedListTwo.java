package org.langke.think.in.java;

import java.util.Iterator;
import java.util.LinkedList;
/**
 *使用原生的LinkedList类完成上边同样的操作和结果
 **/
public class DoublyLinkedListTwo {
    public static void main(String args[]){
        LinkedList<Integer> theList = new LinkedList<Integer>();
        theList.addFirst(22);
        theList.offerFirst(44);
        theList.addLast(33);
        theList.offerLast(55);
        System.out.println(theList);

        // LinkedList的逆序操作
        LinkedList<Integer> resultList = new LinkedList<Integer>();
        Iterator<Integer> iterator= theList.descendingIterator();
        while(iterator.hasNext()){
            resultList.add(iterator.next());
        }
        System.out.println(resultList);

        theList.removeFirst();
        theList.removeLast();
        //theList.remove(11);// 该注释行需要注意
        System.out.println(theList);

        theList.add(1,77);
        theList.add(3,88);
        theList.offer(99);
        System.out.println(theList);
    }
}
