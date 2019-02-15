package com.java.core.remoteprocedurecall.rmi;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.util.Enumeration;

/**
 * 自举注册服务：将远程对象注册到虚拟机中。客户端通过请求获取该对象的存根已发起请求。
 * RMI url格式：以rmi:开头，后接服务器已经一个可选的端口号，接着是远程对象的名字，默认情况下，主机名是localhost,端口号是1099
 * rmi://regserver.mycompany.com:99/central_warehouse
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/15 14:07
 */
public class WarehouseServer {
    public static void main(String[] args) throws RemoteException, NamingException {
        System.out.println("Constructing server implementation...");
        WarehouseImpl centralWarehouse = new WarehouseImpl();

        System.out.println("Binding server implementation to registry...");
        Context namingContext = new InitialContext();
        namingContext.bind("rmi:central_warehouse/", centralWarehouse);

        System.out.println("Waiting for invocations from clients...");

        //枚举所有已经注册的远程对象
        Enumeration<NameClassPair> enumeration = namingContext.list("rmi://localhost");
        while (enumeration.hasMoreElements()) {
            System.out.println(enumeration.nextElement().getName());
        }
    }
}
