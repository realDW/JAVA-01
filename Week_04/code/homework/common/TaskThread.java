package daiwei.geekbang.homework.common;

/**
 * 简单封装一个包含结果的Thread对象
 * Created by Daiwei on 2021/2/1
 */
public class TaskThread extends Thread{

    private TaskResult taskResult;

    public TaskThread(TaskResult result) {
        this.taskResult = result;
    }

    @Override
    public void run() {
        taskResult.setRes(Fibonacci.sum());
    }
}
