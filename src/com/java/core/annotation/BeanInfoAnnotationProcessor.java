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
    }
}
