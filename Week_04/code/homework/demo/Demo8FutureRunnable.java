package daiwei.geekbang.homework.demo;

import daiwei.geekbang.homework.common.Fibonacci;
import daiwei.geekbang.homework.common.TaskResult;
import daiwei.geekbang.homework.common.TaskRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * future - submit runnable
 * Created by Daiwei on 2021/2/1
 */
public class Demo8FutureRunnable {

    private static final int CORE = 1;

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        ExecutorService service = Executors.newFixedThreadPool(CORE);

        TaskResult res = new TaskResult();
        Future<TaskResult> future = service.submit(new TaskRunnable(res), res);
        TaskResult taskResult = future.get();

        Integer result = taskResult.getRes();
        service.shutdown();

        System.out.println("异步计算结果为："+ result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }
}
