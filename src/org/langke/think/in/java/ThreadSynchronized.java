package org.langke.think.in.java;

public class ThreadSynchronized {
	abstract class Abs{
		 void test(){};
	}
	public int doSomething(final int x){
		return x;
	}
	
	public synchronized void m1(){
		try {
			System.out.println("m1");
			//this.wait(100*1000);
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void m2(){
		try {
			System.out.println("m2");
			//this.wait(100*1000);
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void m0(){
		try {
			System.out.println("m0");
			//this.wait(100*1000);
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		final ThreadSynchronized ts = new ThreadSynchronized();
		final ThreadSynchronized ts1 = new ThreadSynchronized();
		new Thread("t1"){
			@Override
			public void run() {
				super.run();
				ts.m1();
			}
		}.start();
		System.out.println("t1");
		new Thread("t0"){
			@Override
			public void run() {
				ts.m0();
				super.run();
			}
		}.start();
		System.out.println("t0");
		new Thread("t2"){
			@Override
			public void run() {
				ts1.m2();
				super.run();
			}
		}.start();
		System.out.println("t2");
	}
}
