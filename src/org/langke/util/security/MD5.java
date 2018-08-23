package org.langke.util.security;

import java.security.MessageDigest;

public class MD5 {
	public static String getmd5str(String s){ 
	      char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'}; 
	      try { 
	          byte[] bytes = s.getBytes(); 
	          MessageDigest md = MessageDigest.getInstance("MD5"); 
	          md.update(bytes); 
	          bytes = md.digest(); 
	          int j = bytes.length; 
	          char[] chars = new char[j * 2]; 
	          int k = 0; 
	          for (int i = 0; i < bytes.length; i++) { 
	              byte b = bytes[i]; 
	              chars[k++] = hexChars[b >>> 4 & 0xf]; 
	              chars[k++] = hexChars[b & 0xf]; 
	          } 
	          return new String(chars); 
	      } 
	      catch (Exception e){ 
	          return null; 
	      } 
	  } 


public static void main(String[] args) {
	  try {
		  MD5 db=new MD5();
		String str=db.getmd5str("faq-demo");
		System.out.println("strmd5==="+str);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
