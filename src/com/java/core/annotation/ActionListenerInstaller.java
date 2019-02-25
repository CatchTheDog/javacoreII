package com.java.core.annotation;

import java.awt.event.ActionListener;
import java.lang.reflect.*;

/**
 * 注解处理器:使用反射获取被注解的方法、类或者域
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/25 15:55
 */
@BugReports({@BugReport(showStopper = true, reportBy = "majq"), @BugReport(reportBy = "majq")})
public class ActionListenerInstaller {
    /**
     * 注解处理器
     *
     * @param obj 待处理对象
     */
    @BugReports({@BugReport(reportBy = "majq")})
    public static void processAnnotations(Object obj) {
        try {
            Class<?> cl = obj.getClass();
            for (Method method : cl.getDeclaredMethods()) {
                ActionListenerFor a = method.getAnnotation(ActionListenerFor.class);
                if (null != a) {
                    Field field = cl.getDeclaredField(a.source());
                    field.setAccessible(true);
                    addListener(field.get(obj), obj, method);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用代理实现监听方法添加
     *
     * @param source
     * @param param
     * @param m
     */
    public static void addListener(Object source, final Object param, final Method m) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        InvocationHandler handler = (proxy, method, args) -> m.invoke(param);
        Object listener = Proxy.newProxyInstance(null, new Class[]{ActionListener.class}, handler);
        Method adder = source.getClass().getMethod("addActionListener", ActionListener.class);
        adder.invoke(source, listener);
    }
}
