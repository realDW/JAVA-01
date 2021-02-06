package daiwei.geekbang.homework.demo;

import daiwei.geekbang.homework.common.Fibonacci;
import daiwei.geekbang.homework.common.TaskResult;
import daiwei.geekbang.homework.common.TaskRunnable;

import java.util.concurrent.*;

/**
 * contDownLatch
 * Created by Daiwei on 2021/2/1
 */
public class Demo12CountDownLatch {

    private static final int CORE = 1;

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(CORE);

        CountDownLatch cdl = new CountDownLatch(1);

        TaskResult taskResult = new TaskResult();

        executor.execute(() -> {
            taskResult.setRes(Fibonacci.sum());
            cdl.countDown();
        });

        cdl.await();

        Integer result = taskResult.getRes();

        System.out.println("异步计算结果为："+ result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        executor.shutdown();
        // 然后退出main线程
    }
}
