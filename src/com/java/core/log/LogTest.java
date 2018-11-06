package com.java.core.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogTest {
    //创建或获取记录器
    private static final Logger myLogger = Logger.getLogger(LogTest.class.getName());

    public static void main(String[] args) {
        //全局日志
        Logger.getGlobal().info("File -> Open menu item selecte!");
        //取消其后所有日志
        Logger.getGlobal().setLevel(Level.OFF);
        Logger.getGlobal().info("afer off logger!");
    }
}
