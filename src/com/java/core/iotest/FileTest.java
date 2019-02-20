package com.java.core.iotest;

import java.io.File;
import java.util.Arrays;

public class FileTest {
    public static void main(String[] args) {
        File file = new File("C:\\马俊强\\软件安装");
        System.out.println("总空间：" + file.getTotalSpace() / 1024 / 1024 / 1024 + "G");
        System.out.println("未分配空间：" + file.getFreeSpace() / 1024 / 1024 / 1024 + "G");
        System.out.println("可用空间：" + file.getUsableSpace() / 1024 / 1024 / 1024 + "G");
        File[] files = File.listRoots();
        Arrays.stream(files).forEach(x -> System.out.println(x.getAbsolutePath()));
    }
}
