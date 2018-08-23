package org.langke.think.in.java;

import java.util.Date;
import java.util.Properties;
/**
 * 
 * @author lizz
 * @version 1.0.0
 * @see java.util
 */
public class Property {
	public static void main(String args[]){
		//
		System.out.print(new Date());
		Properties p = System.getProperties();
		p.list(System.out);
		System.out.print("Memory Usage:");
		Runtime rt = Runtime.getRuntime();
		System.out.println("Total Memory="+rt.totalMemory()+" FreeMemory="+rt.freeMemory());
		try{
			Thread.currentThread().sleep(1*1000);
		}catch(InterruptedException  e){
			
		}
		/**
		 * 
		 */
		int a=2,b=3;
		a=a+b;
		b=a-b;
		a=a-b;
		System.out.println("a="+a+"b="+b);
		
	}
}
