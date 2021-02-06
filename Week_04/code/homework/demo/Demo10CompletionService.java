package daiwei.geekbang.homework.demo;

import daiwei.geekbang.homework.common.Fibonacci;

import java.util.concurrent.*;

/**
 * CompletionService 杀鸡用屠龙刀
 * Created by Daiwei on 2021/2/1
 */
public class Demo10CompletionService {

    private static final int CORE = 1;

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(CORE);

        CompletionService<Integer> service = new ExecutorCompletionService<>(executor);

        service.submit(Fibonacci::sum);

        Integer result = service.take().get();

        System.out.println("异步计算结果为："+ result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        executor.shutdown();
        // 然后退出main线程
    }
}
