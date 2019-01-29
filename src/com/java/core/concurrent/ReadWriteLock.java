package com.java.core.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 测试读写锁
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/01/29 19:04
 */
public class ReadWriteLock {
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private Lock readLock = rwl.readLock();
    private Lock writeLock = rwl.writeLock();
    private Condition readCondition = readLock.newCondition();
    private volatile int count = 0;

    public static void main(String[] args) {

    }

    private void testReadWriteLock() throws InterruptedException {
        this.readLock.lock();
        try {
            if (count == 1000)
                readCondition.await();
            for (; count < 1000; count++) ;
            readCondition.notifyAll();
        } finally {
            this.readLock.unlock();
        }
    }
}
