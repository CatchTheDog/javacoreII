package com.java.core.iotest;


import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Zip工具实现
 * 后续考虑使用NIO和多线程，提高性能
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/19 16:21
 */
public class ZipUtils {
    /**
     * 生成ZIP压缩文件
     *
     * @param srcFilePath 源文件路径
     * @param zipFilePath zip文件路径
     */
    public static void generateZipCompressedFile(String srcFilePath, String zipFilePath) {
        if (null != srcFilePath && srcFilePath.length() > 0 && null != zipFilePath && zipFilePath.length() > 0) {
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(new File(zipFilePath)))) {
                recursionCompressFile(new File(srcFilePath), zipOutputStream, srcFilePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将某文件夹下所有文件递归打包成zip
     *
     * @param file 待打包文件目录
     * @throws FileNotFoundException 文件未找到时抛出FinleNotFoundException
     */
    private static void recursionCompressFile(File file, ZipOutputStream zipOutputStream, String referenceFilePath) throws IOException {
        if (null == file || !file.exists())
            throw new FileNotFoundException("file not found.file path:" + file.getName());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File tempFile : files) {
                recursionCompressFile(tempFile, zipOutputStream, referenceFilePath);
            }
        } else {
            try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
                ZipEntry entry;
                String relativePath = getRelativePath(new File(referenceFilePath), file);
                entry = new ZipEntry(relativePath);
                zipOutputStream.putNextEntry(entry);
                byte[] bytes = new byte[1024];
                int len;
                while ((len = inputStream.read(bytes)) > -1) {
                    zipOutputStream.write(bytes, 0, len);
                }
                zipOutputStream.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件相对路径
     *
     * @param referenceFile 根路径——参照文件路径
     * @param file          比对文件路径
     */
    public static String getRelativePath(File referenceFile, File file) {
        if (null != referenceFile && null != file) {
            String referenceFilePath = referenceFile.getAbsolutePath();
            String filePath = file.getAbsolutePath();
            int index = filePath.indexOf(referenceFilePath);
            if (index > -1) {
                return referenceFilePath.substring(referenceFilePath.lastIndexOf(File.separator) + 1).concat(File.separator).concat(filePath.substring(index + Long.valueOf(referenceFilePath.length()).intValue() + 1));
            }
        }
        return null;
    }

    /**
     * 解压zip文件到指定路径下
     *
     * @param srcFilePath  源文件路径
     * @param descFilePath 解压后文件存放路径
     */
    public static void unzipFile(String srcFilePath, String descFilePath) throws FileNotFoundException {
        if (null == srcFilePath || srcFilePath.length() < 1 || null == descFilePath || descFilePath.length() < 1)
            throw new IllegalArgumentException("srcFilePath | descFilePath is null.");
        File srcFile = new File(srcFilePath);
        File descFile = new File(descFilePath);
        if (!srcFile.exists()) throw new FileNotFoundException("srcFile not found.");
        if (!descFile.exists()) descFile.mkdirs();
        try (ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(srcFile)))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                String entryName = entry.getName();
                String subPath = entryName.substring(0, entryName.lastIndexOf(File.separator));
                File subPathFile = new File(descFile.getAbsolutePath().concat(File.separator).concat(subPath));
                File tempFile = new File(descFile.getAbsolutePath().concat(File.separator).concat(entry.getName()));
                if (!subPathFile.exists()) subPathFile.mkdirs();
                if (!tempFile.exists()) tempFile.createNewFile();
                try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(tempFile))) {
                    byte[] bytes = new byte[1024];
                    int len;
                    while ((len = zipInputStream.read(bytes)) > -1) {
                        bufferedOutputStream.write(bytes, 0, len);
                    }
                }
                zipInputStream.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取JAR文件
     */
    public static void readJARFile(String srcFilePath) throws FileNotFoundException {
        if (null == srcFilePath || srcFilePath.length() < 1)
            throw new IllegalArgumentException("srcFilePath can't be null.");
        File srcFile = new File(srcFilePath);
        if (!srcFile.exists()) throw new FileNotFoundException("srcFile not exists.");
        try (JarInputStream jarInputStream = new JarInputStream(new BufferedInputStream(new FileInputStream(srcFile)))) {
            Manifest manifest = jarInputStream.getManifest();
            Map<String, Attributes> map = manifest.getEntries();
            for (Iterator<Map.Entry<String, Attributes>> iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
                System.out.println(iterator.next().getKey() + ":" + iterator.next().getValue().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        //unzipFile("C:\\马俊强\\学习资料\\majunqiang.zip", "C:\\马俊强\\学习资料\\majunqiangunzip\\");
        readJARFile("C:\\马俊强\\commons-collections-3.2.1.jar");
    }
}
