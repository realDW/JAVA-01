package daiwei.geekbang.homework.demo;

import daiwei.geekbang.homework.common.Fibonacci;
import daiwei.geekbang.homework.common.TaskResult;

import java.util.concurrent.*;

/**
 * Phaser
 * Created by Daiwei on 2021/2/1
 */
public class Demo14Phaser {

    private static final int CORE = 2;

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(CORE);

        TaskResult taskResult = new TaskResult();

        Phaser phaser = new Phaser(0) {

            @Override
            protected boolean onAdvance(int phase, int registeredParties) {

                Integer result = taskResult.getRes();

                System.out.println("异步计算结果为："+ result);

                System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

                return true;
            }
        };

        executor.execute(() -> {
            phaser.register();
            taskResult.setRes(Fibonacci.sum());
            phaser.arriveAndAwaitAdvance();
        });

        executor.shutdown();
        // 然后退出main线程
    }
}
