package com.java.core.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class SocketTest {
    public static void main(String[] args) throws IOException {
        try (Socket s = new Socket()) {
            //为了解决 s = new Socket(ip,port); 在到底目标主机之前无限阻塞等待的问题，使用下面的方式打开连接
            s.connect(new InetSocketAddress("time-A.timefreq.bldrdoc.gov", 13), 10000);
            InputStream inputStream = s.getInputStream();
            Scanner in = new Scanner(inputStream);
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);
            }
            //若需要在主机名和因特网地址之间转换，则可以使用InetAddress
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address.getHostAddress());
        }
    }

    public static void requestBaidu() throws IOException {
        try (Socket s = new Socket()) {
            s.connect(new InetSocketAddress("www.baidu.com", 80), 80);
        }
    }
}
