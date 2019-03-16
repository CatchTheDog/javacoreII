package com.java.core.iotest;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.CRC32;

/**
 * NIO测试
 * NIO包含三个方面:
 * 字符集编码器和解码器
 * 非阻塞的I/O
 * 内存映射文件：将文件映射到内存地址中，这样文件就可以当做内存数组一样访问，文件访问效率更高。
 * channel(通道):通道是用于磁盘文件的一种抽象，它使我们可以访问注入内存映射、文件加锁机制以及文件间快速数据传输等操作系统特性；通过调用流的getChannel() 方法来获取通道。
 * 然后调用FileChannel类的map方法从此通道中获取一个MappedByteBuffer,可以指定想要的文件区域与映射模式，有三种模式：
 * 只读，可读写（写操作结果会实时刷新到文件中，但是多个文件的操作结果依赖操作系统的行为）,可写但私有(写操作结果不会实时刷新到文件中)
 * 有了缓冲区，就可以使用ByteBuffer类和Buffer超类的方法读写数据了。缓冲区支持顺序和随机数据访问，可以使用get和put操作来推动读写位置。
 * 文件加锁机制:
 * 文件锁：文件锁可以控制对文件或文件中某个范围的字节的访问。
 * 要锁定一个文件，可以调用FileChannel类的lock或tryLock方法。
 * 调用FileLock类的release()方法释放锁
 * Selectors:允许单线程处理多个Channel。它是JAVA NIO中能够检测一到多个NIO通道，并能够了解通道是否为读写时间做好准备的组件，通过它可以实现一个单独的线程可以管理多个channel。
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/20 10:07
 */
public class NIOTest {
    /**
     * 获取指定文件CRC校验和
     *
     * @param fileName 待校验文件路径
     * @return 文件校验和
     * @throws IOException 文件读取异常时抛出
     */
    public static long checksumInputStream(String fileName) {
        CRC32 crc32 = new CRC32();
        try (InputStream inputStream = new FileInputStream(fileName)) {
            int c;
            while ((c = inputStream.read()) != -1) {
                crc32.update(c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return crc32.getValue();
    }

    public static long checksumBufferedInputStream(String fileName) {
        CRC32 crc32 = new CRC32();
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileName))) {
            int c;
            while ((c = bufferedInputStream.read()) != -1) {
                crc32.update(c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return crc32.getValue();
    }

    /**
     * 关于RandomAccessFile
     * 支持文件随机访问，可以使用seek()将文件指针定位到任意位置，然后开始读取文件
     *
     * @param fileName 待读取文件路径
     * @return 文件校验和
     */
    public static long checksumRandomAccessFile(String fileName) {
        CRC32 crc32 = new CRC32();
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "r")) {
            long len = randomAccessFile.length();
            for (long p = 0; p < len; p++) {
                randomAccessFile.seek(p);//将文件指针定位到文件中第p个字节的位置
                crc32.update(randomAccessFile.readByte());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return crc32.getValue();
    }

    public static long checksumMappedFile(String fileName) {
        CRC32 crc32 = new CRC32();
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             FileChannel channel = fileInputStream.getChannel()) {
            long len = channel.size();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, len);
            for (long p = 0; p < len; p++) {
                crc32.update(buffer.get());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return crc32.getValue();
    }

    /**
     * Selector
     */
    public static void testSelector() {
        try (SocketChannel channel = null) {
            Selector selector = Selector.open();
            channel.configureBlocking(false);//与selector 一起使用的channel一定要在非阻塞模式下，所以FileChannel无法使用Selector.
            SelectionKey key = channel.register(selector, SelectionKey.OP_ACCEPT, "accept1");//注册通道，注册关注事件，已经关联对象
            while (selector.select() > 0) {
                Set selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey keyReady = keyIterator.next();
                    String id = (String) keyReady.attachment();
                    switch (id) {
                        case "accept1":
                            SocketChannel socketChannel = (SocketChannel) keyReady.channel();
                            //进行其他操作
                            break;
                        case "connect1":
                            break;
                        case "read1":
                            break;
                        case "write1":
                            break;
                    }
                    keyIterator.remove();//处理结束即删除
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String filePath = "C:\\马俊强\\commons-collections-3.2.1.jar";
        System.out.println("InputStream");
        long start = System.currentTimeMillis();
        long crcValue = checksumInputStream(filePath);
        System.out.println("result:" + Long.toHexString(crcValue));
        System.out.println("use " + (System.currentTimeMillis() - start) + "milliseconds.");

        System.out.println("BufferedInputStream");
        start = System.currentTimeMillis();
        crcValue = checksumBufferedInputStream(filePath);
        System.out.println("result:" + Long.toHexString(crcValue));
        System.out.println("use " + (System.currentTimeMillis() - start) + "milliseconds.");

        System.out.println("RandomAccessFile");
        start = System.currentTimeMillis();
        crcValue = checksumRandomAccessFile(filePath);
        System.out.println("result:" + Long.toHexString(crcValue));
        System.out.println("use " + (System.currentTimeMillis() - start) + "milliseconds.");

        System.out.println("MappedFile");
        start = System.currentTimeMillis();
        crcValue = checksumMappedFile(filePath);
        System.out.println("result:" + Long.toHexString(crcValue));
        System.out.println("use " + (System.currentTimeMillis() - start) + "milliseconds.");
    }
}
