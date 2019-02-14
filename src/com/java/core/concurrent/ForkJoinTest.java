package com.java.core.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.DoublePredicate;

/**
 * Fork-Join 框架测试
 * RecursiveTask 实现递归任务处理
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/14 09:15
 */
public class ForkJoinTest {
    public static void main(String[] args) {
        final int SIZE = 10000000;
        double[] numbers = new double[SIZE];
        for (int i = 0; i < SIZE; i++) numbers[i] = Math.random();
        Counter counter = new Counter(numbers, 0, SIZE, x -> x > 0.5);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(counter);
        System.out.println(counter.join());
    }
}

class Counter extends RecursiveTask<Integer> {
    /**
     * 并行计算触发临界值，若待处理元素大于此临界值，才进行并行计算，否则直接处理即可
     */
    public static final int THRESHOLD = 1000;
    /**
     * 待处理元素集合
     */
    private double[] values;
    /**
     * 处理数据索引下界
     */
    private int from;
    /**
     * 处理数据索引上界
     */
    private int to;
    /**
     * 函数式接口实现，用于处理数据使用
     */
    private DoublePredicate filter;

    /**
     * 构造器
     *
     * @param values 待处理元素集合
     * @param from   处理元素索引开始位置
     * @param to     处理元素索引结束位置
     * @param filter 对元素处理函数式接口
     */
    public Counter(double[] values, int from, int to, DoublePredicate filter) {
        this.values = values;
        this.from = from;
        this.to = to;
        this.filter = filter;
    }

    @Override
    protected Integer compute() {
        if (to - from < THRESHOLD) {
            int count = 0;
            for (int i = from; i < to; i++) {
                if (filter.test(values[i])) count++;
            }
            return count;
        } else {
            int mid = (from + to) / 2;
            Counter first = new Counter(values, from, mid, filter);
            Counter second = new Counter(values, mid, to, filter);
            invokeAll(first, second);
            return first.join() + second.join();
        }
    }
}
