package com.java.core.chapter2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String [] args){
        String [] words = {"Hello","world"};
        List<String> a = Arrays.stream(words).map(word -> word.split("")).flatMap(Arrays::stream).collect(Collectors.toList());
        a.forEach(x -> System.out.println(x));
    }
}
