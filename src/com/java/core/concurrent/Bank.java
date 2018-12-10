package com.java.core.concurrent;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用条件对象实现银行转账同步
 * 条件对象用于线程执行某一动作前需要满足某一前置条件的场景下。
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2018/12/10 16:20
 */
public class Bank {
    private final double[] accounts;
    private Lock bankLock;
    private Condition sufficientFunds;

    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
        bankLock = new ReentrantLock();
        sufficientFunds = bankLock.newCondition();
    }

    public void transfer(int from, int to, double amount) throws InterruptedException {
        bankLock.lock();
        try {
            while (accounts[from] < amount) //不能使用if
                sufficientFunds.await(); //此处使用while循环，并在判断条件符合后，使当前线程阻塞并释放锁；在线程被唤醒之后，会重新进行运行条件判断以确定是否可以进行转账操作。
            System.out.println(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d,", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance:%10.2f%n", getTotalBalance());
            sufficientFunds.signalAll(); //唤醒因为此条件对象而阻塞的所有线程
        } finally {
            bankLock.unlock();
        }
    }

    public double getTotalBalance() {
        bankLock.lock();
        try {
            return Arrays.stream(accounts).reduce((initValue, x) -> initValue += x).getAsDouble();
        } finally {
            bankLock.unlock();
        }
    }
}
