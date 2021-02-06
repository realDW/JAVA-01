package daiwei.geekbang.homework.demo;

import daiwei.geekbang.homework.common.TaskResult;
import daiwei.geekbang.homework.common.TaskThread;
import daiwei.geekbang.homework.common.TaskThreadWithNotify;

/**
 * thread + synchronized + wait & notify
 * Created by Daiwei on 2021/2/1
 */
public class Demo4ThreadSynchronized {

    private static final Object locker = new Object();

    private static final long ONE_SEC_MILLI = 1000;

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        TaskResult res = new TaskResult();

        TaskThreadWithNotify taskThread = new TaskThreadWithNotify(res, locker);

        taskThread.start();

        //同步块 + wait & notify
        synchronized (locker) {
            while (!res.isDone()) {
                locker.wait(ONE_SEC_MILLI);
            }
        }

        Integer result = res.getRes();

        System.out.println("异步计算结果为："+ result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }
}
