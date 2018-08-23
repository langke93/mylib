package org.langke.think.in.java.performance;

import java.util.ArrayList;
import java.util.List;

public class CMSGCTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * -Xms20m -Xmx20m -Xmn10m -verbose:gc -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC
	 * CMS GC时出现promotion failed和concurrent mode failure应对措施为：增大survivor space、旧生代空间
	 */
	public static void main(String[] args) throws InterruptedException {
		List<MemoryObject> objects = new ArrayList<MemoryObject>(6);
		for(int i=0;i<9;i++){
			objects.add(new MemoryObject(1024*1024));
		}
		Thread.sleep(2000);
		objects.remove(0);
		objects.remove(0);
		objects.remove(0);
		for(int i=0;i<20;i++){
			objects.add(new MemoryObject(1024*1024));
			if(i%2==0){
				objects.remove(0);
			}
		}
		Thread.sleep(5000);
	}


}
