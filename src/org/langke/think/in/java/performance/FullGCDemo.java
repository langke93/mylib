package org.langke.think.in.java.performance;

import java.util.ArrayList;

public class FullGCDemo {

	/**
	 * @param args
	 * -server -verbose:gc -XX:+PrintGCDetails
	 */
	public static void main(String[] args) throws Exception{
		FullGCDemo demo = new FullGCDemo();
		demo.runTest();
	}
	
	private void runTest()throws Exception{
		int count = Runtime.getRuntime().availableProcessors();
		for(int i=0;i<count;i++){
			new Thread(new ConsumeCPUTask()).start();
		}
		for(int i=0;i<200;i++){
			new Thread(new NotConsumeCPUTask()).start();
		}
	}
	class ConsumeCPUTask implements Runnable{
		public void run(){
			String str = "wodmskafjk[asp'km3948u23qimrf sldkfg3]-4it5kfmsd;laserjtmwq4mtfsd"+
			"dlfkjasdflkjsadfljsalkd;jfo;iw3j4mrfkldsjfmio;aweerujemkllllllllvsdajfsaf"+
			"hdlkfajsdkfj02304o23,flkasdjfp243rflkJAWoe423p@!#$%^&*()343434234";
			float i = 0.002f;
			float j=232.13243f;
			while(true){
				j=i*j;
				str.indexOf("#");
				ArrayList<String> list = new ArrayList<String>();
				for(int k=0;i<10000;k++){
					list.add(str+String.valueOf(k));
				}
				list.contains("ii");
				try{
					Thread.sleep(10);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	class NotConsumeCPUTask implements Runnable{
		public void run(){
			try{
				Thread.sleep(10000000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
