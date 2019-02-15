package com.java.core.remoteprocedurecall.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is the implementation for the remote Warehouse interface.
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/15 13:51
 */
public class WarehouseImpl extends UnicastRemoteObject implements Warehouse {
    private Map<String, Double> prices;

    public WarehouseImpl() throws RemoteException {
        prices = new HashMap<>();
        prices.put("测试商品1", 24.95);
        prices.put("测试商品2", 23.45);
    }

    @Override
    public double getPrice(String description) throws RemoteException {
        Double prirce = prices.get(description);
        return null == prirce ? 0.0 : prirce;
    }
}
