package org.langke.think.in.java;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DumpMethods {
	public static void main(String args[]) {
		String className = "org.langke.think.in.java.ReflectTest";
		if(args.length>0)
			className = args[0];
		try {
			Class<?> c = Class.forName(className);
			Method m[] = c.getDeclaredMethods();	
			ReflectTest rt = (ReflectTest)c.newInstance();
			for (int i = 0; i < m.length; i++){
				m[i].setAccessible(true);
				System.out.println(m[i].toString());		
				System.err.println(m[i].invoke(rt,"executeMethod"));
			}
			Field fields[] = c.getDeclaredFields();
			for(Field field:fields){
				field.setAccessible(true);//原a 变量不可见，设置为可见
				;
				System.err.println(field.get(rt));//这边取出变量值
				System.out.println(field.toString());
			}
		} catch (Throwable e) {
			System.err.println(e);
		}
	}
}
class ReflectTest{
	private String a = "hw";
	static public String b = "ms";
	private static String getX(String x){
		return x;
	}
}