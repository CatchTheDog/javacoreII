package com.java.core.generic;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Mr.X
 * 泛型方法与普通方法在编译时并不冲突（方法名称相同）
 * 若使用普通方法调用方式，则自动调用普通方法
 * 若使用泛型方法调用方式，则会显示方法调用冲突
 */
public class PairTest1 {
    public static void main(String[] args) {
        String[] words = {"Mary", "had", "a", "little", "lamb"};
        Pair<String> mm = ArrayAlg.minmax(words);
        //Pair<String> mm = ArrayAlg.<String>minmax(words);
        System.out.println("min = " + mm.getFirst());
        System.out.println("max = " + mm.getSecond());
    }
}

class ArrayAlg {

    /**
     * 普通方法
     *
     * @param a 数组，存储需要处理的元素
     * @return 数组中最小值，最大值二元组构造的对象
     */
    public static Pair<String> minmax(String[] a) {
        if (null == a || a.length == 0) return null;
        Pair<String> pair = new Pair<>();
        pair.setFirst(a[0]);
        pair.setSecond(a[0]);
        iterate(a, pair);
        return pair;
    }

    /**
     * 泛型方法
     *
     * @param a   数组，存储需要处理的元素
     * @param <T> 泛型类型参数
     * @return 数组中最小值，最大值二元组构造的对象
     */
    public static <T extends Comparable & Serializable> Pair<T> minmax(T[] a) {
        if (null == a || a.length == 0) return null;
        Pair<T> pair = new Pair<>();
        pair.setFirst(a[0]);
        pair.setSecond(a[0]);
        iterate(a, pair);
        return pair;
    }

    /**
     * 遍历数组，获取最大值，最小值 二元组
     *
     * @param a    数组，存储需要处理的元素
     * @param pair 存储最大值、最小值二元组的对象
     * @param <T>  类型参数
     */
    private static <T extends Comparable & Serializable> void iterate(T[] a, Pair<T> pair) {
        Arrays.stream(a).forEach(x -> {
            if (pair.getFirst().compareTo(x) > 0) pair.setFirst(x);
            if (pair.getSecond().compareTo(x) < 0) pair.setSecond(x);
        });
    }
}

/**
 * @param <T> 泛型类型参数
 * @author Mr.X
 * @version 1.1
 * 泛型用来做类型检查使用，避免了部分强制类型转换，且可以实现多态方法；内部使用了类型擦除机制，对于有限定的类型参数，使用第一个参数类型，对于无限定的类型参数，使用Object.
 * 由于泛型使用类型擦除机制，所以会导致：
 *      1.
 *      2.
 * java对泛型有如下限制：
 *      1.不应在静态成员中医用封闭类型参数
 *      2.不能用基本类型实例化泛型类型参数
 *      3.不能在数据类型转换或instanceof操作中使用“外露”类型参数
 *      4.不能再new操作中使用外露类型参数
 *      5.不能再类定义的implements 或 extends 子句中使用外露类型参数
 * 在方法调用、变量值存取时，系统会自动为泛型域做强制类型转换；
 *
 *
 */
class Pair<T> {
    private T first;
    private T second;

//    private static T third;

    public Pair() {
    }

//    public Pair(T first, T second) {
//        this.first = first;
//        this.second = second;
//    }
//
//    private static T testStatic(){
//        return third;
//    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }
}
