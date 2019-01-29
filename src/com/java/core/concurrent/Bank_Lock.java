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
public class Bank_Lock {
    /**
     * 使用数组模拟银行账户，索引值为银行账号代码，对应位置上的元素为银行账户余额
     */
    private final double[] accounts;
    /**
     * 锁，用于对转账操作进行同步
     */
    private Lock bankLock;
    /**
     * 锁条件对象——条件变量，用于对转账金额的大小检查（确保在转账后账户余额不会出现负值）以保证余额充足
     */
    private Condition sufficientFunds;

    public Bank_Lock(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
        bankLock = new ReentrantLock();
        sufficientFunds = bankLock.newCondition();
    }

    /**
     * 转账操作
     *
     * @param from   转出账户代码
     * @param to     转入账户代码
     * @param amount 转账金额
     * @throws InterruptedException 线程执行中断时抛出
     */
    public void transfer(int from, int to, double amount) throws InterruptedException {
        bankLock.lock();
        try {
            while (accounts[from] < amount) //不能使用if
                sufficientFunds.await(); //此处使用while循环，并在判断条件符合后，使当前线程阻塞并释放锁；在线程被唤醒之后，会重新进行运行条件判断以确定是否可以进行转账操作。
            System.out.println(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d,", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance:%10.2f%n", getTotalBalance()); //此处使用了锁的可重入属性
            sufficientFunds.signalAll(); //此次转账完成，唤醒其它因为此条件对象而阻塞的所有线程
        } finally {
            bankLock.unlock(); //在此处释放锁，需要放入finally中，即使抛出异常也需要将锁释放，否则其他线程此后永远无法进入此方法
        }
    }

    /**
     * 获取银行所有账户总余额
     * @return 银行所有账户总余额
     */
    public double getTotalBalance() {
        bankLock.lock();
        try {
            return Arrays.stream(accounts).reduce((initValue, x) -> initValue += x).getAsDouble();
        } finally {
            bankLock.unlock();
        }
    }
}
