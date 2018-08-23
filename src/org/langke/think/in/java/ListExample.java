package org.langke.think.in.java;
import java.util.*;
public class ListExample {
	public static void main(String s[]){
		List list = new ArrayList();
		list.add("one");
		list.add("2rd");
		list.add(new Integer(3));
		list.add(new Float(4f));
		list.add("five");
		System.out.println(list);
		
		Iterator iterat = list.iterator();
		while(iterat.hasNext()){
			System.out.println(iterat.next());
		}
		ListIterator li = list.listIterator();
		li.add("ListIterator");
		System.out.println(li);
		lruTest(list);
	}
	public static void lruTest(List list){
		  List<String> keyList; 
		  keyList = Collections.synchronizedList(new LinkedList<String>()); 
		  keyList.add("1");
		  keyList.add("2");
		  keyList.add("3");
		  System.out.println(keyList);
		  keyList.remove("2");
		  System.out.println(keyList);
		  keyList.add("2");
		  keyList.remove(0);
		  System.out.println(keyList);
		  
		
	}
}
