package org.langke.interview;

 class Extends {
	public static void main(String args[]){
		D d = new D();
		d.main(args);
	}
}
/*Extends*/
class A{void m1(A a){System.out.print("A");}}
class B extends A{void m1(B b){System.out.print("B");}}
class C extends B{void m1(C c){System.out.print("C");}}
class D extends C{
	void m1(D d){System.out.print("D");}
	public static void main(String args[]){
		A a= new A();
		B b = new B();
		C c = new C();
		D d = new D();
		d.m1(a);
		d.m1(d);
	}
}
/*Extends*/
/*Thread*/
class PingPong2{
	synchronized void hit (long n){
		for(int i=1;i<3;i++){
			System.out.print(n+"-"+i+" ");
		}
	}
}
class Tester implements Runnable{
	static PingPong2 pp2 = new PingPong2();
	public static void main(String []args){
		new Thread(new Tester()).start();
		new Thread(new Tester()).start();
	}
	public void run(){
		pp2.hit(Thread.currentThread().getId());
	}
}
/*Thread*/
/*Structure*/
class Atom{
	Atom(){System.out.print("atom ");}
}
class Rock extends Atom{
	Rock(String type){System.out.print(type);}
}
class Mountain extends Rock{

	Mountain() {
		super("granite ");
		new Rock("granite ");
	}
	public static void main(String [] a){new Mountain();}
}
/*Structure*/
/*Pass by value*/
class test{
	public static void add3(Integer i){
		int val = i.intValue();
		val += 3;
		i = new Integer(val);
	}
	public static void main(String args[]){
		Integer i = new Integer(0);
		add3(i);
		System.out.println(i);
	}
}
/*Pass by value*/