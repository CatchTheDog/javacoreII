package com.java.core.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Scanner;

public class ReflectionTest {
    public static void main(String[] args) {
        String name;
        if (args.length > 0) name = args[0];
        else {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter class name(e.g. java.util.Date):");
            name = in.next();
        }

        try {
            Class c1 = Class.forName(name);
            Class superC1 = c1.getSuperclass();
            String modifiers = Modifier.toString(c1.getModifiers());
            if (modifiers.length() > 0) System.out.println(modifiers + " ");
            System.out.print("class " + name);
            if (superC1 != null && superC1 != Object.class) System.out.print(" extends " + superC1.getName());
            System.out.print("\n{\n");
            printConstructors(c1);
            System.out.println();
            printMethods(c1);
            System.out.println();
            printFields(c1);
            System.out.print("}");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public static void printConstructors(Class c1) {
        Constructor[] constructors = c1.getDeclaredConstructors();
        for (Constructor c : constructors) {
            String name = c.getName();
            System.out.println("  ");
            String modifiers = Modifier.toString(c.getModifiers());
            if (modifiers.length() > 0) System.out.println(modifiers + "   ");
            System.out.print(name + "(");

            Class[] paramTypes = c.getParameterTypes();
            printParamTypes(paramTypes);
            System.out.println(");");
        }
    }

    public static void printMethods(Class c1) {
        Method[] methods = c1.getDeclaredMethods();
        Arrays.stream(methods).forEach(x -> {
            Class retType = x.getReturnType();
            String name = x.getName();
            System.out.print("  ");
            String modifiers = Modifier.toString(x.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.print(retType.getName() + "  " + name + "(");
            Class[] paramTypes = x.getParameterTypes();
            printParamTypes(paramTypes);
            System.out.println(");");
        });
    }

    private static void printParamTypes(Class[] paramTypes) {
        int[] a = {0};
        Arrays.stream(paramTypes).forEach(x -> {
            System.out.print(x.getName());
            if (a[0] < paramTypes.length - 1) System.out.print(", ");
            a[0]++;
        });
    }

    public static void printFields(Class c1) {
        Field[] fields = c1.getDeclaredFields();
        Arrays.stream(fields).forEach(x -> {
            Class type = x.getType();
            String name = x.getName();
            System.out.print("  ");
            String modifiers = Modifier.toString(x.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.println(type.getName() + "   " + name + ";");

        });
    }
}
