package daiwei.geekbang.homework.common;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 简单封装一个包含结果的Thread对象
 * Created by Daiwei on 2021/2/1
 */
public class TaskThreadWithLock extends Thread{

    private TaskResult taskResult;

    private final Condition fibCond;

    private final Lock lock;

    public TaskThreadWithLock(TaskResult result, Condition fibCond, Lock lock) {
        this.taskResult = result;
        this.fibCond = fibCond;
        this.lock = lock;
    }

    @Override
    public void run() {
        taskResult.setRes(Fibonacci.sum());
        try {
            lock.lock();
            fibCond.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
