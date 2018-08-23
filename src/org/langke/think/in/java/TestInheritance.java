package org.langke.think.in.java;

/**
 *先定义一个类A
 **/
class A{
     public void method1(){

    	System.out.println(this.getClass()+"method1");
    }
    protected void method2(){

    	System.out.println(this.getClass()+"method2");
    }
    public void method3(){

    	System.out.println(this.getClass()+"method3");
    }
}
/**
 *然后定义一个类B，B类继承于A类，二者是继承关系
 **/
class B extends A{
    public B(){}
    public void method1(){
    	System.out.println(this.getClass()+"B method1");
        super.method2();
    }
    //......
}
/**
 *复合使用过程中的测试代码
 **/
public class TestInheritance{
    public static void main(String args[])
    {
        B b = new B();
        b.method1(); //在此方法的调用中也间接调用了A类的方法method1
        b.method2(); //这里调用的方法是B类从A类继承过来的method2方法，同样可以间接调用
        A a = new A();
        a.method1();
        a.method2();
        A c = new B();
        c.method1();
        c.method2();
    }
}
