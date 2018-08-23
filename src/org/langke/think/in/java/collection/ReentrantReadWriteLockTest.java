package org.langke.think.in.java.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class ReentrantReadWriteLockTest {

	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	//private static ReentrantLock lock = new ReentrantLock();
	private static WriteLock writeLock = lock.writeLock();
	private static ReadLock readLock = lock.readLock();
	private static Map<String,String> maps = new HashMap<String,String>();
	private static CountDownLatch latch = new CountDownLatch(102);
	private static CyclicBarrier barrier = new CyclicBarrier(102);
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		long beginTime = System.currentTimeMillis();
		for(int i=0;i<100;i++){
			new Thread(new ReadThread()).start();
		}
		for(int i=0;i<2;i++){
			new Thread(new WriteThread()).start();
		}
		latch.await();
		long endTime = System.currentTimeMillis();
		System.out.println("Consume Time is:"+(endTime-beginTime)+" ms");		
	}
	static class WriteThread implements Runnable{
		public void run(){
			try{
				barrier.await();
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				writeLock.lock();
				//lock.lock();
				maps.put("1","2");
				Thread.sleep(100);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				writeLock.unlock();
				//lock.unlock();
			}
			latch.countDown();
		}
	}
	static class ReadThread implements Runnable{
		public void run(){
			try{
				barrier.await();
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				readLock.lock();
				//lock.lock();
				maps.get("1");
				Thread.sleep(100);				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				readLock.unlock();
				//lock.unlock();
			}
			latch.countDown();
		}
	}
}
