package org.langke.java8;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * 设定ForkJoin线程池大小
 * 
 * @author admini
 *
 */
public class ForkJoinPoolLimit {

    public static void main(String args[]) throws InterruptedException {
        // System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism",
        // "2");
        final ForkJoinPool pool = new ForkJoinPool(2);
        // List<Integer> cost = Lists.newArrayList(1, 3, 7, 9, 34);
        // cost..submit(() -> cost.parallelStream().map(x -> x += 1).reduce((y,
        // z) -> y + z).get());
        System.out.println(pool.commonPool());
        // IntStream.range(0,Runtime.getRuntime().availableProcessors())
        // .parallel()
        pool.submit(() -> IntStream
                .range(0, Runtime.getRuntime().availableProcessors())
                .parallel().forEach(p -> {
                    try {
                        System.out.println(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }));

        Thread.currentThread().join();
    }
}
