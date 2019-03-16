package com.java.core.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 生成流
 */
public class GenerateStreams {
    @Test
    public void generateStreams() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "jkl");
        strings.stream().filter(string -> !string.isEmpty()).forEach(string -> System.out.println(string));
        System.out.println("=====================");
        strings.parallelStream().filter(string -> string.length() > 2).filter(string -> string.startsWith("a")).forEach(System.out::println);
        System.out.println("=====================");
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
    }
}
