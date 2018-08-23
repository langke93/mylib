package org.langke.util.security;

import java.io.IOException;

import sun.misc.*;

public class BASE64Coding {
	private static BASE64Encoder encoder = new BASE64Encoder();

	private static BASE64Decoder decoder = new BASE64Decoder();

	public BASE64Coding() {
	}

	public static String encode(String s) {
		return encoder.encode(s.getBytes());
	}

	public static String decode(String s) {
		try {
			byte[] temp = decoder.decodeBuffer(s);
			return new String(temp);
		} catch (IOException ioe) {
			// handler exception here
		}
		return null;
	}
	public static void main(String args[])throws Exception{
		String s = "中文字符-china character";
		s = BASE64Coding.encode(s);
		System.out.println(s);
		System.out.println(BASE64Coding.decode(s));
		s = "YToyOntzOjg6ImtleXdvcmRzIjtzOjM6IjE1UiI7czoxODoic2VhcmNoX2VuY29kZV90aW1lIjtpOjE0MzY3MTEwMjI7fQ==";
		System.out.println(BASE64Coding.decode(s));
	}
	
}
