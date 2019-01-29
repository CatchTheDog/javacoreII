package com.java.core.concurrent;

/**
 * 线程中断测试
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/01/29 09:50
 */
public class InterruptTest {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    //阻塞状态的线程在调用interrupt()方法时，会抛出InterruptedException
                    Thread.currentThread().sleep(2000);
                    System.out.println(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();//此处不可抑制异常，可以采用此种方式——设置中断状态以便外部程序可以检测线程中断状态
                System.out.println("thread sleep -> interrupt");
//               Throwable throwable = new Throwable();   也可以采用此种方式，继续向外抛出异常即可。
//               throwable.initCause(e.getCause());
//               throw throwable;
            } finally {
                //执行一些清理工作
            }
        });
        thread.start();
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt(); //在线程休眠时执行中断，会抛出InterruptedException
        if (thread.isInterrupted()) {
            try {
                thread.sleep(1000); //如果线程在中断状态，
            } catch (InterruptedException e) {
                System.out.println("thread interrupt -> sleep");
            }
        }
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stackTraceElements.length; i++) {
            StackTraceElement element = stackTraceElements[i];
            System.out.println(element.getClassName());
            System.out.println(element.getFileName());
            System.out.println(element.getLineNumber());
            System.out.println(element.getMethodName());
        }
    }
}
