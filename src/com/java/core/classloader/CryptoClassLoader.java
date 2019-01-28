package com.java.core.classloader;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 实现自定义类加载器，对使用Caesar加密的字节码文件进行解密并加载
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/01/28 14:09
 */
public class CryptoClassLoader extends ClassLoader {
    /**
     * 字节码解密密钥
     */
    private int key;

    public CryptoClassLoader(int k) {
        this.key = k;
    }

    /**
     * 覆写此方法，以实现自己的类加载器
     *
     * @param name 字节码文件名——类名字
     * @return 类对象
     * @throws ClassNotFoundException 字节码文件未找到或发生IO异常时抛出
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classBytes;
        try {
            classBytes = loadClassBytes(name);
        } catch (IOException e) {
            ClassNotFoundException throwable = new ClassNotFoundException();
            throwable.initCause(e.getCause());
            throw throwable;
        }
        Class<?> cl = defineClass(name, classBytes, 0, classBytes.length); //defineClass向虚拟机提供字节码
        if (cl == null) throw new ClassNotFoundException(name);
        return cl;
    }

    /**
     * 从字节码源加载字节码
     *
     * @param name 源文件名称
     * @return 字节码字节数组
     * @throws IOException 字节码源文件未找到或IO异常时抛出
     */
    private byte[] loadClassBytes(String name) throws IOException {
        String cname = name.replace(".", "/") + ".caesar";
        try (FileInputStream inputStream = new FileInputStream(cname)) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int ch;
            while ((ch = inputStream.read()) != -1) {
                byte b = (byte) (ch - key);
                buffer.write(b);
            }
            return buffer.toByteArray();
        }
    }
}
