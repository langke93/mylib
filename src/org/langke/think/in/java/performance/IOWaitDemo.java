package org.langke.think.in.java.performance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

public class IOWaitDemo {

	private String fileName = "/data/iowait.log";
	private static int threadCount = Runtime.getRuntime().availableProcessors();
	private Random random = new Random();
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		if(args.length==1)
			threadCount = Integer.parseInt(args[1]);
		IOWaitDemo  demo = new IOWaitDemo();
		demo.runTest();

	}
	private void runTest()throws Exception{
		File file = new File(fileName);
		file.createNewFile();
		for(int i=0;i<threadCount;i++){
			new Thread(new Task()).start();
		}
	}
	class Task implements Runnable{
		public void run(){
			while(true){
				try{
					BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true));
					StringBuilder strBuilder = new StringBuilder("=====begin======\n");
					String threadName=Thread.currentThread().getName();
					for(int i=0;i<100000;i++){
						strBuilder.append(threadName);
						strBuilder.append("\n");
					}
					strBuilder.append("====end=====\n");
					writer.write(strBuilder.toString());
					writer.close();
					Thread.sleep(random.nextInt(10));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}
