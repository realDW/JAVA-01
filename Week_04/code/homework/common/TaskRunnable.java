package daiwei.geekbang.homework.common;

/**
 * Created by Daiwei on 2021/2/1
 */
public class TaskRunnable implements Runnable {

    private TaskResult result;

    public TaskRunnable(TaskResult r) { result = r; }

    @Override
    public void run() {
        result.setRes(Fibonacci.sum());
    }
}
