package org.langke.util.js;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class RhinoJavaScript {

    public static void main(String[] args) throws ScriptException {

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        String script = "var form = new Array();";
        script += "form.push([\"Card\",\"A0123\"]);";
        script += "form.push([\"sex\",\"2\"]);";
        script += "form.push([\"No\",\"NO101\"]);";
        script += "JSON.stringify(form);";
        String str1 = (String) engine.eval(script);//执行javascript
        System.out.println(str1);
        String str2 = (String) engine.eval("escape("+str1+")");//执行javascript
        System.out.println(str2);
    }

}
