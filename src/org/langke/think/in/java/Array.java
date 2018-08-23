package org.langke.think.in.java;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

class Array {

	static{
		System.out.println("a".equals("a"));
		System.out.println("a"=="a");
		System.out.println("Init static");
	}
	public void linkedList(){
		List<Integer> list = new LinkedList<Integer>();
		for(int i=0;i<10;i++){
			list.add(i);
		}
		System.out.println(list);
		int outer = 6-1;
		while(!list.isEmpty()){
			if(list.size()>outer){
				list.remove(outer);
			}else if(list.size()==1){
				break;
			}else{
				list.remove(outer%list.size());
			}
			System.out.println(list);
		}
	}
	public void array(){

		Integer array[] = {0,1,2,3,4,5,6,7,8,9};
		for(int j=1;j<array.length;j++)
			for(int i=1;i<array.length;i++){
				if(i==3||array[i]!=-1){
					System.out.println(array[i]);
					array[i]=-1;
					break;
				}
			}
	} 
	
	public static void main(String args[]){
		Object obj = new Array();
		((Array) obj).linkedList();
		A1 a =  new B1();
		System.out.println(a.a);
		if(a instanceof B1) {
			B1 b = (B1)a;
			System.out.println(b.a);
		} 
		try {
			a.a="CLONE";
			A1 a2 = (A1) a.clone();
			System.out.println(a.a);
			System.out.println(a2.a);
			System.out.println(a==a2);
			System.out.println(a.equals(a2));
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		A a4 = null;
		try {
			System.out.println(new String("柘").getBytes("utf-8").length);
			System.out.println(new String("user").getBytes(("utf-8")).length);
			System.out.println(new String("用户名").getBytes(("gbk")).length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
class A1 implements Cloneable{
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	String a="A";
}
class B1 extends A1{
	String a="B";
	String b=a;
}