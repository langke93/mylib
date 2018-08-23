package org.langke.util.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class Url {
	public static StringBuilder getUrl(String url){
		StringBuilder sb = new StringBuilder();
		URL urlObj ;
		InputStream inputStream = null;
		int c;
		try {
			urlObj = new URL(url);
			inputStream = urlObj.openStream();
			while((c=inputStream.read())!=-1){
				//System.out.write(c);
				sb.append((char)c);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return sb;
	}
	
	public static void main(String args[]) throws UnsupportedEncodingException{
		StringBuilder sb = getUrl("http://www.google.com.hk");
		System.out.println(new String(sb.toString().getBytes("ISO-8859-1"),System.getProperty("file.encoding")));
	}
}
