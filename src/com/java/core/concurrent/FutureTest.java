package com.java.core.concurrent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 测试Callable,Future:接口
 * FutureTask：实现Future,Runnable接口，可以将Callable对象转换为Runnable对象并交给线程进行执行，可以实现任务的开启，取消，获取结果等。
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/13
 */
public class FutureTest {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Enter base directory (e.g. /opt/jdk1.8.0/src):");
            String directory = in.nextLine();
            System.out.print("Enter keyword (e.g. volatile):");
            String keyword = in.nextLine();

            MatchCounter matchCounter = new MatchCounter(new File(directory), keyword);
            FutureTask<Integer> futureTask = new FutureTask<>(matchCounter);
            new Thread(futureTask).start();
            try {
                System.out.println(futureTask.get() + " matching files");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }
}

/**
 * MathCounter 类实现Callable<V>接口
 */
class MatchCounter implements Callable<Integer> {
    private File directory;
    private String keyword;

    public MatchCounter(File directory, String keyword) {
        this.directory = directory;
        this.keyword = keyword;
    }

    @Override
    public Integer call() throws Exception {
        int count = 0;
        try {
            File[] files = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();
            for (File file : files) {
                //如果当前文件是文件夹，则启动线程递归处理当前文件夹；否则，直接在文件中查找
                if (file.isDirectory()) {
                    MatchCounter counter = new MatchCounter(file, keyword);
                    FutureTask<Integer> task = new FutureTask(counter);
                    results.add(task);
                    new Thread(task).start();
                } else {
                    if (search(file)) count++;
                }
            }
            for (Future<Integer> result : results) {
                try {
                    count += result.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    Exception throwable = new Exception();
                    throwable.initCause(e.getCause());
                    throw throwable;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Exception exception = new Exception();
            exception.initCause(e.getCause());
            throw exception;
        }
        return count;
    }

    /**
     * 在文件中查找关键词，找到一次即可返回；未找到直至文件末尾
     *
     * @param file 待查找文件
     * @return 是否在文件中找到关键词
     */
    public boolean search(File file) {
        try {
            try (Scanner in = new Scanner(file, "UTF-8")) {
                boolean found = false;
                while (!found && in.hasNextLine()) {
                    String line = in.nextLine();
                    found = line.contains(this.keyword);
                }
                return found;
            }
        } catch (IOException e) {
            return false;
        }
    }
}