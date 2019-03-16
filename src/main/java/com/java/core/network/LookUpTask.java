package com.java.core.network;

import java.net.InetAddress;
import java.util.concurrent.Callable;

/**
 * 使用多线程处理日志，将日志中的ip地址转换为域名
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2018/12/19 16:29
 */
public class LookUpTask implements Callable<String> {
    private String logStr;

    public LookUpTask(String logStr) {
        this.logStr = logStr;
    }

    @Override
    public String call() throws Exception {
        String ip = logStr.substring(0, logStr.indexOf(" "));
        String restStr = logStr.substring(logStr.indexOf(" "));
        InetAddress inetAddress = InetAddress.getByName(ip);
        return inetAddress.getHostName();
    }
}
