package com.java.core.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ThreadEchoHandler implements Runnable {
    private Socket socket;

    public ThreadEchoHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = this.socket.getInputStream();
             Scanner in = new Scanner(inputStream);
             OutputStream outputStream = this.socket.getOutputStream();
             PrintWriter out = new PrintWriter(outputStream, true);
        ) {
            out.println("Hello,input 'BYE' to exit!");
            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine();
                out.println(line);
                if (line.equals("BYE")) {
                    done = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
