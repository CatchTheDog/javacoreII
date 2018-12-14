package com.java.core.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestReachable {
    public static void main(String[] args) {
        try {
            InetAddress inetAddress = InetAddress.getByName("192.30.253.112");
            System.out.println(inetAddress.isReachable(10000));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
