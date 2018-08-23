package org.langke.think.in.java.performance;

public class MinorGCDemo {

	/**
	 * @param args
	 * -Xms40M -Xmx40M -Xmn16M -verbose:gc -XX:+PrintGCDetails
	 * 通过jstat -gcutil [pid] 1000 10 查看Eden，S0,S1,old在minor GC时变化情况
	 */
	public static void main(String[] args) throws Exception{
		MemoryObject object  = new MemoryObject(1024*1024);
		for(int i=0;i<2;i++){
			happenMinorGC(11);
			Thread.sleep(200);
		}
	}

	private static void happenMinorGC(int happenMinorGCIndex) throws Exception{
		for(int i=0;i<happenMinorGCIndex;i++){
			if(i==happenMinorGCIndex-1){
				Thread.sleep(2000);
				System.out.println("minor gc should happen");
			}
			new MemoryObject(1024*1024);
		}
	}
}
