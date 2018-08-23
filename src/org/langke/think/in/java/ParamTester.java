package org.langke.think.in.java;

public class ParamTester{
    public static void main(String args[])
    {
        int a = 10;
        int b = 12;
        int c = add(a,b);
        System.out.println("c = " + c + " a = " + a + " b = " + b);
        StringBuilder str = new StringBuilder("TestParam");
        int d = change(str);
        change2(str);
        System.out.println("str = " + str + " d = " + d);
    }
    public static int add(int a,int b)
    {
        int c = a + b;
        a = 13;
        b = 14;
        return c;
    }
    public static int change(StringBuilder c)
    {
        int strLength = c.length();
        c = new StringBuilder("Hello Tester");
        return strLength;
    } 
    public static int change2(StringBuilder c){
    	c.append("Keeter");
    	return c.length();
    }
}

