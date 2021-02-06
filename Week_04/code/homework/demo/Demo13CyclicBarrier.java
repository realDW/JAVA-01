package daiwei.geekbang.homework.demo;

import daiwei.geekbang.homework.common.Fibonacci;
import daiwei.geekbang.homework.common.TaskResult;

import java.util.concurrent.*;

/**
 * CyclicBarrier
 * Created by Daiwei on 2021/2/1
 */
public class Demo13CyclicBarrier {

    private static final int CORE = 1;

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(CORE);

        TaskResult taskResult = new TaskResult();

        CyclicBarrier barrier = new CyclicBarrier(1, () -> {

            Integer result = taskResult.getRes();

            System.out.println("异步计算结果为："+ result);

            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        });

        executor.execute(() -> {
            taskResult.setRes(Fibonacci.sum());
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        executor.shutdown();
        // 然后退出main线程
    }
}
