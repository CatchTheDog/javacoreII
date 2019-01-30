package com.java.core.concurrent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列使用
 * 使用长度为10的阻塞队列存储内容，让100个线程同时消费队列中的内容。
 * 使用阻塞队列put,take方法，在队列中内容为空或者队列满时，自动阻塞以达到自动平衡负载的效果。
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2018/12/10 17:33
 */
public class BlockingQueueTest {
    /**
     * 阻塞队列大小为10
     */
    private static final int FILE_QUEUE_SIZE = 10;
    /**
     * 查找线程数为100
     */
    private static final int SEARCH_THREADS = 100;
    /**
     * 文件夹队列结束标识
     */
    private static final File DUMMY = new File("");
    /**
     * 存储文件队列
     */
    private static BlockingQueue<File> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Enter base directory (e.g. /opt/jdk1.8.0/src):");
            String directory = in.nextLine();
            System.out.print("Enter keyword (e.g. volatile):");
            String keyword = in.nextLine();

            Runnable enumerator = () -> {
                try {
                    enumerate(new File(directory));
                    queue.put(DUMMY);//加入文件夹结束标识
                } catch (InterruptedException e) {
                    System.out.println(e.getStackTrace());
                    Thread.currentThread().interrupt();
                }
            };

            //生产者启动
            new Thread(enumerator).start();

            for (int i = 0; i <= SEARCH_THREADS; i++) {
                Runnable searcher = () -> {
                    try {
                        boolean done = false;
                        while (!done) {
                            File file = queue.take();
                            if (file == DUMMY) {
                                queue.put(file);
                                done = true; //到达内容结尾，任务停止
                            } else {
                                search(file, keyword);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                };
                //消费者启动
                new Thread(searcher).start();
            }
        }
    }

    /**
     * 递归遍历指定文件夹及其子文件夹下的所有文件并放入阻塞队列中
     *
     * @param directory 待遍历文件夹路径
     * @throws InterruptedException 阻塞队列被中断时抛出
     */
    public static void enumerate(File directory) throws InterruptedException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) enumerate(file);
            else queue.put(file);
        }
    }

    /**
     * 在文件中查找关键词，如果查找成功，则在标准输出中输出文件路径，关键词所在行号，关键词所在行内容
     *
     * @param file    待查找文件
     * @param keyword 待查找关键词
     * @throws FileNotFoundException 文件未找到时抛出该异常
     */
    public static void search(File file, String keyword) throws FileNotFoundException {
        try (Scanner in = new Scanner(file, "UTF-8")) {
            int lineNumber = 0;
            while (in.hasNextLine()) {
                lineNumber++;
                String line = in.nextLine();
                if (line.contains(keyword))
                    System.out.printf("%s:%d:%s%n", file.getPath(), lineNumber, line);
            }
        }
    }
}
