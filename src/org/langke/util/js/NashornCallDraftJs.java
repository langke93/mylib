package org.langke.util.js;

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
 * @date 2018/9/3 Method Code Too Large 错误，JDK版本升级为jdk1.8.0_192之后正常
 * @date 2018/9/3  Expected an operand but found const/ TypeError: bad map 增加启动参数：java -Dnashorn.args=--language=es6 Main
 * javax.script.ScriptException: SyntaxError: WebRoot/WEB-INF/classes/org/langke/util/js/draft-js-demo.js:2:7 Expected ident but found {

 *  最后：ES6 destructuring is not yet implemented 卒
 *  
 */
public class NashornCallDraftJs {

    public static void main(String[] args) {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        //加载并执行javascript脚本
        try{
            engine.eval("load('https://cdnjs.cloudflare.com/ajax/libs/react/15.3.1/react.js')");
            engine.eval("load('https://cdnjs.cloudflare.com/ajax/libs/react/15.3.1/react-dom.js')");
            engine.eval("load('https://cdnjs.cloudflare.com/ajax/libs/immutable/3.8.2/immutable.js')");
            engine.eval("load('https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.23/browser.min.js')");
            //engine.eval("load('https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.27.1/es6-shim.js')"); 
            engine.eval("load('https://cdn.bootcss.com/babel-standalone/6.26.0/babel.min.js')");
            engine.eval("load('https://cdnjs.cloudflare.com/ajax/libs/draft-js/0.10.5/Draft.js')");
            engine.eval("load('https://cdnjs.cloudflare.com/ajax/libs/html-minifier/3.3.3/htmlminifier.js')");
            engine.eval("load('WebRoot/WEB-INF/classes/org/langke/util/js/nashorn-polyfill.js')");
            engine.eval("load('WebRoot/WEB-INF/classes/org/langke/util/js/draft-js-demo.js')");
            
            Invocable invocable = (Invocable) engine;
            //调用javascript函数
            Object result = invocable.invokeFunction("fun1", "Peter Parker");
            
            System.out.println(result);
            System.out.println(result.getClass());
            
            invocable.invokeFunction("fun1", new Date()); 
        }catch(ScriptException  |NoSuchMethodException e   ){
            e.printStackTrace();
        } 
    }

}
