package com.java.core.concurrent;

/**
 * 若没有同步来确保Holder对其他线程可见，所以Holder是非正确发布的。
 * 没有正确发布的对象会导致两种错误：首先，发布线程以外的任何线程都可以看到Holder域的过期值，因而看到的是一个null引用或者旧值。
 * 其次，线程看到的Holder引用是最新的，然而Hodler状态却是过期的。
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/22 16:19
 */
public class Holder {
    private int n;

    public Holder(int n) {
        this.n = n;
    }

    public static void main(String[] args) {
        Holder holder = new Holder(10);
        for (int i = 0; i < 100; i++)
            new Thread(() -> {
                holder.increment();
                holder.assertSanity();
            }).start();
    }

    public void assertSanity() {
        if (n != n) {
            throw new AssertionError("This statement is false.");
        }
    }

    public void increment() {
        this.n++;
    }
}
