package com.java.core.concurrent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * 线程池测试
 * Executor 执行器类静态工厂方法用于构建线程池：
 * newCachedThreadPool 必要时会创建新线程，空闲线程会保留60s
 * newFixedThreadPool 包含固定数量线程，空闲线程一直被保留
 * newSingleThreadExecutor 单线程线程池，所有提交的任务顺序执行
 * newScheduledThreadPool 预定执行任务的固定线程池
 * newSingleThreadScheduledExecutor 预定执行任务的单线程池
 * <p>
 * shutdown()
 * shutdownNow() 取消所有任务
 * invokeAny()
 * invokeAll()
 * 使用ExecutorCompletionService 来组织任务的排列集合
 * <p>
 * Fork-Join框架实现并行计算 RecursiveTask<V> compute() 实现
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/13 17:12
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Enter base directory (e.g. /opt/jdk1.8.0/src):");
            String directory = in.nextLine();
            System.out.print("Enter keyword (e.g. volatile):");
            String keyword = in.nextLine();

            ExecutorService pool = Executors.newCachedThreadPool();
            MatchCounter1 counter1 = new MatchCounter1(new File(directory), keyword, pool);
            Future<Integer> result = pool.submit(counter1);

            try {
                System.out.println(result.get() + " matching files");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                pool.shutdown();
            }
            int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
            System.out.println("largetst pool size = " + largestPoolSize);
        }
    }
}

class MatchCounter1 implements Callable<Integer> {
    private File directory;
    private String keyword;
    private ExecutorService pool;
    private int count;

    public MatchCounter1(File directory, String keyword, ExecutorService pool) {
        this.directory = directory;
        this.keyword = keyword;
        this.pool = pool;
    }

    @Override
    public Integer call() throws Exception {
        count = 0;
        try {
            File[] files = this.directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            for (File file : files) {
                if (file.isDirectory()) {
                    MatchCounter1 counter1 = new MatchCounter1(file, keyword, pool);
                    Future<Integer> result = pool.submit(counter1);//向线程池中提交任务执行
                    results.add(result);
                } else {
                    if (search(file)) count++;
                }
            }
            for (Future<Integer> result : results) {
                try {
                    count += result.get();
                } catch (ExecutionException e) {
                    Exception exception = new Exception();
                    exception.initCause(e.getCause());
                    throw exception;
                }
            }
        } catch (InterruptedException e) {
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
    private boolean search(File file) {
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
