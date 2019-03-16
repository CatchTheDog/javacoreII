package com.java.core.iotest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JAVA <code>正则表达式</code>测试
 * 正则表达式支持模式：
 * <p>
 * Matcher.matches() 对整个字符串进行匹配，只有整个字符串匹配了才返回true
 * Matcher.find() 对字符串任意位置进行匹配
 * Matcher.lookingAt() 对字符串开头进行匹配
 * <p>
 * 使用上面三个方法做了匹配操作之后，就可以使用下面的方法进行分析：
 * Matcher.start()
 * Matcher.end()
 * Matcher.group()
 * <p>
 * 模式内置分组时，Matcher.groupCount() Matcher.start(i) Matcher.end(i) Matcher.group(i) 可以使用
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/2/15 13:31
 */
public class RegularExpressions {
    public static void main(String[] args) {
        test4();
    }


    public static void test1() {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher("日记 2018-12-01 阴雨 2019-02-25 晴");
        while (matcher.find()) {
            String matchStr = matcher.group();
            System.out.println(matchStr);
        }
    }

    public static void test2() {
        Pattern pattern = Pattern.compile("\\d+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher("aaa2223bb3333ccc333");
        while (matcher.find()) {
            System.out.println(matcher.start());
            System.out.println(matcher.end());
            System.out.println(matcher.group());
        }
    }

    public static void test3() {
        Pattern pattern = Pattern.compile("\\d+.*", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher("2223bb");
        if (matcher.matches()) {
            System.out.println(matcher.start());
            System.out.println(matcher.end());
            System.out.println(matcher.group());
        }
    }

    public static void test4() {
        Pattern pattern = Pattern.compile("([a-z]+)(\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher("aaa2223bb");
        if (matcher.find()) {
            System.out.println(matcher.groupCount());
            System.out.println(matcher.start(1));
            System.out.println(matcher.end(1));
            System.out.println(matcher.group(1));
            System.out.println(matcher.start(2));
            System.out.println(matcher.end(2));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group());
        }
    }
}
