package daiwei.geekbang.homework.common;

/**
 * 简单封装一个包含结果的Thread对象
 * Created by Daiwei on 2021/2/1
 */
public class TaskThreadWithNotify extends Thread{

    private TaskResult taskResult;

    private final Object locker;

    public TaskThreadWithNotify(TaskResult result, Object locker) {
        this.taskResult = result;
        this.locker = locker;
    }

    @Override
    public void run() {
        taskResult.setRes(Fibonacci.sum());
        synchronized (locker) {
            locker.notifyAll();
        }
    }
}
