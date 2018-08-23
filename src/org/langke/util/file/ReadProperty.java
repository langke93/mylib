package org.langke.util.file;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
 

public class ReadProperty {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(readValue("menu.root_name"));
	}
	   
	public static String readValue(String key){
		return readValue( key, null) ;
	}
    /**  
     * 读取properties文件   
     * @param propertiesname  方法1
     * @return  
     */  
    public static String readValue(String key,String fileName) {
  	  		Properties props = new Properties();
  	  		InputStream in = null;
  	  		if(fileName==null||fileName.equals(""))fileName = "/config.properties";
  	        try {
	  	         in = new BufferedInputStream (ReadProperty.class.getResourceAsStream(fileName));
	  	         props.load(in);
	  	         String value = props.getProperty (key);
	  	         in.close();
	  	         props.clear();
	  	         return new String(value.getBytes("ISO8859_1"),   "utf-8");
  	        } catch (Exception e) {
  	         e.printStackTrace();
  	         if(in!=null)
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				} 	        	 
  	         return null;
  	        }
  	 }
}
