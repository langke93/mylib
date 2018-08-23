package org.langke.java7;

import java.util.concurrent.Future;

import jsr166y.ForkJoinPool;
import jsr166y.RecursiveTask;

public class Calculator extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 100;
    private int start;
    private int end;

    public Calculator(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if((start - end) < THRESHOLD){
            for(int i = start; i< end;i++){
                sum += i;
            }
        }else{
            int middle = (start + end) /2;
            Calculator left = new Calculator(start, middle);
            Calculator right = new Calculator(middle + 1, end);
            left.fork();
            right.fork();

            sum = left.join() + right.join();
        }
        return sum;
    }

    public static void main(String args[]) throws Exception{
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future<Integer> result = forkJoinPool.submit(new Calculator(0, 10000));
        //new Integer(49995000), 
        System.out.println(result.get());
    }
}