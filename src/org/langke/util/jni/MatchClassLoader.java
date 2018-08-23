/*
 * @(#)MatchClassLoader.java	0.1 2009-3-27
 *
 * Copyright 2009 langke, Inc. All rights reserved.
 */
package org.langke.util.jni;
/**
 * 指纹对比接口，直接调用match静态方法
 * @author lee
 *
 */
public class MatchClassLoader {
	//比对指纹模板，ARegTemplate是登记模板，AVerTemplate是比对模板，比对成功返回True，失败返回False。
	public  native boolean process(String ARegTemplate, String AVerTemplate);
	
	/**
	 * 设置比对指纹模板的阀值。
	AThreshold设定指纹识别系统比对识别阀值分数(1-100)，默认为10，值越大，误判率越低同时拒绝率变大
	AOneToOneThreshold设定Biokey低速指纹1：1比对的识别阀值分数(1-100)，默认为10，值越大，误判率越低同时拒绝率变大
	注意：1:1没有这个方法
	 */
	//public  native void SetThreshold(long AThreshold, long AOneToOneThreshold); 
	public MatchClassLoader(){}
	static{
		//System.out.println(System.getProperty("java.library.path"));
        System.loadLibrary("matchload");//把Match.dll复制到tomcat_home/bin
	}
	//使用单例模式
	//private MatchClassLoader(){};
	  //注意这是private 只供内部调用 
	private static MatchClassLoader instance = new MatchClassLoader(); 
		//这里提供了一个供外部访问本class的静态方法，可以直接访问 
	private static MatchClassLoader getInstance() { 
		return instance;  
	 }
	/**
	 * 比对方法
	 * @param reg
	 * @param ver
	 * @return
	 */
	public static boolean match(String reg,String ver){
		if(reg==null || ver ==null){
			return false;
		}else if(reg.indexOf("/////")!=-1 || ver.indexOf("/////")!=-1){
			//System.out.println("不能有转义符");
			//return false;
		}
		return MatchClassLoader.getInstance().process(reg,ver);
	}
	    public static void main(String args1[]) {
	    	String reg = "";
	    
	    	String ver = "";
	    	System.out.println( match(reg,ver));
	    	System.out.print("continue");
			
	    }
}
