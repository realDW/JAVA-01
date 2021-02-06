package daiwei.geekbang.homework.demo;

import daiwei.geekbang.homework.common.TaskResult;
import daiwei.geekbang.homework.common.TaskThreadWithLock;
import daiwei.geekbang.homework.common.TaskThreadWithNotify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * executorPool + lock
 * Created by Daiwei on 2021/2/1
 */
public class Demo6ThreadPoolLock {

    private static final ReentrantLock lock = new ReentrantLock();

    private static final Condition fibCond = lock.newCondition();

    private static final int core = 1;

    private static final long TIMEOUT = 100L;

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        TaskResult res = new TaskResult();

        ExecutorService service = Executors.newFixedThreadPool(core);
        service.execute(new TaskThreadWithLock(res, fibCond, lock));
        // 使用lock实现管程并且加入超时机制
        try {
            lock.lock();
            while (!res.isDone()) {
                boolean isFinishCalc = fibCond.await(TIMEOUT, TimeUnit.MILLISECONDS);
                if (res.isDone() || !isFinishCalc) {
                    break;
                }
            }
            if (!res.isDone()) {
                throw new RuntimeException("fibonacci calc timeout!");
            }
        } finally {
            lock.unlock();
            service.shutdown();
        }
        Integer result = res.getRes();

        System.out.println("异步计算结果为："+ result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }
}
