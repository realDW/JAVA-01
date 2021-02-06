package daiwei.geekbang.homework.demo;

import daiwei.geekbang.homework.common.Fibonacci;
import daiwei.geekbang.homework.common.TaskResult;
import daiwei.geekbang.homework.common.TaskRunnable;
import daiwei.geekbang.homework.common.TaskThreadWithNotify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * future - submit callable
 * Created by Daiwei on 2021/2/1
 */
public class Demo7FutureCallable {

    private static final int core = 1;

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        ExecutorService service = Executors.newFixedThreadPool(core);
        Future<Integer> future = service.submit(Fibonacci::sum);
        Integer result = future.get();
        service.shutdown();

        System.out.println("异步计算结果为："+ result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }
}
