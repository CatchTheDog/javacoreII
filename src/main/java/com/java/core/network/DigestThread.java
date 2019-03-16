package com.java.core.network;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 读取一个文件并为之生成256位的SHA-2消息摘要
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2018/12/10 9:09
 */
public class DigestThread implements Runnable {
    private String filename;

    public DigestThread(String filename) {
        this.filename = filename;
    }

    public static void main(String[] args) {
        String[] filenames = {""};
        for (String filename : filenames) {
            Thread t = new Thread(new DigestThread(filename));
            t.start();
        }
    }

    @Override
    public void run() {
        try (FileInputStream in = new FileInputStream(filename)) {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in, sha);
            while (din.read() != -1) ;
            din.close();
            byte[] digest = sha.digest();

            StringBuilder result = new StringBuilder(filename);
            result.append(": ");
            result.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
