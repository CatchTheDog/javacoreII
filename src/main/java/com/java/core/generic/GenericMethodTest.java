package com.java.core.generic;


import java.time.LocalDate;

/**
 * 在类型擦除之后，多态方法调用时父类泛型方法与子类方法产生冲突，为了调用到正确的多态方法，
 * 编译器会生成桥方法用于解决此问题
 */
public class GenericMethodTest {
    public static void main(String[] args) {
        new GenericMethodTest().test();
    }

    public void test() {
        LocalDate localDate = LocalDate.now();
        Pair<LocalDate> pair = new DateInterval();
        pair.setFirst(LocalDate.now());
        //这里希望调用 DateInterval 的setSecond方法
        pair.setSecond(localDate);
    }

    class Pair<T> {
        private T first;
        private T second;

        public Pair() {
        }

        public Pair(T first, T second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public void setFirst(T first) {
            this.first = first;
        }

        public T getSecond() {
            return second;
        }

        public void setSecond(T second) {
            this.second = second;
        }
    }

    class DateInterval extends Pair<LocalDate> {
        public void setSecond(LocalDate second) {
            if (second.compareTo(getFirst()) >= 0) {
                super.setSecond(second);
            }
        }
    }

}
