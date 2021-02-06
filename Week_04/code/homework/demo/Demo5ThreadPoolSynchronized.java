package daiwei.geekbang.homework.demo;

import daiwei.geekbang.homework.common.TaskResult;
import daiwei.geekbang.homework.common.TaskThreadWithNotify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * executorPool + synchronized
 * Created by Daiwei on 2021/2/1
 */
public class Demo5ThreadPoolSynchronized {

    private static final Object locker = new Object();

    private static final int core = 1;

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        TaskResult res = new TaskResult();

        ExecutorService service = Executors.newFixedThreadPool(core);
        service.submit(new TaskThreadWithNotify(res, locker));
        synchronized (locker) {
            while (!res.isDone()) {
                locker.wait();
            }
        }


        Integer result = res.getRes();

        service.shutdown();
        System.out.println("异步计算结果为："+ result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }
}
