package com.java.core.concurrent;

import java.util.HashSet;
import java.util.Set;

/**
 * 不可变对象：
 * 它的状态不能在创建后再被修改
 * 所有域都是final类型，并且它被正确创建（在创建期间没有发生this引用逸出）
 * 提供的对类状态进行检测的方法的结果依赖于该对象的不可变域的计算
 * 除构造函数外无其他入口可以修改域值
 * <p>
 * 下面的类：无法继承
 * 所有域都是私有，final类型
 * 所有域的初始化都在构造函数中完成，除此之外无其他入口可以修改域
 * 构造函数内部无this引用逸出，对象构造完整
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/22 9:14
 */
public final class ThreeStooges {
    /**
     * 私有 final 且 无其他入口可以修改其所包含元素
     */
    private final Set<String> stooges = new HashSet<>();

    /**
     * 初始化所有域
     */
    public ThreeStooges() {
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Curly");
    }

    /**
     * 只能探查类状态，无法修改类状态
     *
     * @param name
     * @return
     */
    public boolean isStooge(String name) {
        return stooges.contains(name);
    }
}
