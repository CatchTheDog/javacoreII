package com.java.core.generic;

public class Swaper {
    public static <T> void swapHelper(GenericMethodTest.Pair<T> p) {
        T t = p.getFirst();
        p.setFirst(p.getSecond());
        p.setSecond(t);
    }

    /**
     * 通配符实现值交换
     * 需要保存临时变量，但是? 不能作为类型使用，所以使用swapHelper方法，类型T 会自动捕获?
     *
     * @param p
     */
    public static void swap(GenericMethodTest.Pair<?> p) {
        swapHelper(p);
    }
}
