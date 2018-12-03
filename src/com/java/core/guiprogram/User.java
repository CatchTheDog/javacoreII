package com.java.core.guiprogram;

public class User {
    private String text;
    private char[] password;
    private String name;

    public User(String text, char[] password) {
        this.text = text;
        this.password = password;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
