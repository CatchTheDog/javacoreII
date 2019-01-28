package com.java.core.scriptengine;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * java脚本引擎测试
 *
 * @author Mr.X
 * @version 1.0.0
 * @since 2019/01/28 17:35
 */
public class ScriptEngineTest {
    public static void main(String[] args) throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        System.out.println(engine);
        String s = "122";
        engine.eval("var x = parseInt(" + s + ");");
        System.out.println(engine.get("x"));
    }
}
