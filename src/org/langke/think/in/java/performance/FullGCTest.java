package org.langke.think.in.java.performance;

import java.util.ArrayList;
import java.util.List;

public class FullGCTest {

	/**
	 * @param args
	 * -server -Xms20m -Xmx20m -Xmn10m -verbose:gc -XX:+PrintGCDetails
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		List<MemoryObject> objects = new ArrayList<MemoryObject>(6);
		for(int i=0;i<10;i++){
			objects.add(new MemoryObject(1024*1024));
		}
		//让上面对象尽可能转入旧生代
		System.gc();
		System.gc();
		Thread.sleep(2000);
		objects.clear();
		for(int i=0;i<10;i++){
			objects.add(new MemoryObject(1024*1024));
			if(i%3==0){
				objects.remove(0);
			}
		}
		Thread.sleep(5000);
	}
}
class MemoryObject{
	private byte[] bytes;
	public MemoryObject(int objectSize){
		this.bytes = new byte[objectSize];
	}
}
