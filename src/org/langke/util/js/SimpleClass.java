package org.langke.util.js;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimpleClass extends BaseTest{

	//@Test
	public static void main(String[] args) {
		SimpleClass simpleClass = new SimpleClass();
		try {
			simpleClass.prepareEngine();
			simpleClass.readSimpleClassFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 public void readSimpleClassFile() throws Exception {
        //nashorn.eval(getScript("WebRoot/WEB-INF/classes/org/langke/util/js/hello-world.js"));
	    nashorn.eval("load('WebRoot/WEB-INF/classes/org/langke/util/js/hello-world.js')");
        nashorn.eval("var greeter = new HelloWorld('Snoop');");
        System.out.println(nashorn.eval("greeter.greet();"));
    }

}
