package org.langke.think.in.java.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{

		for(int i=0;i<10;i++){
			AObject object = new AObject();
			long beginTime = System.currentTimeMillis();
			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
			objectOutput.writeObject(object);
			objectOutput.close();
			byteOutput.close();
			byte[] bytes = byteOutput.toByteArray();
			System.out.println("Java序列化耗时："+(System.currentTimeMillis()-beginTime)+"ms");
			System.out.println("Java序列化后的字节大小为:"+bytes.length);
			
			beginTime = System.currentTimeMillis();
			ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
			ObjectInputStream objectInput = new ObjectInputStream(byteIn);
			Object obj = objectInput.readObject();
			objectInput.close();
			System.out.println("Java反序列化耗时："+(System.currentTimeMillis()-beginTime)+"ms");
			System.out.println(obj);
		}
	}
}

 class AObject implements Serializable{
	private String a="java";
	private String b="bluedavy";
	private String c="chapter4";
	private String d="hello";
	private int i=100;
	private int j=10;
	private long m=100L;
	private boolean isA=true;
	private boolean isB=false;
	private boolean isC=false;
	private BObject object=new BObject();
	private BObject bobject=new BObject();
	private BObject cobject=new BObject();
	private BObject dobject=new BObject();
}
 class BObject implements Serializable{
	
}