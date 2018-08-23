package org.langke.think.in.java;
import java.util.Collection;
import java.util.List;
import java.util.Map;
public class InterFaceExample {
	public static void main(String args[]){
		MyInterFaceImpl interFace = new MyInterFaceImpl();
		System.out.println(interFace.doMethod("try"));
		short s=1;
		s = (short) (s +1);
		s+=1;
		String  a="a",b="a";
		if(a.equals(b))
			System.out.println(a.hashCode()+":"+b.hashCode());
	}
}
interface MyInterFaceIntf{
	final String str = "InterFace";
	
	public  String doMethod();
}
interface  MyInterFaceIntf2  {
	public String doMethod(String s);
}
class  MyInterFaceImpl implements MyInterFaceIntf,MyInterFaceIntf2{

	 public String doMethod() {
		return this.str;
	}
	public String Fuck(){
		return "Fuck";
		
	}
	public String doMethod(String s) {
		try{
		return s;
		}catch(Exception e){return s;}finally{
			System.out.println(2<<10000000);
		}
	}
}