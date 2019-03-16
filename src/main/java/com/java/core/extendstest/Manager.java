package com.java.core.extendstest;

/**
 * @author Mr.X
 * @version 1.0.0
 * Object:
 * equals() 判断两个对象是否具有相同的引用  这是对象相等的默认行为。
 * getClass() 返回一个对象所属的类
 * instanceof  在equals方法中使用此方法会破坏对称性。
 * hashCode方法
 * @since 2018/12/4
 */
public class Manager extends Employee {
    public static void main(String[] args) {
        Manager manager = new Manager();
        System.out.println(manager.getAge());
//        Object array = {1,2,3};  //报错
        int[] array_3 = {1, 2, 3};
        Object array_4 = new int[]{1, 2, 3};
        Object[] array_1 = {1, 3, 4};
        Object array_2 = new int[]{1, 2, 3};
        Object array_5 = new int[10];
    }
}
