package org.langke.data.imp;

public class URLEncoder {
	public static String encode(String in){
		if(in==null) return null;
		return java.net.URLEncoder.encode(in);
	}
}
