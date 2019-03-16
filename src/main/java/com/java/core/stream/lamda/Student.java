package com.java.core.stream.lamda;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * lamda表达式测试
 * 方法引用
 */
public class Student {
    private String name;
    private Double score;

    public Student(String name, Double score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }

    @Test
    public void test1() {
        List<Student> students = new ArrayList<Student>() {
            {
                add(new Student("stu1", 100.0));
                add(new Student("stu2", 99.1));
                add(new Student("stu3", 99.5));
                add(new Student("stu4", 101.1));
            }
        };
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Double.compare(o1.getScore(), o2.getScore());
            }
        });
        System.out.println(students);
    }

    @Test
    public void test2() {
        List<Student> students = new ArrayList<Student>() {
            {
                add(new Student("stu1", 100.0));
                add(new Student("stu2", 99.1));
                add(new Student("stu3", 99.5));
                add(new Student("stu4", 101.1));
            }
        };
        Collections.sort(students, (s1, s2) -> Double.compare(s1.getScore(), s2.getScore()));
        System.out.println(students);
    }
}
