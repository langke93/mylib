package org.langke.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {
	public static void main(String arg[]){
		//JCA
		try {
			String mac="CA";
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte bmac[] = mac.getBytes();
			md.update(bmac);
			bmac = md.digest();
			String str_mac = "";
			for(int i=0;i<bmac.length;i++){
				str_mac += bmac[i];
			}
			System.out.println(str_mac);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
	}
}
