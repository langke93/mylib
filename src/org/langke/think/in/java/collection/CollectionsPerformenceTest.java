package org.langke.think.in.java.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

/**
 * 集合类性能测试
 * @author admini
 *
 */
public class CollectionsPerformenceTest {
	ArrayList arrayList = new ArrayList();
	LinkedList linkedList = new LinkedList();
	Vector vector = new Vector();
	Stack stack = new Stack();
	HashSet hashSet = new HashSet();
	TreeSet treeSet = new TreeSet();
	HashMap hashMap = new HashMap();
	TreeMap treeMap = new TreeMap();
	enum operate{INIT ,ADD,QUERY,DEL};
	long performenceTest(int total,Object obj,operate op){
		long time = getTime();
		if(obj instanceof ArrayList){
			switch (op) {
			case INIT:
				arrayList = new ArrayList(total);
			case ADD:
				for(int i=0;i<total;i++){
					arrayList.add(i);
				}
				break;
			case QUERY:
				for(int i=0;i<total;i++){
					arrayList.get(i);
				}
				break;
			case DEL:
				for(int i=0;i<total;i++){
					arrayList.remove(i);
				}
				break;
			}
		}else if(obj instanceof LinkedList){
			switch (op) {
			case INIT:
				linkedList = new LinkedList();
			case ADD:
				for(int i=0;i<total;i++){
					linkedList.add(i);
				}
				break;
			case QUERY:
				for(int i=0;i<total;i++){
					linkedList.get(i);
				}
				break;
			case DEL:
				for(int i=0;i<total;i++){
					linkedList.remove(i);
				}
				break;
			}
		}else if(obj instanceof Vector){
			switch (op) {
			case INIT:
				vector = new Vector(total);
			case ADD:
				for(int i=0;i<total;i++){
					vector.add(i);
				}
				break;
			case QUERY:
				for(int i=0;i<total;i++){
					vector.get(i);
				}
				break;
			case DEL:
				for(int i=0;i<total;i++){
					vector.remove(i);
				}
				break;
			}
		}else if(obj instanceof Stack){
			switch (op) {
			case INIT:
				stack = new Stack();
			case ADD:
				for(int i=0;i<total;i++){
					stack.add(i);
				}
				break;
			case QUERY:
				for(int i=0;i<total;i++){
					stack.get(i);
				}
				break;
			case DEL:
				for(int i=0;i<total;i++){
					stack.remove(i);
				}
				break;
			}
		}else if(obj instanceof HashSet){
			switch (op) {
			case INIT:
				hashSet = new HashSet(total);
			case ADD:
				for(int i=0;i<total;i++){
					hashSet.add(i);
				}
				break;
			case QUERY:
				for(int i=0;i<total;i++){
					hashSet.contains(i);
				}
				break;
			case DEL:
				for(int i=0;i<total;i++){
					hashSet.remove(i);
				}
				break;
			}
		}else if(obj instanceof TreeSet){
			switch (op) {
			case INIT:
				treeSet = new TreeSet();
			case ADD:
				for(int i=0;i<total;i++){
					treeSet.add(i);
				}
				break;
			case QUERY:
				for(int i=0;i<total;i++){
					treeSet.contains(i);
				}
				break;
			case DEL:
				for(int i=0;i<total;i++){
					treeSet.remove(i);
				}
				break;
			}
		}else if(obj instanceof HashMap){
			switch (op) {
			case INIT:
				hashMap = new HashMap();
			case ADD:
				for(int i=0;i<total;i++){
					hashMap.put(i, i);
				}
				break;
			case QUERY:
				for(int i=0;i<total;i++){
					hashMap.get(i);
				}
				break;
			case DEL:
				for(int i=0;i<total;i++){
					hashMap.remove(i);
				}
				break;
			}
		}else if(obj instanceof TreeMap){
			switch (op) {
			case INIT:
				treeMap = new TreeMap();
			case ADD:
				for(int i=0;i<total;i++){
					treeMap.put(i, i);
				}
				break;
			case QUERY:
				for(int i=0;i<total;i++){
					treeMap.get(i);
				}
				break;
			case DEL:
				for(int i=0;i<total;i++){
					treeMap.remove(i);
				}
				break;
			}
		}
		return getTime()-time;
	}
	long getTime(){
		return System.nanoTime();
	}
	String timeUnit(long time){
		String res;
		if(time > 1000000)
			res = (time/1000000)+" ms";
		else
			res = time+"ns";
		return res;
	}
	void singleTest(Object obj){
		int total = 1000000;//初始化元素个数
		int op_count = 100;//操作次数
		long time;//执行时间
		System.out.print(obj.getClass().getSimpleName()+"        ");
		time = performenceTest(total, obj, operate.INIT);
		System.out.print(timeUnit(time)+"        ");
		time = performenceTest(op_count, obj, operate.ADD);
		System.out.print(timeUnit(time)+"        ");
		time = performenceTest(op_count, obj, operate.QUERY);
		System.out.print(timeUnit(time)+"        ");
		time = performenceTest(op_count, obj, operate.DEL);
		System.out.print(timeUnit(time)+"        "+"\n");		
	}
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(10000);
		System.out.println(9l<<30l);
		System.out.println("集合类型         初始化时间        增加时间       查找时间         删除时间");
		CollectionsPerformenceTest collectionsPerformenceTest = new CollectionsPerformenceTest();
		collectionsPerformenceTest.singleTest(collectionsPerformenceTest.arrayList);
		collectionsPerformenceTest.singleTest(collectionsPerformenceTest.linkedList);
		collectionsPerformenceTest.singleTest(collectionsPerformenceTest.vector);
		collectionsPerformenceTest.singleTest(collectionsPerformenceTest.stack);
		collectionsPerformenceTest.singleTest(collectionsPerformenceTest.hashSet);
		collectionsPerformenceTest.singleTest(collectionsPerformenceTest.treeSet);
		collectionsPerformenceTest.singleTest(collectionsPerformenceTest.hashMap);
		collectionsPerformenceTest.singleTest(collectionsPerformenceTest.treeMap);
	}

}
