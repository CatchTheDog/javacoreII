package com.java.core.remoteprocedurecall.rmi;

/**
 * 非远程对象使用序列化副本在虚拟机之间传递
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/16 09:59
 */
public class Product {
    private String description;
    private double price;
    private Warehouse location;

    public Product(String description, double price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public void setLocation(Warehouse location) {
        this.location = location;
    }
}
