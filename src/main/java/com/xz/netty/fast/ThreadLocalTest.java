package com.xz.netty.fast;


public class ThreadLocalTest {
    public static class MyRunnable implements Runnable {
        private ThreadLocal<User> threadLocal = new ThreadLocal<>();

        @Override
        public void run() {
            User yy = new User(7, "yy");
            User zz = new User(77, "zz");
            threadLocal.set(yy);


            User user = threadLocal.get();
            System.out.println(user == yy);

            threadLocal.set(zz);
        }

    }

    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();
        Thread thread1 = new Thread(runnable,"zjw");
        thread1.start();
/*        Thread thread2=new Thread(runnable);

        thread2.start();*/
    }
}
