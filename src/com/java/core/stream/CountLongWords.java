package com.java.core.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * JDK 8 Stream 使用
 * 流与集合比较：
 * 1.流不存储元素
 * 2.流的操作不会修改其数据源
 * 3.流是惰性操作
 *
 * 流操作的典型流程：
 * 1.创建流；
 * 2.指定将初始流转换为其他流的中间操作
 * 3.应用终止操作，生成结果。
 */
public class CountLongWords {
	public static void main(String [] args) throws IOException {
		//System.out.println(Paths.get("gutenberg/alice30.txt").toAbsolutePath());
		String contents = new String(Files.readAllBytes(Paths.get("gutenberg/alice30.txt")),StandardCharsets.UTF_8);
		List<String> words = Arrays.asList(contents.split("\\PL+"));

		long count = 0;
		//循环迭代
		for(String s : words){
			if(s.length() > 12) count++;
		}
		System.out.println(count);
		//顺序流
		count = words.stream().filter(w -> w.length()>12).count();
		System.out.println(count);
		//并行流
		count = words.parallelStream().filter(w -> w.length()>12).count();
		System.out.println(count);
	}
}
