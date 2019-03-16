package com.java.core.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadEchoServer {
    public static void main(String[] args) {
        try {
            int i = 1;
            ServerSocket s = new ServerSocket(8189);
            while (true) {
                Socket incoming = s.accept();
                System.out.println("Swapping " + i);
                Runnable r = new ThreadEchoHandler(incoming);
                Thread thread = new Thread(r);
                thread.start();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
