package daiwei.geekbang.homework.demo;

import daiwei.geekbang.homework.common.Fibonacci;
import daiwei.geekbang.homework.common.TaskResult;
import daiwei.geekbang.homework.common.TaskThread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * lockSupport
 * Created by Daiwei on 2021/2/1
 */
public class Demo16LockSupport {

    private static final int CORE = 1;

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(CORE);

        TaskResult taskResult = new TaskResult();
        Thread main = Thread.currentThread();

        executor.execute(() -> {
            taskResult.setRes(Fibonacci.sum());
            LockSupport.unpark(main);
        });

        while (!taskResult.isDone()) {
            LockSupport.park();
        }

        Integer result = taskResult.getRes();

        System.out.println("异步计算结果为："+ result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        executor.shutdown();
        // 然后退出main线程
    }
}
