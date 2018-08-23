package org.langke.think.in.java;
import java.util.*;
public class PropertiesExample {
	public static void main(String arg[]){
		Properties props = System.getProperties();
		Enumeration prop_names = props.propertyNames();
		while (prop_names.hasMoreElements()){
			String prop_name = (String)prop_names.nextElement();
			String property = props.getProperty(prop_name);
			System.out.println(prop_name+property);
		}
	}
}
