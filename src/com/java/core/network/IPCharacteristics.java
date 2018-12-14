package com.java.core.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPCharacteristics {
    public static void main(String[] args) {
        try {
            String ip = "127.0.0.1";
            String ip_1 = "www.baidu.com";
            String ip_2 = "192.168.254.32";
            String ip_3 = "224.0.2.1";
            String ip_4 = "FF01:0:0:0:0:0:0:1";
            String ip_5 = "FF05:0:0:0:0:0:0:101";
            String ip_6 = "0::1";

            InetAddress address = InetAddress.getByName(ip);
            if (address.isAnyLocalAddress()) {//通配地址
                System.out.println(address + " is a wildcard address");
            }
            if (address.isLoopbackAddress()) {//回送地址
                System.out.println(address + " is loopback address");
            }
            if (address.isLinkLocalAddress()) {//IPv6本地连接地址
                System.out.println(address + " is a link-local address");
            } else if (address.isSiteLocalAddress()) {//Ipv6 本地网站地址
                System.out.println(address + " is a site-local address");
            } else {
                System.out.println(address + " is a global address");
            }

            if (address.isMulticastAddress()) { //组播地址
                if (address.isMCGlobal()) { //全球组播地址
                    System.out.println(address + " is a global multicast address");
                } else if (address.isMCOrgLocal()) {//组织范围组播地址
                    System.out.println(address + " is an organization wide multicast address");
                } else if (address.isMCSiteLocal()) {//网站范围组播地址
                    System.out.println(address + " is a site wide multicast address");
                } else if (address.isMCLinkLocal()) {//子网组播地址
                    System.out.println(address + " is a subnet wide multicast");
                } else if (address.isMCNodeLocal()) {//本地接口组播地址
                    System.out.println(address + " is an interface-local multicase address");
                } else {
                    System.out.println(address + " is an unknown multicase address type");
                }
            } else {
                System.out.println(address + " is a unicast address");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
