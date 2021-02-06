package daiwei.geekbang.homework.demo;

import daiwei.geekbang.homework.common.Fibonacci;
import daiwei.geekbang.homework.common.TaskResult;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * blockingQueue
 * Created by Daiwei on 2021/2/1
 */
public class Demo15BlockingQueue {

    private static final int CORE = 1;

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(CORE);

        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        executor.execute(() -> queue.offer(Fibonacci.sum()));

        Integer result = queue.take();

        System.out.println("异步计算结果为："+ result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        executor.shutdown();
        // 然后退出main线程
    }
}
