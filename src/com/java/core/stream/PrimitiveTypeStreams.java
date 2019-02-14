package com.java.core.stream;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * 为了避免对原始类型进行装箱拆箱，java提供了原始类型的流
 */
public class PrimitiveTypeStreams {
    public static void show(String title, IntStream stream){
        final int SIZE = 10;
        int [] firstElement = stream.limit(SIZE).toArray();
        System.out.println(title + " : ");
        int [] index = {0};
        Arrays.stream(firstElement).forEach(element->{
            if(index[0] < firstElement.length-1)
                System.out.print(element + " , ");
            else
                System.out.print(element);
            index[0] ++;
        });
        System.out.println();
    }

    public static void main(String [] args){
        IntStream is1 = IntStream.generate(()->(int)(Math.random()*100));
        show("is1",is1);
        IntStream is2 = IntStream.range(2,5);
        show("is2",is2);
        IntStream is3 = IntStream.rangeClosed(2,5);
        show("is3",is3);
    }

}


