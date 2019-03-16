package com.java.core.concurrent;


public class SynchBankTest {
    public static void main(String[] args) {
        int size = 100;
        int initial = 1000;
        //Bank_Lock bank = new Bank_Lock(size, initial);
        Bank_Synchronized bank = new Bank_Synchronized(size, initial);
        while (true) {
            new Thread(() -> {
                int from = (int) (size * Math.random());
                int to = (int) (size * Math.random());
                try {
                    bank.transfer(from, to, initial * Math.random());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
