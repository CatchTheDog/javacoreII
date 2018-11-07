package com.java.core.log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.*;

/**
 * java 日志默认配置未见位置为 jre/lib/logging.properties文件，可以使用 java -Djava.util.logging.config.file=configfilepath MainClass 来修改此默认配置文件为新的配置文件
 * 可以在配置文件中修改默认的日志记录级别，在日志记录器名称后加上.level 为该日志记录器配置级别
 * 也可以通过修改java.util.logging.config.class系统属性设置来修改日志属性文件管理器。
 */
public class LogTest {
    //创建或获取记录器
    private static final Logger myLogger = Logger.getLogger(LogTest.class.getName());

    public static void main(String[] args) {
        //全局日志
        Logger.getGlobal().info("File -> Open menu item selecte!");
        //取消其后所有日志
        Logger.getGlobal().setLevel(Level.OFF);
        Logger.getGlobal().info("afer off logger!");
        testHighLevelLogger();
        testLogp();
        //testExceptionLog("4545","1232");
        testLocalise("", "");
        testSelfHandlers();
        testFilter("hahahahh好");
        testFormator("格式化");
    }

    /**
     * 高级日志记录器测试
     * 默认情况下只打印 severe warning info 三个级别日志
     * 且打印在控制台
     * 使用logp方法获取调用类和方法的确切位置
     */
    public static void testHighLevelLogger() {
        myLogger.severe("日志1");
        myLogger.warning("日志1");
        myLogger.info("日志3");
        myLogger.config("日志4");
        myLogger.fine("日志5");
        myLogger.finer("日志6");
        myLogger.finest("日志7");
        myLogger.log(Level.INFO, "日志8");
    }

    public static void testLogp() {
        myLogger.entering(LogTest.class.getName(), "testLogp", null);
        System.out.println("testLogp method run!");
        myLogger.exiting(LogTest.class.getName(), "testLogp", "运行结束！");
    }

    /**
     * 日志记录异常
     *
     * @param a
     * @param path
     */
    public static void testExceptionLog(String a, String path) {
        if (null == a || a.trim().length() < 1) {
            IllegalArgumentException exception = new IllegalArgumentException();
            myLogger.throwing(LogTest.class.getName(), "testExceptionLog", exception);
            throw exception;
        }
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            byte[] buffer = new byte[1024];
            int count = -1;
            while ((count = fileInputStream.read(buffer)) > -1) {
                String str = new String(buffer, 0, count - 1);
                System.out.println(str);
            }
        } catch (IOException e) {
            myLogger.log(Level.WARNING, "reading file exception!", e);
        }
    }

    public static void testLocalise(String a, String b) {
        //在获取logger时指定本地化资源包名：com.java.core.log.logmessages
        Logger localLogger = Logger.getLogger(LogTest.class.getName(), "com.java.core.log.logmessages");
        localLogger.info("readingFile");
        //向资源文件内占位符传递参数
        localLogger.log(Level.INFO, "renamingFile", new Object[]{"A", "B"});

    }

    /**
     * 日志处理器也有级别，只有当日志级别高于日志记录器级别和日志处理器级别时，日志才会被记录
     * 默认情况下的日志记录器的级别时INFO
     * 如果需要记录低于INFO级别的日志，就需要修改默认配置文件；
     * 或者绕过配置文件，设置自己的处理器。
     */
    public static void testSelfHandlers() {
        Logger logger = Logger.getLogger(LogTest.class.getName());
        //设置日志记录器级别为FINE
        logger.setLevel(Level.FINE);
        //在默认情况下，日志记录器将记录发送到自己的处理器和父处理器，自定义的日志记录器都是原始日志记录器（命名为“”）的子类，而
        //原始记录器将会把所有等于或者高于INFO级别的记录发送到控制台；为了避免在控制台中看到两次日志记录，就将useParentHandlers设置为false
        logger.setUseParentHandlers(false);
        //定义并设置自己的处理器
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.FINE);
        logger.addHandler(handler);
        logger.fine("fine级别日志！");
        try {
            //默认提供了ConsoleHandler FileHandler SocketHandler
            //可以通过设置日志管理器配置文件的不同参数，或者利用其它的构造器配置不同的参数。
            FileHandler fileHandler = new FileHandler();
            logger.addHandler(fileHandler);
            logger.info("filehandler log");
            //可以通过扩展Handler类或StreamHandler类自定义处理器
        } catch (IOException e) {
            logger.throwing(LogTest.class.getName(), "testSelfHandlers", e);
        }
    }

    /**
     * 默认情况下系统使用级别进行日志过滤，可以通过实现Filter自定义过滤规则
     * 同一时刻只能有一个过滤器工作
     *
     * @param message
     */
    public static void testFilter(String message) {
        Logger logger = Logger.getLogger(LogTest.class.getName());
        logger.info("过滤器开始工作了！");
        logger.setFilter(record -> record.getMessage().contains("好"));
        logger.info(message);
    }

    /**
     * 测试格式化器
     *
     * @param message
     */
    public static void testFormator(String message) {
        Logger logger = Logger.getLogger(LogTest.class.getName());
        logger.info("格式化器开始工作了");
        logger.setLevel(Level.INFO);
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return formatMessage(record);
            }
        });
        handler.setLevel(Level.INFO);
        logger.addHandler(handler);
        logger.info(message);
    }

}
