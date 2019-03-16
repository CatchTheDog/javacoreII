package com.java.core.concurrent;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * 不可变容器：使用容器复制，使得对象内部容器状态一旦初始化就无法改变
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/22 10:27
 */
public class OneValueCache {
    private final BigInteger lastNumber;
    private final BigInteger[] lastFactors;

    public OneValueCache(BigInteger lastNumber, BigInteger[] lastFactors) {
        this.lastNumber = lastNumber;
        this.lastFactors = Arrays.copyOf(lastFactors, lastFactors.length);
    }

    public BigInteger[] getFactors(BigInteger i) {
        if (null == lastNumber || !lastNumber.equals(i)) return null;
        else return Arrays.copyOf(lastFactors, lastFactors.length);
    }
}
