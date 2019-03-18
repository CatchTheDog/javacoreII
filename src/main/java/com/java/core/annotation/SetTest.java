package com.java.core.annotation;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 字节码工程测试
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/3/16 15:48
 */
public class SetTest {
    public static void main(String[] args) {
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.FINEST);
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).addHandler(handler);

        Set<Item> parts = new HashSet<>();
        parts.add(new Item("Toaster", 1279));
        parts.add(new Item("Microwave", 4104));
        parts.add(new Item("Toaster", 1279));
        System.out.println(parts);
    }
}
