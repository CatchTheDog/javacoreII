package com.java.core.chapter2;

import java.nio.charset.Charset;

public class Test {
    public static void main(String [] args){
        Charset cset = Charset.forName("ISO-8859-1");
        cset.aliases().stream().forEach(x->System.out.println(x));
        cset.availableCharsets().forEach((key,value) ->System.out.println("key:"+key+",value:"+value));
    }
}
