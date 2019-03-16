package com.java.core.annotation;

/**
 * 源码级注解处理器测试bean类
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/3/1 9:32
 */
public class AnnotationUserTest {
    private String name;
    private int age;

    @Property(editor = "editor1")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Property(editor = "editor2")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
