package org.langke.think.in.java;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.UUID;
 abstract class AbstractClass {
	 String thisVal="la";
  abstract void method();
 void method1(){
	 System.out.println("method1");
 }
}
class AbstractTest extends AbstractClass{
	
	public AbstractTest() {
		System.out.println(super.thisVal);
	}
	public static void main(String arg[]){
		byte[] p= {0x56,0x34,0x12,0x00};
		System.out.println(Integer.toHexString(ByteBuffer.wrap(p).order(ByteOrder.LITTLE_ENDIAN).getInt()));
		System.out.println(Integer.toHexString(ByteBuffer.wrap(p).getInt()));
		byte[] pp= {0x00,0x12,0x34,0x56};
		System.out.println(Integer.toHexString(ByteBuffer.wrap(pp).order(ByteOrder.BIG_ENDIAN).getInt()));
		System.out.println(Integer.toHexString(ByteBuffer.wrap(pp).getInt()));
		
		System.out.println("当前系统order="+ByteOrder.nativeOrder());
		AbstractTest at= new AbstractTest();
		
		at.method();
		System.out.println(java.util.Calendar.getInstance().getTime());
		System.out.println(("\u8d44\u6e90\u7f51"));
		Date date = new Date();
		System.out.println(date);
		System.out.println(date.getTime());
		date.setTime(date.getTime()+60000);
		System.out.println(date.getTime());
		System.out.println(date);
		System.out.println(UUID.randomUUID().toString());
		System.out.println(java.net.URLEncoder.encode("\n"));
		System.out.println(UUID.randomUUID().toString());
		
		 int MAXIMUM_CAPACITY = 1 << 30; 
		 System.out.println(MAXIMUM_CAPACITY);	
		 System.out.println(MAXIMUM_CAPACITY=1 << 2);
		 MAXIMUM_CAPACITY>>>=3;
		 System.out.println(MAXIMUM_CAPACITY);
		 System.out.println((byte)Byte.MAX_VALUE+1);
		 System.out.println(Byte.MIN_VALUE);
		 System.out.println('a'=='a');
		 System.out.println("a".equals("a"));
		 System.out.println(Integer.MAX_VALUE);
		 System.out.println(at.callFunc());
	}

	public StringBuilder callFunc(){
		StringBuilder hell = new StringBuilder("Hello");
		 try{
			 return returnVal(hell);
		 }finally{
			 hell.delete(0, hell.length());
		 }
	}
	public StringBuilder returnVal(StringBuilder str){
		 System.out.println("returnVal");
		return str.append("1");
	}
	void method() {
		
		System.out.println("method SUB");

		System.out.println(this.thisVal);
		
	}
}