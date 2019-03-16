package com.java.core.proxy;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ProxyTest {

    public static void main(String[] args) {
        Object[] strs = new Object[1000];
        for (int i = 0; i < 1000; i++) strs[i] = new TraceHandler("xyz").getProxy();
        int result = Arrays.binarySearch(strs, "mab");
        if (result > 0) System.out.println(strs[result]);
    }
}

class TraceHandler implements InvocationHandler {

    private Object target;

    public TraceHandler(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> cl = proxy.getClass();
        detectClass(cl);
        System.out.print(target);
        System.out.print("." + method.getName() + "(");
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                System.out.print(args[i]);
                if (i < args.length - 1) System.out.print(", ");
            }
        }
        System.out.println(")");
        return method.invoke(target, args);
    }

    /**
     * 查看类内部方法，实例
     *
     * @param cl
     */
    public void detectClass(Class<?> cl) {
        Package pk = cl.getPackage();
        //System.out.println("package:"+pk.getName()); 代理类不属于任何包
        Class superClass = cl.getSuperclass();
        System.out.println(cl.getName().concat(":"));
        System.out.println("\t".concat("superClasss:").concat(superClass.getName()));
        Class<?>[] interfaces = cl.getInterfaces();
        for (Class<?> c : interfaces) System.out.println("\t".concat("interface:").concat(c.getName()));
        Method[] methods = cl.getDeclaredMethods();
        for (Method method1 : methods) System.out.println("\t".concat("method:").concat(method1.getName()));
        Field[] fields = cl.getDeclaredFields();
        for (Field field : fields) System.out.println("\t".concat("field:").concat(field.getName()));
    }

    /**
     * 生成代理,代理类包含方法：指定接口的方法+Object类方法
     * 指定接口作用：接口必须是target类实现的接口
     *
     * @return 返回代理类对象
     */
    public Object getProxy() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces(); //代理实现目标实现的所有接口
        //Class<?> [] interfaces = {Comparable.class};
        return Proxy.newProxyInstance(loader, interfaces, this);
    }
}
