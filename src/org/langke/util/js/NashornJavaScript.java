package org.langke.util.js;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Date;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
/**
 * 
 * @author langke
 * https://blog.csdn.net/sunning9001/article/details/52354454
 * http://www.runoob.com/java/java8-nashorn-javascript.html
 *
 */
public class NashornJavaScript {
    public static void main(String[] args) throws ScriptException, FileNotFoundException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        //加载并执行javascript脚本
        engine.eval(new FileReader("WebRoot/WEB-INF/classes/org/langke/util/js/nashorn1.js"));
        
        Invocable invocable = (Invocable) engine;
        //调用javascript函数
        Object result = invocable.invokeFunction("fun1", "Peter Parker");
        System.out.println(result);
        System.out.println(result.getClass());
        
        invocable.invokeFunction("fun2", new Date());
        invocable.invokeFunction("fun2", LocalDateTime.now());
        String testConst1 = (String) "const pi = 3.14;";
        String testPrint1 = (String) "function hello(name) {print ('Hello, ' + name +' = '+ pi);}";
        engine.eval(testConst1);
        engine.eval(testPrint1);

        Invocable inv = (Invocable) engine;
        inv.invokeFunction("hello", "pi");
       // invocable.invokeFunction("fun2", new Person());
      
    }
    
    public void testNashorn(String[] args){
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        try {
            args =new String[1];
            args[0] = "WebRoot/WEB-INF/classes/org/langke/util/js/make.js";
            for (int i = 0; i < args.length; i++) {
                File file = new File(args[i]).getAbsoluteFile();
                System.out.println("args[" + i + "] is" + file);
                if (file.isFile()) {
                    String strJSName = args[i];// For Jar
                    nashorn.eval(new FileReader(strJSName));
                    Object eval = nashorn.eval("make('" + strJSName + "')");// 传递参数strJSName到JS脚本
                    System.out.println(eval);
                } else {
                    System.out.println("Java运行命令参数非法，请检查！");
                }
            }
        } catch (ScriptException | FileNotFoundException e) {
            System.out.println("Error executing script: " + e.getMessage());
        }
    }
}
