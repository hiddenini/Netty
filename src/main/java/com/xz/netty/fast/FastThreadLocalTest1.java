package com.xz.netty.fast;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;

public class FastThreadLocalTest1 {

    private static FastThreadLocal<Object> threadLocal0 = new FastThreadLocal<Object>();

    private static FastThreadLocal<Object> threadLocal1 = new FastThreadLocal<Object>();


    public static void main(String[] args) {
        new FastThreadLocalThread(() -> {
            threadLocal0.set(new User(22, "zjw"));
            Object object = threadLocal0.get();
            System.out.println(object);

            threadLocal1.set(new User(23, "xt"));

            Object o = threadLocal1.get();

            System.out.println(o);

        }).start();

        new Thread(() -> {
            Object object = threadLocal0.get();
            System.out.println(object);

            Object o = threadLocal1.get();
            System.out.println(o);


        }).start();
    }
}
