package org.langke.interview;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflecTest {
	//server OSR编译阈值：10700
	private static final int WARMUP_COUNT=10700;
	private ForReflection testClass = new ForReflection();
	private static Method method = null;
	public static void main(String[] args)throws Exception{
		method = ForReflection.class.getMethod("execute",new Class<?>[]{String.class});
		ReflecTest demo = new ReflecTest();
		for(int i=0; i<20; i++){
			demo.testDirectCall();
			demo.testCacheMethodCall();
			demo.testNoCacheMethodCall();
		}
		long beginTime = System.currentTimeMillis();
		demo.testDirectCall();
		long endTime = System.currentTimeMillis();
		System.out.println("直接调用消耗的时间为： "+(endTime-beginTime)+"毫秒");
		beginTime=System.currentTimeMillis();
		demo.testNoCacheMethodCall();
		endTime = System.currentTimeMillis();
		System.out.println("不缓存Method,反射调用消耗的时间为："+(endTime-beginTime)+"毫秒");
		beginTime = System.currentTimeMillis();
		demo.testCacheMethodCall();
		endTime=System.currentTimeMillis();
		System.out.println("缓存Method,反射调用消耗的时间为："+(endTime-beginTime)+"毫秒");
	}
	public void testDirectCall(){
		for(int i=0; i<WARMUP_COUNT; i++){
			testClass.execute("hello");
		}
	}
	public void testCacheMethodCall()throws Exception{
		for(int i=0; i<WARMUP_COUNT; i++){
			method.invoke(testClass, new Object[]{"hello"});
		}
	}
	public void testNoCacheMethodCall() throws Exception{
		for(int i=0; i<WARMUP_COUNT; i++){
			Method testMethod=ForReflection.class.getMethod("execute", new Class<?>[]{String.class});
			testMethod.invoke(testClass, new Object[]{"hello"});
		}
	}
	
	public class ForReflection{
		private Map<String,String> caches = new HashMap<String,String>();
		public void execute(String message){
			String b=this.toString()+message;
			caches.put(b,message);
		}
	}
}
