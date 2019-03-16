package com.java.core.remoteprocedurecall.rmi;

public class Book extends Product {
    private String isbn;

    public Book(String description, double price, String isbn) {
        super(description, price);
        this.isbn = isbn;
    }

    @Override
    public String getDescription() {
        return super.getDescription().concat(" ").concat(isbn);
    }
}
