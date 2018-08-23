package org.langke.think.in.java.collection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorTest {
	ThreadPoolExecutorTest(BlockingQueue<Runnable> queue){
		 executor = new ThreadPoolExecutor(
					10,
					600,
					30,
					TimeUnit.SECONDS,
					queue,
					Executors.defaultThreadFactory()
					,new ThreadPoolExecutor.AbortPolicy());
	}
	ThreadPoolExecutorTest(ExecutorService  es){
		executor = (ThreadPoolExecutor) es;
	}
	static final BlockingQueue<Runnable> syncQueue = new SynchronousQueue<Runnable>();
	static final ArrayBlockingQueue<Runnable> arrayQueue = new ArrayBlockingQueue<Runnable>(10);
	static final LinkedBlockingQueue<Runnable> linkedQueue = new LinkedBlockingQueue<Runnable>(10);
	final ThreadPoolExecutor executor ;
	final AtomicInteger completedTask = new AtomicInteger(0);
	final AtomicInteger rejectedTask = new AtomicInteger(0);
	static long beginTime;
	final int count =1000;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		beginTime = System.currentTimeMillis();
		ThreadPoolExecutorTest demo;
//		demo = new ThreadPoolExecutorTest(syncQueue);
//		demo.start(); 
		demo = new ThreadPoolExecutorTest(arrayQueue);
		demo.start();
//		demo = new ThreadPoolExecutorTest(linkedQueue);
//		demo.start(); 
//		demo = new ThreadPoolExecutorTest(Executors.newFixedThreadPool(500));
//		demo.start();
	}
	public void start(){
		CountDownLatch latch = new CountDownLatch(count);
		CyclicBarrier barrier = new CyclicBarrier(count);
		for(int i=0;i<count;i++){
			new Thread(new TestThread(latch,barrier)).start();
		}
		try{
			latch.await();
			executor.shutdownNow();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	class TestThread implements Runnable{
		private CountDownLatch latch;
		private CyclicBarrier barrier;
		public TestThread(CountDownLatch latch,CyclicBarrier barrier){
			this.latch = latch;
			this.barrier = barrier;
		}
		public void run(){
			try{
				barrier.await();
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				executor.execute(new Task(latch));
			}catch(RejectedExecutionException exception){
				latch.countDown();
				System.err.println("被拒绝的任务数为："+rejectedTask.incrementAndGet());
			}
		}
	}
	class Task implements Runnable{
		private CountDownLatch latch;
		public Task(CountDownLatch latch){
			this.latch = latch;
		}
		public void run(){
			try{
				Thread.sleep(3000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			System.out.println("执行的任务数为："+completedTask.incrementAndGet());
			System.out.println("任务耗时为："+(System.currentTimeMillis()-beginTime)+"ms");
			latch.countDown();
		}
	}
}
