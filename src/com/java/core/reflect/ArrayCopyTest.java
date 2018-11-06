package com.java.core.reflect;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayCopyTest {
    /**
     * 通用数组复制工具方法
     *
     * @param a
     * @param newLength
     * @return
     */
    public static Object arrayCopy(Object a, int newLength) {
        if (null == a || !a.getClass().isArray()) return null;
        int originalLength = Array.getLength(a);
        Class elementType = a.getClass().getComponentType();
        Object newArray = Array.newInstance(elementType, newLength);
        System.arraycopy(a, 0, newArray, 0, Math.min(originalLength, newLength));
        return newArray;
    }

    /**
     * 在使用时，如果外部对返回的Object [] 进行转换，则会跑出异常
     *
     * @param a
     * @param newLength
     * @return
     */
    public static Object[] wrongArrayCopy(Object[] a, int newLength) {
        Object[] newArray = new Object[newLength];
        System.arraycopy(a, 0, newArray, 0, Math.min(a.length, newLength));
        return newArray;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        a = (int[]) arrayCopy(a, 10);
        System.out.println(Arrays.toString(a));

        String[] b = {"aaa", "bbb", "ccc"};
        b = (String[]) arrayCopy(b, 10);
        System.out.println(Arrays.toString(b));

        System.out.print("The following call will generate an exception.");
        b = (String[]) wrongArrayCopy(b, 10);
    }
}
