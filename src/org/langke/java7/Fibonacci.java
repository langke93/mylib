package org.langke.java7;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import jsr166y.ForkJoinPool;
import jsr166y.ForkJoinTask;
import jsr166y.RecursiveAction;
import jsr166y.RecursiveTask;

class Fibonacci extends RecursiveTask<Integer> {
    final int n;

    Fibonacci(int n) {
        this.n = n;
    }

    private int compute(int small) {
        final int[] results = { 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89 };
        return results[small];
    }

    public Integer compute() {
        if (n <= 10) {
            return compute(n);
        }
        Fibonacci f1 = new Fibonacci(n - 1);
        Fibonacci f2 = new Fibonacci(n - 2);
        System.out.println("fork new thread for " + (n - 1));
        f1.fork();
        System.out.println("fork new thread for " + (n - 2));
        f2.fork();
        return f1.join() + f2.join();
    }
}

class ConcurrentPrint extends RecursiveAction {
    protected void compute() {
        TaskBarrier b = new TaskBarrier() {
            protected boolean terminate(int cycle, int registeredParties) {
                System.out.println("Cycle is " + cycle + ";"
                        + registeredParties + " parties");
                return cycle >= 10;
            }
        };
        int n = 3;
        CyclicAction[] actions = new CyclicAction[n];
        for (int i = 0; i < n; ++i) {
            final int index = i;
            actions[i] = new CyclicAction(b) {
                protected void compute() {
                    System.out.println("I'm working " + getCycle() + " "
                            + index);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        }
        for (int i = 0; i < n; ++i)
            actions[i].fork();
        for (int i = 0; i < n; ++i)
            actions[i].join();
    }
    public void testBarrier () throws InterruptedException, ExecutionException {
		System.out.println("\ntesting Task Barrier ...");
        ForkJoinTask fjt = new ConcurrentPrint();
        ForkJoinPool fjpool = new ForkJoinPool(4);
        fjpool.submit(fjt);
        fjpool.shutdown();
    }

    public void testFibonacci () throws InterruptedException, ExecutionException {
    	System.out.println("\ntesting Fibonacci ...");
		final int num = 14; //For demo only
        ForkJoinTask<Integer> fjt = new Fibonacci(num);
        ForkJoinPool fjpool = new ForkJoinPool();
        Future<Integer> result = fjpool.submit(fjt);

        // do something
        System.out.println("Fibonacci(" + num + ") = " + result.get());
    }

}
