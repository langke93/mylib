package org.langke.think.in.java;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
public class Encoding extends URLDecoder{
	public static void main(String args[]) throws UnsupportedEncodingException {
		String str = "";
		try {
			str= URLDecoder.decode("%E6%B1%9F%E5%91%80","UTF-8");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(str);
		int f = (100/15);
		System.out.println(f);
		
		new Encoding().echoBytesTest("A");
	}

    void echoBytesTest(String s) throws UnsupportedEncodingException {
        echoBytes(s, "Unicode");
        echoBytes(s, "UnicodeBig");
        echoBytes(s, "UnicodeLittle");
        echoBytes(s, "UnicodeBigUnmarked");
        echoBytes(s, "UnicodeLittleUnmarked");
        echoBytes(s, "UTF-16");
        echoBytes(s, "UTF-16BE");
        echoBytes(s, "UTF-16LE");
        echoBytes(s, "UTF-8");
    }
     
    void echoBytes(String s, String encoding) throws UnsupportedEncodingException {
        byte[] bytes = s.getBytes(encoding);
        for (byte b : bytes) {
            int i = b & 0xff;
            System.out.print(Integer.toHexString(i) + " ");
        }
        System.out.println(" --->"+encoding);
    }
}
