package org.langke.net.network.firewall;

import java.io.IOException;
import java.io.InputStream;

public class FirewallInterface {
	final private static String OPEN_ORACLE_PORT= "netsh firewall add portopening TCP 1521 1521 ENABLE CUSTOM ";
	final private static String encoding = System.getProperty("file.encoding");
	static String result = "";
	public static String openOraclePort(String client_ip){
		try{
			if(client_ip==null) return result;
			Process child = Runtime.getRuntime().exec(OPEN_ORACLE_PORT+client_ip);
			InputStream in = child.getInputStream();
			int c;
			String ininfo="";
			while ((c = in.read()) != -1) {
				ininfo+=((char)c);
			}
			result += new String(ininfo.getBytes("iso-8859-1"),encoding);
			
			InputStream err = child.getErrorStream();
			String errinfo="";
			while((c = err.read())!=-1){
				errinfo+=((char)c);
			}
			result += new String(errinfo.getBytes("iso-8859-1"),encoding);
			
			in.close();
			try {
				child.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
				result+=result;
			}
	}catch (IOException e) {
			e.printStackTrace();
			result+=result;
		}catch (Exception e){
			e.printStackTrace();
			result+=result;
			
		}
		return result;
	}
	
}
