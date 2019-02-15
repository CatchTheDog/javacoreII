package com.java.core.remoteprocedurecall.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The remote interface for a simple warehouse.
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/15 13:47
 */
public interface Warehouse extends Remote {
    double getPrice(String description) throws RemoteException;
}
