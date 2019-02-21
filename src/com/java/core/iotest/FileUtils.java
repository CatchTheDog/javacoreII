package com.java.core.iotest;


import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 文件管理工具类
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/21 16:17
 */
public class FileUtils {
    public static void main(String[] args) throws IOException {
//        File file = new File("C:\\马俊强\\软件安装");
//        System.out.println("总空间：" + file.getTotalSpace() / 1024 / 1024 / 1024 + "G");
//        System.out.println("未分配空间：" + file.getFreeSpace() / 1024 / 1024 / 1024 + "G");
//        System.out.println("可用空间：" + file.getUsableSpace() / 1024 / 1024 / 1024 + "G");
//        File[] files = File.listRoots();
//        Arrays.stream(files).forEach(x -> System.out.println(x.getAbsolutePath()));
//        createFileByPath("C:\\马俊强\\学习资料\\testcreate.txt",false);
//        System.out.println(getRelativePathBetweenFiles("C:\\马俊强\\学习资料\\majunqiangunzip\\需求\\edr需求开发\\ndm_model", "C:\\马俊强\\软件安装\\7zip\\7zip\\7-Zip\\Lang"));
//        System.out.println(countSubstrAppearTimes("aaaaaaaaaaaaa", "aa"));
        listAllFile("C:\\马俊强\\学习资料\\需求", null, 0);
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
     */
    public static String getRelativePathBetweenFiles(String referenceFilePath, String comparedFilePath) {
        if (null == referenceFilePath || referenceFilePath.length() < 1 || null == comparedFilePath || comparedFilePath.length() < 1)
            throw new IllegalArgumentException("args is null.");
        if (comparedFilePath.indexOf(referenceFilePath) > -1) {
            return comparedFilePath.substring(referenceFilePath.length());
        } else {
            String longestSubStr = longestSubStr(referenceFilePath, comparedFilePath);
            if (longestSubStr.length() > 0) {
                int num = countSubstrAppearTimes(referenceFilePath.substring(longestSubStr.length()), File.separator);
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i <= num; i++) buffer.append("..").append(File.separator);
                buffer.append(comparedFilePath.substring(longestSubStr.length()));
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

    /**
     * 统计子字符串在字符串中出现的次数
     *
     * @param str    待检测字符串
     * @param subStr 待检测子字符串
     * @return 子字符串在字符串中出现的次数
     */
    public static int countSubstrAppearTimes(String str, String subStr) {
        if (null == str || null == subStr) throw new IllegalArgumentException("str,subStr can't be null.");
        if (str.indexOf(subStr) < 0) return 0;
        int index, count;
        for (index = 0, count = 0; (index = str.indexOf(subStr, index)) > -1; count++, index++) ;
        return count;
    }

    /**
     * 打印文件系统结构树
     *
     * @param dirPath    文件路径
     * @param fileFilter 过滤器
     * @param depth      相对根路径深度
     * @throws IOException
     */
    public static void listAllFile(String dirPath, FileFilter fileFilter, int depth) throws IOException {
        if (null == dirPath || dirPath.length() < 1) throw new IllegalArgumentException("dirpath can't be null.");
        File file = new File(dirPath);
        if (!file.exists()) throw new FileNotFoundException("dirpath file not found.");
        depth++;
        for (int i = 0; i < depth; i++) System.out.print("  ");
        System.out.println("|");
        for (int i = 0; i < depth; i++) System.out.print("  ");
        System.out.println(file.getName());
        File[] files;
        if (null == fileFilter) files = file.listFiles();
        else files = file.listFiles(fileFilter);
        if (null != files && files.length > 0) {
            for (File tempFile : files) {
                if (file.isDirectory()) listAllFile(tempFile.getCanonicalPath(), fileFilter, depth);
                else System.out.println(tempFile.getCanonicalPath());
            }
        }
    }
}
