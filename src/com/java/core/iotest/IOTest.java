package com.java.core.iotest;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

/**
 * Java IO 测试
 *
 * @author Mr.X
 * @version 1.0.0
 * 流需要进行组合使用，将最终使用的流置于最后，以供使用。
 * PushbackInputStream 回推流
 * ZipInputStream 读取ZIP压缩文件
 * 文本输入与输出：
 * 整数1234 以二进制数方式存储  00 00 04 D2
 * 以文本方式存储    "1234"       需要考虑字符编码方式
 * OutputStreamWriter  将Unicode字符流转换为字节流
 * InputStreamReader   将字节流转换为Unicode字符流
 * PrintWriter 以文本格式打印字符串和数字
 * Scanner 读入文本,数字等
 * BufferedReader 读入文本
 * @since 2019/02/14 14:33
 */
public class IOTest {
    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(System.out);
        writer.print("1234xyz");
        writer.close();
        PrintWriter bos = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File("C:\\马俊强\\testio.txt"))), true);
        bos.print(123);
        bos.print('a');
        bos.print(1234L);
        bos.print(false);
        bos.print(1.23);
        bos.close();

        Charset charset = Charset.forName("ISO-8859-1");
        Set<String> aliases = charset.aliases();
        for (String aliase : aliases) {
            System.out.println(aliase);
        }
        //使用指定的字符集对字符进行编码
        String str = "nihaoa";
        ByteBuffer buffer = charset.encode(str);
        byte[] bytes = buffer.array();
        //使用指定的字符集对字符进行解码
        ByteBuffer bbuffer = ByteBuffer.wrap(bytes);
        CharBuffer cbuf = charset.decode(bbuffer);
        String x = cbuf.toString();
        System.out.println(x);

        Map<String, Charset> charsets = Charset.availableCharsets();
        for (String name : charsets.keySet()) {
            System.out.println(name);
        }
    }
}
