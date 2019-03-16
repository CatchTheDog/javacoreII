package com.java.core.remoteprocedurecall.rmi;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.util.Enumeration;

public class WarehouseClient {
    public static void main(String[] args) throws NamingException, RemoteException {
        Context namingContext = new InitialContext();

        System.out.println("RMI registry bindings...");
        Enumeration<NameClassPair> enumeration = namingContext.list("rmi://localhost/");
        while (enumeration.hasMoreElements()) {
            System.out.println(enumeration.nextElement().getName());
        }
        String url = "rmi://localhost/central_warehouse";
        Warehouse centralWarehouse = (Warehouse) namingContext.lookup(url);
        String desc = "测试商品1";
        double price = centralWarehouse.getPrice(desc);
        System.out.println(desc + " : " + price);
    }
}
