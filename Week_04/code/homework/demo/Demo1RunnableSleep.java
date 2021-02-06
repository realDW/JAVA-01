package daiwei.geekbang.homework.demo;

import daiwei.geekbang.homework.common.Fibonacci;
import daiwei.geekbang.homework.common.TaskResult;
import daiwei.geekbang.homework.common.TaskRunnable;

import java.util.concurrent.TimeUnit;

/**
 * runnable + sleep
 * Created by Daiwei on 2021/2/1
 */
public class Demo1RunnableSleep {

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        TaskResult res = new TaskResult();

        new Thread(new TaskRunnable(res)).run();

        // sleep 100ms 确保拿到了数据
        TimeUnit.MILLISECONDS.sleep(100);

        Integer result = res.getRes();

        System.out.println("异步计算结果为："+ result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }







}
