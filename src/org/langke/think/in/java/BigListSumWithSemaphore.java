package org.langke.think.in.java;
 

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * 有一个很大的整数list，需要求这个list中所有整数的和，写一个可以充分利用多核CPU的代码，来计算结果。
 * 
 * 
 * 乍一看到题目“充分利用多核CPU”，
 * 以为会根据CPU的核心数，计算出比较合理的任务线程的数目。
 * 就题目的计算目标来说，实际上讲的主线程等待任务线程完成，
 * 任务线程之间没有必要互相等待。
 * 是不是可以考虑用信号来......
 * 每次来看帖，都发现大家图画的很拉风……
 * 
 * 我也帖个代码，虽然来晚了，大家不一定能看到……
 * 
 * @author HardPass
 *
 */
public class BigListSumWithSemaphore {
	private List<Integer> list = new ArrayList<Integer>();

	private AtomicLong sum = new AtomicLong(0L); // 结果
	private int threadCounts = 2 * Runtime.getRuntime().availableProcessors() + 1; // 任务线程数
	private List<Runnable> tasks = new ArrayList<Runnable>(); // 任务线程
	
	SemaphoreLock lock = new SemaphoreLock();
	


	public static void main(String[] args) {
		new BigListSumWithSemaphore().test();
	}

	public void test() {
		init();
		ExecutorService exec = Executors.newFixedThreadPool(threadCounts); //线程池
		for(Runnable task : tasks){
			exec.execute(task);
		}
		lock.lockHere(); // 此处尝试wait
		exec.shutdown();
		System.out.println("List中所有整数的和为:" + sum);
	}

	private void init() {
		for (int i = 1; i <= 1000000; i++) {
			list.add(i);
		}
		int len = list.size() / threadCounts;

		int i = 0;
		for (; i < threadCounts - 1; i++) {
			tasks.add(new Task(list.subList(i * len, (i + 1) * len)));
		}
		tasks.add(new Task(list.subList(i * len, list.size())));
	}

	private class Task implements Runnable {
		private List<Integer> subList;

		public Task(List<Integer> subList) {
			this.subList = subList;
		}

		@Override
		public void run() {
			lock.lockThere();  // 增加锁的计数
			long subSum = 0L;
			for (Integer i : subList) {
				subSum += i;
			}
			sum.addAndGet(subSum);
			System.out.println("分配给线程：" + Thread.currentThread().getName()
					+ "那一部分List的整数和为：\tSubSum:" + subSum);
			lock.release(); // 释放一个锁的计数
		}
	}

}

/**
 * 
 * 信号量锁
 * 
 * 此Semaphore非java.util.concurrent.Semaphore
 * 
 * @author HardPass
 *
 */
class SemaphoreLock {
	private int count = 0; // 信号量
	/**
	 * 信号量大于0的时候 wait
	 * 这是不是传说中的可重入？
	 */
	public synchronized void lockHere() {
		while (count > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}

	public synchronized void lockThere() {
		count++;
	}

	public synchronized void release() {
		count--;
		if(count==0){
			notify();
		}
	}
}