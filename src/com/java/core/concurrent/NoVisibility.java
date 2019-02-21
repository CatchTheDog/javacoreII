package com.java.core.concurrent;

/**
 * 多线程程序的共享变量可见性测试
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/21 19:23
 */
public class NoVisibility {
    private static boolean ready;
    private static int number;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new ReaderThread()).start();
        Thread.sleep(1000);
        number = 34;
        ready = true;
    }

    private static class ReaderThread implements Runnable {
        @Override
        public void run() {
            while (!ready) Thread.yield();
            System.out.println(number);
        }
    }
}
