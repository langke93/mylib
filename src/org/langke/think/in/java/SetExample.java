
package org.langke.think.in.java;
import java.util.*;
/**
 * HashSet以哈希表形式存放,是无序的,操作速度快
 * @author lizz
 *
 */
public class SetExample {
	public static void main(String ar[]){
		Set set = new HashSet();
		set.add("one");
		set.add("3rd");
		set.add(new Integer(4));
		set.add(new Float(5f));
		set.add("one");//这里重复不会添加
		System.out.println(set);
		
		//test
		int num=3;
		int i;
		char aa=0;
		   for (i=1;i<=num;i++)
		   {
			   aa=(char) ('A'+i-1);

				System.out.println(i+":"+aa);
		   }
		   
	}
}
