package com.java.core.iotest;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
 * DataOutput 以二进制格式写数组、字符、布尔值已经字符串
 * DataInput  读取二进制
 * @since 2019/02/14 14:33
 */
public class IOTest {
    public static void main(String[] args) throws IOException {
        PrintWriter writer = new PrintWriter(System.out, true);
        writer.println("1234xyz");
        writer.flush();
        //writer.close(); //不可在此处关闭流，若在此处关闭流之后，其后所有到标准输出流的输出都无效，因为从底层中调用了Systme.out.close(); 但是代码在执行时不会有异常
        PrintWriter bos = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File("C:\\马俊强\\testio.txt"))), true);
        bos.print(123);
        bos.print(1);
        bos.print(1234L);
        bos.print(false);
        bos.print(1.23);
        bos.close();

        Charset charset = Charset.forName("ISO-8859-1");
        Set<String> aliases = charset.aliases();
        for (String aliase : aliases) {
            System.out.print(" " + aliase);
        }
        System.out.println();
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
            System.out.print(" " + name);
        }
        System.out.println();
        PushbackInputStream pushbackInputStream = new PushbackInputStream(new BufferedInputStream(new FileInputStream(new File("C:\\马俊强\\testio.txt"))), 1024);
        byte[] bytes1 = new byte[8];
        pushbackInputStream.read(bytes1);
        if (new String(bytes1, "utf-8").equals("123a1234")) {
            pushbackInputStream.unread(bytes1); //跳过部分数据
        }
        pushbackInputStream.read(bytes1); //重新读取数据,PushbackInputStream 缓冲区默认大小是1字节，可以在构造函数中指定缓冲区大小
        System.out.println(new String(bytes1));
        pushbackInputStream.close();
        //如何回推一个double
        DataInputStream din = new DataInputStream(pushbackInputStream = new PushbackInputStream(new BufferedInputStream(new FileInputStream("C:\\马俊强\\testio.txt")), 1024));
        double d = din.readDouble();
        byte[] doubleAsBytes = new byte[8];
        ByteBuffer.wrap(doubleAsBytes).putDouble(d);
        pushbackInputStream.unread(doubleAsBytes);
        double d1 = din.readDouble(); //12311234false1.23
        System.out.println(d1);
        System.out.println(d);

        //未正确读取到压缩文件
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream("C:\\马俊强\\testio.zip"));
        ZipEntry entry;
        LineNumberReader reader = null;
        while ((entry = zipInputStream.getNextEntry()) != null) {
            System.out.println(entry.getName());
            reader = new LineNumberReader(new InputStreamReader(zipInputStream, "GBK")); //使用UTF-8解码，则出现乱码，为啥子？
            String str1;
            while ((str1 = reader.readLine()) != null) {
                System.out.println(reader.getLineNumber() + "行：" + str1);
            }
            zipInputStream.closeEntry();
        }
        if (null != reader) {
            reader.close();
        }
        if (null != zipInputStream) {
            zipInputStream.close();
        }
        //生成zip文件
        ZipUtils.generateZipCompressedFile("C:\\马俊强\\学习资料\\需求", "C:\\马俊强\\学习资料\\majunqiang.zip");
        writer.close();
    }


}
