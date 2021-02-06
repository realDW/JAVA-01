package daiwei.geekbang.homework.common;

import java.util.concurrent.TimeUnit;

/**
 * Created by Daiwei on 2021/2/1
 */
public class Fibonacci {

    public static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
