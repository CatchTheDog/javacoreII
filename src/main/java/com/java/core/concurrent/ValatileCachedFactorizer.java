package com.java.core.concurrent;

import java.math.BigInteger;

/**
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/22 10:38
 */
public class ValatileCachedFactorizer {
    //不可变容器缓存结算结果
    private volatile OneValueCache cache = new OneValueCache(null, null);

    public BigInteger[] service(BigInteger i) {
        BigInteger[] factors = cache.getFactors(i);
        if (null == factors) {
            factors = factor(i);
            cache = new OneValueCache(i, factors);
        }
        return factors;
    }

    public BigInteger[] factor(BigInteger i) {
        return null;
    }
}
