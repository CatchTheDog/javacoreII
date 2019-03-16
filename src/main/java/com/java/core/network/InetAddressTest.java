package com.java.core.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressTest {
    public static void main(String[] args) throws UnknownHostException {
        if (args.length > 0) {
            String host = args[0];
            InetAddress[] addresses = InetAddress.getAllByName(host); //获取指定地址（域名或者点分四段模式的ip）的所有地址对象
            for (InetAddress a : addresses)
                System.out.println(a);
        } else {
            InetAddress localHostAddress = InetAddress.getLocalHost(); //获取本机地址对象
            System.out.println(localHostAddress);
        }

        InetAddress inetAddress = InetAddress.getByName("www.baidu.com");
        System.out.println(inetAddress);

        //为DNS上不存在的主机创建地址对象
        byte[] address = {107, 23, (byte) 216, (byte) 196};
        InetAddress lessWrong = InetAddress.getByAddress(address);
        InetAddress lessWrongWithName = InetAddress.getByAddress("lesswrong.com", address);

        //虚拟机参数 networkaddress.cache.ttl/networkaddress.cache.negative.ttl 控制DNS缓存

        //使用new SecurityManager().checkConnnect(String hostname,int port); 方法检测一个主机能否解析

        System.out.println(lessWrong.getHostName());
        System.out.println(lessWrong.getCanonicalHostName());
        System.out.println(lessWrong.getHostAddress());
        byte[] address_1 = lessWrong.getAddress(); //ipv4 4字节  ipv6 16字节
        //在java中，没有无符号字节这一数据类型，值大于127的字节会被当做负数
        System.out.println("-------");
        for (int i = 0; i < address_1.length; i++) {
            System.out.print(address_1[i] + "  ");
            System.out.println(address_1[i] < 0 ? address_1[i] + 256 : address_1[i]);
        }


    }
}
