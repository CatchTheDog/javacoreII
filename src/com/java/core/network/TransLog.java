package com.java.core.network;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 读取日志文件并将文件中的ip地址转换为主机名称
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2018/12/19 17:01
 */
public class TransLog {
    /**
     * 文件名称，从classes路径下查找
     */
    private String logPath;

    public TransLog(String logPath) {
        this.logPath = TransLog.class.getResource(logPath).getPath();
    }

    /**
     * 测试
     *
     * @param args
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IOException
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        String path = "log.txt";
        TransLog transLog = new TransLog(path);
        transLog.trans();
    }

    public void trans() throws IOException, ExecutionException, InterruptedException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.logPath)))) {
            ExecutorService executorService = Executors.newCachedThreadPool();
            Queue<LogEntry> results = new LinkedList<>();
            String line;
            while ((line = br.readLine()) != null) {
                Future<String> future = executorService.submit(new LookUpTask(line));
                results.add(new LogEntry(line, future));
            }
            for (LogEntry logEntry : results) {
                System.out.println(logEntry.logStr + ":" + logEntry.future.get());
            }
            executorService.shutdown();
        }
    }

    private class LogEntry {
        private String logStr;
        private Future<String> future;

        public LogEntry(String logStr, Future<String> future) {
            this.logStr = logStr;
            this.future = future;
        }

        public String getLogStr() {
            return logStr;
        }

        public void setLogStr(String logStr) {
            this.logStr = logStr;
        }

        public Future<String> getFuture() {
            return future;
        }

        public void setFuture(Future<String> future) {
            this.future = future;
        }
    }
}
