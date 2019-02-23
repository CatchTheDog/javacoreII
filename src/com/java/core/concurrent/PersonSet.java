package com.java.core.concurrent;

import java.util.HashSet;
import java.util.Set;

/**
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

    private class Person {

    }
}
