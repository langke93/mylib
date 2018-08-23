package org.langke.think.in.java;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class Base{
    public static int a = 10;
    public int b = 20;
    static
    {
        System.out.println("Static Init Base " + a);
        //System.out.println("Null Init " + b);
    }
    public Base()
    {
        System.out.println("Init Base " + this.b);
    }
}
/**
 *一级子类和基类包含的内容一样
 **/
class SuperClass extends Base{
    public static int a1 = getSuperStaticNumber();
    public int b1 = getSuperInstanceNumber();
    public SuperClass()
    {
        System.out.println("Init SuperClass" + this.b1);
    }
    static
    {
        System.out.println("Static Init SuperClass" + a1);
    }
    public static int getSuperStaticNumber()
    {
        System.out.println("Static member init");
        return 100;
    }
    public int getSuperInstanceNumber()
    {
        System.out.println("Instance member init");
        return 200;
    }
}
/**
 *二级子类为测试该代码的驱动类
 */
public class SubClass extends SuperClass{
    public static int a2 = getStaticNumber();
    public int b2 = getInstanceNumber();
    private int c;
    public SubClass()
    {
        System.out.println("Init SubClass " + this.b2);
    }
    public static int getStaticNumber()
    {
        System.out.println("Static member init Sub");
        return 1000;
    }
    public int getInstanceNumber()
    {
        System.out.println("Instance member init Sub");
        return 2000;
    }
    protected class InnerOne
    {
        private int a = 1;
        public void writeLine()
        {
            System.out.println("Hello + " + this.a);
            System.out.println("Hello + " + SubClass.this.a);
            System.out.println("Hello + " + SubClass.this.c);
        }
    }

    public static void main(String args[]) throws Throwable
    {
        SubClass subClass = new SubClass();
        InnerOne innerOne = subClass.new InnerOne();
        innerOne.writeLine();
        StringBuilder sb = new StringBuilder("Main");
        int length = sb.length();
        String str = "H Str";
        subClass.change(sb,length,str);
        System.out.println(sb+""+length+str);
        subClass.finalize();
    }
    static
    {
        System.out.println("Static Init " + a2);
    }
    
    public static int change(StringBuilder c,int length,String str)
    {
        int strLength = c.length();
        str = str.concat("123");
        c.append(" HelloKitty");
        length = c.length();
        c = new StringBuilder("Hello Tester");
        System.out.println(c);
        return strLength;
    } 
    @Override
    protected void finalize()  {
    	b2 = 0;
    	System.out.println("runner finalize");
    	try {
			super.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
    }
    
    public static void readFile(String path) throws IOException
    {
        File file = new File(path);
        try{
        FileReader reader = new FileReader(file);
        throw new IOException();
        }finally{
        	
        }
    }

}