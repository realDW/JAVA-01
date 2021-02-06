package daiwei.geekbang.homework.demo;

import daiwei.geekbang.homework.common.Fibonacci;

import java.util.concurrent.*;

/**
 * 花里胡哨的 forkJoin
 * Created by Daiwei on 2021/2/1
 */
public class Demo11ForkJoin {

    private static final int CORE = 1;

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        Fibonacci fibonacci = new Fibonacci(36);

        Integer result = fibonacci.invoke();

        System.out.println("异步计算结果为："+ result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }

    static class Fibonacci extends RecursiveTask<Integer> {

        final int n;

        public Fibonacci(Integer n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n < 2) return 1;
            Fibonacci f1 = new Fibonacci(n - 1);
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            return f2.compute() + f1.join();
        }
    }
}
