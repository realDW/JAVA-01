package daiwei.geekbang.homework.demo;

import daiwei.geekbang.homework.common.Fibonacci;
import daiwei.geekbang.homework.common.TaskResult;
import daiwei.geekbang.homework.common.TaskRunnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * CompletableFuture 杀鸡用牛刀
 * Created by Daiwei on 2021/2/1
 */
public class Demo9CompletableFuture {

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        CompletableFuture<Integer> calc = CompletableFuture.supplyAsync(Fibonacci::sum);

        System.out.println("异步计算结果为："+ calc.join());

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }
}
