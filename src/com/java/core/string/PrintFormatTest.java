package com.java.core.string;

/**
 * @author Mr.X
 * @version 1.0.0
 * 打印格式化
 * @since 2018/12/4
 */
public class PrintFormatTest {
    public static void main(String[] args) {
        double str1 = -13432.2;
        double str2 = 2.345;
        String str3 = "hhhhh";
        System.out.printf("first str is %,.2f \n", str1);
        System.out.printf("second str is %,.2f \n", str2);
        System.out.printf("third str is %8s \n", str3);
    }
}
