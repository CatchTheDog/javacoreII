package com.java.core.remoteprocedurecall.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is the implementation for the remote Warehouse interface.
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/15 13:51
 */
public class WarehouseImpl extends UnicastRemoteObject implements Warehouse {
    private Map<String, Product> products;
    private Warehouse backup;

    public WarehouseImpl(Warehouse backup) throws RemoteException {
        products = new HashMap<>();
        this.backup = backup;
    }

    @Override
    public double getPrice(String description) throws RemoteException {
        for (Product product : products.values()) {
            if (product.getDescription().equals(description)) return product.getPrice();
        }
        if (backup == null) return 0;
        else return backup.getPrice(description);
    }

    public void add(String keyword, Product product) {
        product.setLocation(this);
        products.put(keyword, product);
    }

    @Override
    public Product getProduct(List<String> keywords) throws RemoteException {
        for (String keyword : keywords) {
            Product product = products.get(keyword);
            if (null != product) return product;
        }
        if (backup != null) return backup.getProduct(keywords);
        else if (products.values().size() > 0) return products.values().iterator().next();
        else return null;
    }
}
