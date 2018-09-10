package org.langke.util.js;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.junit.Before;

public class BaseTest {

    protected ScriptEngine nashorn;

    @Before
    public void prepareEngine() throws Exception {
        ScriptEngineManager mgr = new ScriptEngineManager();
        nashorn = mgr.getEngineByName("nashorn");
        nashorn.eval("load('WebRoot/WEB-INF/classes/org/langke/util/js/nashorn-polyfill.js')");
        //nashorn.eval("load('https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.35.3/es6-shim.js')");
        nashorn.eval("load('https://cdnjs.cloudflare.com/ajax/libs/es6-shim/0.30.0/es6-shim.js')");
    }

    protected void readScript(String scriptPath) throws Exception {
        new String(Files.readAllBytes(Paths.get(getClass().getResource(scriptPath).toURI())));
    }

    protected Reader getScript(String path) {
        return new InputStreamReader(BaseTest.class.getClassLoader().getResourceAsStream(path));
    }

}
