package com.java.core.guiprogram;

import java.awt.*;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Mr.X
 * @version 1.0.0
 * 利用反射得到GUI应用中生成的每一个AWT事件的记录
 * @since 2018/12/4 8:55
 */
public class EventTrace {
    /**
     * the handler for all event proxies
     */
    private InvocationHandler handler;

    public EventTrace() {
        handler = (proxy, method, args) -> {
            System.out.println(method + ":" + args[0]);
            return null;
        };
    }

    /**
     * Adds event tracers for all events to which this component and its children can listen
     *
     * @param c a component
     */
    public void add(Component c) {
        try {
            BeanInfo info = Introspector.getBeanInfo(c.getClass());
            EventSetDescriptor[] eventSets = info.getEventSetDescriptors();
            for (EventSetDescriptor eventSet : eventSets) {
                addListener(c, eventSet);
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

        if (c instanceof Container) {
            for (Component comp : ((Container) c).getComponents()) {
                this.add(comp);
            }
        }
    }

    /**
     * add a listener to the given event set
     *
     * @param c        a component
     * @param eventSet a descriptor of a listener interface
     */
    public void addListener(Component c, EventSetDescriptor eventSet) {
        Object proxy = Proxy.newProxyInstance(null, new Class[]{eventSet.getListenerType()}, handler);
        Method addListenerMethod = eventSet.getAddListenerMethod();
        try {
            addListenerMethod.invoke(c, proxy);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
