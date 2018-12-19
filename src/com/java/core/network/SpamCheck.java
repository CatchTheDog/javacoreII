package com.java.core.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Mr.X
 * @version 1.0.0
 * @since 2018/12/19 16:10
 */
public class SpamCheck {
    public static final String BLACKHOLE = "sbl.spamhaus.org";

    public static void main(String[] args) {
        String[] ips = {"207.34.56.23", "125.12.32.4", "130.130.130.130"};
        for (String arg : ips) {
            if (isSpammer(arg)) {
                System.out.println(arg + " is a known spammer.");
            } else {
                System.out.println(arg + " apperars legitimate.");
            }
        }
    }

    private static boolean isSpammer(String arg) {
        try {
            InetAddress address = InetAddress.getByName(arg);
            byte[] quad = address.getAddress();
            String query = BLACKHOLE;
            for (byte octet : quad) {
                int unsignedByte = octet < 0 ? octet + 256 : octet;
                query = unsignedByte + "." + query;
            }
            InetAddress.getByName(query);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }

    }
}
