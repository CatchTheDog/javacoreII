package com.java.core.scriptengine;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

/**
 * java脚本引擎测试
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/01/28 17:35
 */
public class ScriptEngineTest {
    public static void main(String[] args) throws ScriptException {
        enumrateScriptEngine();
        runScriptWithEngine();
    }

    /**
     * 枚举虚拟机支持的脚本引擎
     */
    public static void enumrateScriptEngine() {
        List<ScriptEngineFactory> factories = new ScriptEngineManager().getEngineFactories();
        for (ScriptEngineFactory factory : factories) {
            System.out.println(factory.getEngineName());
            System.out.println(factory.getEngineVersion());
            System.out.println(factory.getLanguageName());
            System.out.println(factory.getLanguageVersion());
            factory.getExtensions().forEach(element -> System.out.println(element));
            factory.getMimeTypes().forEach(element -> System.out.println(element));
            ScriptEngine engine = factory.getScriptEngine();
        }
    }

    /**
     * 使用脚本引擎执行脚本
     */
    public static void runScriptWithEngine() throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        System.out.println(engine);
        String s = "122";
        engine.eval("var x = parseInt(" + s + ");");
        System.out.println(engine.get("x"));
    }
}
