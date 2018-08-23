package org.langke.util.security;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import sun.misc.*;
/*
 * 随机密钥:MAC通常用于SSL之类协议，用来验证线路上传输的数据。
 * 通过使用MAC，可以确定数据在线路上是否被破坏。
 */
public class MAC {
	public static void main(String arg[])throws Exception{
		String text = "消息摘要";
		SecureRandom random = new SecureRandom();
		byte[] keyBytes = new byte[20];
		random.nextBytes(keyBytes);
		SecretKeySpec key = new SecretKeySpec(keyBytes,"HMACSHA1");
		System.out.println("key:"+new BASE64Encoder().encode(key.getEncoded()));
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(key);
		mac.update(text.getBytes("UTF8"));
		byte[] result = mac.doFinal();
		System.out.println("MAC:"+new BASE64Encoder().encode(result));
//		
		try{
		float i=(float)(0.0)/(float)(0.0);//double i=0.0/0.0;
		if(i==i)
			System.out.println("Yes,i==i");
		else
			System.out.println("No,i!=i"+i);
		}catch(Exception e){
			e.printStackTrace();
		}
//
		System.out.println(2.00 - 1.10);
		System.out.println(12345+5432l);//注后面是:L
//
		  short a=1;
		  short b=(short) (a+1);
	}
}
