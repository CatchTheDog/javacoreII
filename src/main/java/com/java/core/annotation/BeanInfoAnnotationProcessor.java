package com.java.core.annotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.beans.Introspector;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 对于在源码中存在的注解，使用注解处理器在编译时处理
 * 注解处理器通常通过扩展AbstractProcessor类实现了Processor接口，使用@SupportedAnnotationTypes 指定支持哪种注解（支持通配符“com.horstmann.*”），
 * 使用@SupportedSourceVersion注解注明支持哪个版本
 * <p>
 * process方法有两个参数：一个是在本次循环中要进行处理的注解集合，零个是包含有管当前处理循环信息的RoundEnv引用
 * <p>
 * 使用JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(beanClassName + "BeanInfo");
 * PrintWriter out = new PrintWriter(sourceFile.openWriter());
 * 生成源文件。
 * AbstractProcess 类有一个受保护的域processingEnv，用于访问各种处理服务。Filer接口负责创建新文件并追踪它们。
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/02/28 15:57
 */
@SupportedAnnotationTypes("com.java.core.annotation.Property")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BeanInfoAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement typeElement : annotations) {
            Map<String, Property> props = new LinkedHashMap<>();
            String beanClassName = null;
            for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
                String mname = element.getSimpleName().toString();
                String[] prefixes = {"get", "set", "is"};
                boolean found = false;
                for (int i = 0; !found && i < prefixes.length; i++) {
                    if (mname.startsWith(prefixes[i])) {
                        found = true;
                        int start = prefixes[i].length();
                        String name = Introspector.decapitalize(mname.substring(start));
                        props.put(name, element.getAnnotation(Property.class));
                    }
                    if (!found)
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "@Property must be applied to getXxx,setXxx or isXxx method", element);
                    else if (beanClassName == null)
                        beanClassName = ((TypeElement) element.getEnclosedElements()).getQualifiedName().toString();
                }
                try {
                    if (beanClassName != null) writeBeanInfoFile(beanClassName, props);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private void writeBeanInfoFile(String beanClassName, Map<String, Property> props) throws IOException {
        JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(beanClassName + "BeanInfo");
        PrintWriter out = new PrintWriter(sourceFile.openWriter());
        int i = beanClassName.lastIndexOf(".");
        if (i > 0) {
            out.print("package ");
            out.print(beanClassName.substring(0, i));
            out.print(";");
        }
        out.print("public class ");
        out.print(beanClassName.substring(i + 1));
        out.println("BeanInfo extends java.beans.SimpleBeanInfo");
        out.println("{");
        out.println("    public java.beans.PropertyDescriptor [] getPropertyDescriptors()");
        out.println("    {");
        out.println("      try");
        out.println("      {");
        for (Map.Entry<String, Property> entry : props.entrySet()) {
            out.print("        java.beans.PropertyDescrptor ");
            out.print(entry.getKey());
            out.println("Descriptor");
            out.print("        = new java.beans.PropertyDescriptor(\"");
            out.print(entry.getKey());
            out.print("\",");
            out.print(beanClassName);
            out.println(".class");
            String ed = entry.getValue().editor();
            if (!ed.equals("")) {
                out.print("        ");
                out.print(entry.getKey());
                out.print("Descriptor.setPropertyEditorClass(");
                out.print(ed);
                out.println(".class");
            }
            out.println("        return new java.beans.PropertyDescriptor []");
            out.print("        {");
            boolean first = true;
            for (String p : props.keySet()) {
                if (first) first = false;
                else out.print(",");
                out.println();
                out.print("              ");
                out.print(p);
                out.print("Descriptor");
            }
            out.println();
            out.println("        };");
            out.println("      }");
            out.println("     catch(java.beans.IntrospectionException e)");
            out.println("     {");
            out.println("        e.printStackTrace();");
            out.println("        return null;");
            out.println("     }");
            out.println("    }");
            out.println("}");
            out.close();
        }
    }
}
