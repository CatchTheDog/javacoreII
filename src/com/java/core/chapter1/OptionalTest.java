package com.java.core.chapter1;

import java.util.Optional;

public class OptionalTest {

    public static void main(String [] args){
        OptionalTest optionalTest = new OptionalTest();
        Integer value1 = null;
        Integer value2 = new Integer(10);

        Optional<Integer> a = Optional.ofNullable(value1);
        Optional<Integer> b = Optional.of(value2);

        System.out.println(optionalTest.sum(a,b));
    }

    public Integer sum(Optional<Integer> a,Optional<Integer> b){
        System.out.println("第一个参数数值是否存在："+a.isPresent());
        System.out.println("第二个参数数值是否存在："+b.isPresent());

        a.ifPresent(value ->{
            System.out.println("a存在，则打印:"+value);
        });
        b.ifPresent(value -> {
            System.out.println("b存在，则打印："+value);
        });
        Integer value1 = a.orElse(new Integer(0));
        Integer value2 = b.get();
        return value1 + value2;
    }

}
