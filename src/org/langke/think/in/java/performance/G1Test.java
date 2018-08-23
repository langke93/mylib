package org.langke.think.in.java.performance;

import java.util.ArrayList;
import java.util.List;

public class G1Test {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * Sun JDK 1.6.0 Update 18上支持G1
	 * -Xms40m -Xmx40m -Xmn20m -verbose:gc -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:MaxGCPauseMillis=5 -XX:GCPauseIntervalMillis=1000 -XX:+PrintGCDetails
	 */
	public static void main(String[] args) throws InterruptedException {
		List<MemoryObject> objects = new ArrayList<MemoryObject>();
		
		for(int i=0;i<20;i++){
			objects.add(new MemoryObject(1024*1024));
			if(i%3==0){
				objects.remove(0);
			}
		}
		Thread.sleep(2000);
	}

}
