package com.java.core.annotation;


import org.apache.bcel.Repository;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.ElementValuePair;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;

import java.io.File;
import java.io.IOException;

/**
 * BCEL 字节码工程向源代码中插入代码
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/3/16 15:07
 */
public class EntryLogger {
    private ClassGen cg;
    private ConstantPoolGen cpg;

    public EntryLogger(ClassGen cg) {
        this.cg = cg;
        this.cpg = cg.getConstantPool();
    }

    public static void main(String[] args) {
        try {
            JavaClass jc = Repository.lookupClass(Item.class.getName());
            ClassGen cg = new ClassGen(jc);
            EntryLogger el = new EntryLogger(cg);
            el.convert();
            File file = new File(Repository.lookupClassFile(cg.getClassName()).getPath());
            cg.getJavaClass().dump(file.getPath());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void convert() {
        for (Method method : cg.getMethods()) {
            AnnotationEntry[] annotationEntries = method.getAnnotationEntries();
            for (AnnotationEntry entry : annotationEntries) {
                if (entry.getAnnotationType().equals("LLogEntry;")) {
                    for (ElementValuePair pair : entry.getElementValuePairs()) {
                        if (pair.getNameString().equals("logger")) {
                            String loggerName = pair.getValue().stringifyValue();
                            cg.replaceMethod(method, insertLogEntry(method, loggerName));
                        }
                    }
                }
            }
        }
    }

    /**
     * 向class文件的方法中插入代码
     *
     * @param method
     * @param loggerName
     * @return
     */
    private Method insertLogEntry(Method method, String loggerName) {
        MethodGen mg = new MethodGen(method, cg.getClassName(), cpg);
        String className = cg.getClassName();
        String methodName = mg.getMethod().getName();
        System.out.printf("Adding logging instructions to %s.%s%n", className, methodName);

        int getLoggerIndex = cpg.addMethodref("java.util.logging.Logger", "getLogger",
                "(Ljava/lang/String;)Ljava/util/logging/Logger;");
        int enteringIndex = cpg.addMethodref("java.util.logging.Logger", "entering",
                "(Ljava/lang/String;Ljava/lang/String;)V");
        InstructionList il = mg.getInstructionList();
        InstructionList patch = new InstructionList();
        patch.append(new PUSH(cpg, loggerName));
        patch.append(new INVOKEDYNAMIC(getLoggerIndex));
        patch.append(new PUSH(cpg, className));
        patch.append(new PUSH(cpg, methodName));
        patch.append(new INVOKEDYNAMIC(enteringIndex));
        InstructionHandle[] ihs = il.getInstructionHandles();
        il.insert(ihs[0], patch);

        mg.setMaxStack();
        return mg.getMethod();
    }
}
