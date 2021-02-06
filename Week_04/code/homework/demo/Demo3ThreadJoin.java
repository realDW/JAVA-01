package daiwei.geekbang.homework.demo;

import daiwei.geekbang.homework.common.TaskResult;
import daiwei.geekbang.homework.common.TaskThread;

/**
 * thread + join
 * Created by Daiwei on 2021/2/1
 */
public class Demo3ThreadJoin {

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        TaskResult res = new TaskResult();

        TaskThread taskThread = new TaskThread(res);

        taskThread.start();

        // 使用 join 阻塞当前线程
        taskThread.join();

        Integer result = res.getRes();

        System.out.println("异步计算结果为："+ result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }
}
