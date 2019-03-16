package com.java.core.string;

import java.io.Console;
import java.util.Scanner;

/**
 * @author Mr.X
 * @version 1.0.0
 * 读取输入示例程序
 * @since 2018/12/4 13:49
 */
public class ScannerTest {
    public static void main(String[] args) {
        //Scanner可以从控制台读取各种类型的数据
        Scanner in = new Scanner(System.in);
        System.out.println("Hello?");
        String reply = in.nextLine();
        String next = in.next();
        int age = in.nextInt();
        //读取密码 Console只能每次读取一行，没有其他方法
        Console cons = System.console();
        String userName = cons.readLine("User name:");
        char[] passwd = cons.readPassword("Password:");
    }
}
