package com.java.core.concurrent;

import java.util.Arrays;

/**
 * 使用内部锁实现 银行账户转账
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/01/29 15:43
 */
public class Bank_Synchronized {
    private double[] accounts;

    public Bank_Synchronized(int n, double initialBanlance) {
        this.accounts = new double[n];
        Arrays.fill(this.accounts, initialBanlance);
    }

    /**
     * 转账操作
     *
     * @param from   转出账户代码
     * @param to     转入账户代码
     * @param amount 转账金额
     * @throws InterruptedException 线程执行中断时抛出
     */
    public synchronized void transfer(int from, int to, double amount) throws InterruptedException {
        while (accounts[from] < amount) //不能使用if
            wait(); //此处使用while循环，并在判断条件符合后，使当前线程阻塞并释放锁；在线程被唤醒之后，会重新进行运行条件判断以确定是否可以进行转账操作。
        System.out.println(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d,", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Total Balance:%10.2f%n", getTotalBalance()); //此处使用了锁的可重入属性
        notifyAll(); //此次转账完成，唤醒其它因为此条件对象而阻塞的所有线程
    }

    /**
     * 获取银行所有账户总余额
     *
     * @return 银行所有账户总余额
     */
    public synchronized double getTotalBalance() {
        return Arrays.stream(accounts).reduce((initValue, x) -> initValue += x).getAsDouble();
    }
}
