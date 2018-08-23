package org.langke.think.in.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 对象锁,使用synchronized锁对象，不同对象实例可同时进入锁区域执行
 * @author langke
 *
 */
public class ObjectLock {
    String anyString = new String();

    public ObjectLock(String anyString){
        this.anyString = anyString;
    }

    public void setUsernamePassword(String anyString) {
        synchronized (anyString) {
            try {
                    System.out.println("线程名称为：" + Thread.currentThread().getName()
                            + "在" + System.currentTimeMillis() + "进入同步块");
                    Thread.sleep(3000);
                    System.out.println("线程名称为：" + Thread.currentThread().getName()
                            + "在" + System.currentTimeMillis() + "离开同步块");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ObjectLock ol = new ObjectLock("hello");
        Runnable task = new Runnable() {
            @Override
            public void run() {
             ol.setUsernamePassword("t1");
            }            
        };
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
             ol.setUsernamePassword("t2");
            }            
        };
        ExecutorService es =  Executors.newFixedThreadPool(2);
        es.submit(task);
        es.submit(task1);
    }
    
}
