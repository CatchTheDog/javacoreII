package com.java.core.concurrent;

/**
 * 设计线程安全类的过程应该包含下面三个基本要素：
 * 确定对象状态时由哪些变量构成的
 * 确定限制状态变量的不变约束
 * 限制一个管理并发访问对象状态的策略
 * 对象的不变约束：
 * 后验条件：
 * 状态依赖：
 * 使用原子性和封装性来保证对象的不变约束和后延条件。
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/23 15:15
 */
public class Counter1 {
    private long value = 0;

    public static void main(String[] args) {
        Counter1 counter1 = new Counter1();
        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(() -> {
//                System.out.print(Thread.currentThread().getName() + " getValue: " + counter1.getValue() + " ");
                System.out.println(Thread.currentThread().getName() + " increment: " + counter1.increment());
            });
            thread.setName("Thread-" + i);
            thread.start();
        }
    }

    public synchronized long getValue() {
        return this.value;
    }

    public long increment() {
        if (value == Long.MAX_VALUE)
            throw new IllegalArgumentException("count overflow .");
        return ++value;
    }
}
