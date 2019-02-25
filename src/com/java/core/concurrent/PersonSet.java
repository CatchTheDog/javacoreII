package com.java.core.concurrent;

import java.util.HashSet;
import java.util.Set;

/** 使用同步，不可变类实现线程安全性
 * add() containsPerson() 方法具有后验依赖，所以需要同步处理
 * 前置依赖，后验依赖，不可变性约定 是线程安全的要求所在。
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/23 15:59
 */
public class PersonSet {
    private final Set<Person> mySet = new HashSet();

    public synchronized void addPerson(Person person) {
        mySet.add(person);
    }

    public synchronized boolean containsPerson(Person person) {
        return mySet.contains(person);
    }

    /**
     * 内置不可变类 PersonSet线程安全的前提是Person类是线程安全的，其他线程持有Person类的引用后对Person类做改动会引起多线程安全问题。
     */
    public final class Person {
        private final String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
