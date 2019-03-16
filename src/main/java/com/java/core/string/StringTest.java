package com.java.core.string;

import java.util.Arrays;

/**
 * @author Mr.X
 * @version 1.0.0
 * 只有字符串常量是共享的，而通过+或者substring等操作产生的结果并不是共享的。
 * null用于表示没有任何对象与变量关联
 * char数据类型是一个采用UTF16编码表示Unicode码点的代码单元，大多数常用Unicode字符使用一个代码单元表示，但辅助字符需要一对代码单元表示。
 * 关于字符串的两个术语解释：
 * 代码点：一个字符（不论此字符是基本字符还是辅助字符），是指一个编码表中的某个字符对应的代码值，也就是Unicode编码表中每个字符对应的数值。U+0041——A
 * 代码单元：一个16位编码值，在UTF-16中的基本多语言级别中，，每一个字符用16位表示，通常被称为代码单元。因为UTF-16编码采用不同长度的编码表示所有的Unicode代码点，因此增补字符采用的是一对连续的代码单元进行编码。
 * StringBuilder线程不安全，StringBuffer线程安全；但是StringBuilder效率更高。
 * @since 2018/12/4
 */
public class StringTest {
    public static void main(String[] args) {
        String str = "hello world!";
        System.out.println(str.substring(0, 3));
        System.out.println(String.join("/", "s", "m", "l", "xl`"));
        System.out.println("equals".equalsIgnoreCase("Equals"));
        String str1 = "\uD835\uDD46 is the set of octonions";//包含一个辅助字符  ⑪  需要两个代码单元表示
        System.out.println(str1.length()); //length返回代码单元数量；而codePointCount返回代码点数量
        System.out.println(str1.codePointCount(0, str1.length())); //codepoint才是真是的字符串所含字符数；length是指字符串所含字符的编码值的格式，辅助字符需要使用一对编码值，所以如果包含有辅助字符的字符串的length要比codepoint大。
        int index = str1.offsetByCodePoints(0, 1);//返回字符串从起始位置偏移一定代码点后的索引值
        System.out.println((char) str1.codePointAt(index)); //获取指定位置的代码点——字符 返回的是int
        System.out.println(str1.charAt(1)); //为了避免在存在辅助字符的情况下的异常，避免使用charAt，尽量使用codePointAt
        //遍历一个字符串，并且依次查看每一个代码点：
        int[] codePoints = str1.codePoints().toArray();
        Arrays.stream(codePoints).forEach(x -> System.out.println(Integer.toHexString(x)));
    }
}
