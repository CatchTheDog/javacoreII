package com.java.core.iotest;


import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileTest {
    public static void main(String[] args) throws IOException {
//        File file = new File("C:\\马俊强\\软件安装");
//        System.out.println("总空间：" + file.getTotalSpace() / 1024 / 1024 / 1024 + "G");
//        System.out.println("未分配空间：" + file.getFreeSpace() / 1024 / 1024 / 1024 + "G");
//        System.out.println("可用空间：" + file.getUsableSpace() / 1024 / 1024 / 1024 + "G");
//        File[] files = File.listRoots();
//        Arrays.stream(files).forEach(x -> System.out.println(x.getAbsolutePath()));
//        createFileByPath("C:\\马俊强\\学习资料\\testcreate.txt",false);
        System.out.println(getRelativePathBetweenFiles("C:\\马俊强\\学习资料", "C:\\edrjob\\log"));
    }


    /**
     * 根据路径创建文件（文件夹/文件）
     *
     * @param filePath    文件路径
     * @param isDirectory 标识，指示创建的目录类型(文件|文件夹)
     * @return 是否创建成功
     * @throws IOException
     */
    public static boolean createFileByPath(String filePath, boolean isDirectory) throws IOException {
        if (null == filePath || filePath.length() < 1) throw new IllegalArgumentException("file path can't be null.");
        File file = new File(filePath);
        if (!file.exists()) {
            if (isDirectory) {
                return file.mkdirs();
            } else {
                File parentFile = file.getParentFile();
                boolean isParentFileExist = true;
                if (!parentFile.exists()) {
                    isParentFileExist = parentFile.mkdirs();
                }
                return isParentFileExist && file.createNewFile();
            }
        }
        return true;
    }

    /**
     * 获取两个文件的相对路径,这里需要使用最长公共子串算法对比路径
     *
     * @param referenceFilePath 参照文件路径
     * @param comparedFilePath  比对文件路径
     * @return 比对文件相对于参照文件的相对路径
     * C:\马俊强\学习资料
     * C:\马俊强\学习资料\需求\edr需求开发
     * C:\edrjob\log
     */
    public static String getRelativePathBetweenFiles(String referenceFilePath, String comparedFilePath) {
        if (null == referenceFilePath || referenceFilePath.length() < 1 || null == comparedFilePath || comparedFilePath.length() < 1)
            throw new IllegalArgumentException("args is null.");
        if (comparedFilePath.indexOf(referenceFilePath) > -1) {
            return comparedFilePath.substring(referenceFilePath.length());
        } else {
            String longestSubStr = longestSubStr(referenceFilePath, comparedFilePath);
            if (longestSubStr.length() > 0) {
                Pattern pattern = Pattern.compile("\\\\");
                Matcher matcher = pattern.matcher(referenceFilePath.substring(longestSubStr.length()));
                int num = matcher.groupCount();
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < num; i++) buffer.append("..").append(File.separator);
                buffer.append(comparedFilePath.substring(comparedFilePath.indexOf(File.separator) + 1));
                return buffer.toString();
            }
        }
        return null;
    }

    /**
     * 查找路径字符串最长公共子串
     *
     * @return
     */
    public static String longestSubStr(String referenceFilePath, String comparedFilePath) {
        char[] referenceFilePathArray = referenceFilePath.toCharArray();
        char[] comparedFilePathArray = comparedFilePath.toCharArray();
        int i = 0;
        for (; i < referenceFilePath.length(); i++) {
            if (referenceFilePathArray[i] != comparedFilePathArray[i]) break;
        }
        return new String(referenceFilePathArray, 0, i);
    }


}
