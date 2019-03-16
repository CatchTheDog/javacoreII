package com.java.core.scriptengine;


import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Java 编译器调用
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/3/16 14:35
 */
public class JavaCompilerTest {
    public static void main(String[] args) {
        compilerTest();
    }

    public static void compilerTest() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        OutputStream outputStream = new PrintStream(System.out);
        OutputStream errorOutputStream = new PrintStream(System.err);
        int result = compiler.run(null, outputStream, errorOutputStream, "-sourcepath", "src", "../ScriptEngineTest.java");
    }
}
