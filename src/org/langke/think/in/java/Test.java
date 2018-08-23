package org.langke.think.in.java;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Test {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
	    String cachettl =  java.security.Security.getProperty("networkaddress.cache.ttl");  
        String negative = java.security.Security.getProperty("networkaddress.cache.negative.ttl");
        System.out.println("cachettl:"+cachettl+" negative:"+negative);
	    float income = 34982f;
	    float expand = 24825f;
	    float income_annual_rate_of_growth = 0.078f;
	    float expand_annual_rate_of_growth = 0.064f;
	    long savings =0;
	    for(int i=0;i<=100;i++){
	        long saving = 0 ;
	        if(i != 0) {
	            income = income*(1f+income_annual_rate_of_growth) ;
	            expand = expand*(1f+expand_annual_rate_of_growth);
	        }
            saving = (long) (income - expand) ;
            savings += saving;
	        System.out.println("year:"+i + " income:"+income+ " expand:"+expand+" saving:"+saving+" savings:"+savings);
	    }
	    
		Date date = new Date();
		date.setHours(date.getHours()-24);
		System.out.println(date);
		Map map = new HashMap();
		map.put("map", "hashMap");
		System.out.println(arg(map));
		System.out.println(map);
		System.out.println(1 << 16);
		Thread.sleep(10000);
		List lis = new ArrayList<Double>(100000000);// 长度2千万的空list  
		System.out.println(Runtime.getRuntime().availableProcessors());//cpu个数
		sum();
	}

	public static <K, V> Map arg(Map map) {
		try{
			throw new IllegalAccessException("hehe");
		}catch(Exception e){
			e.printStackTrace();
		}
		map.put("java只有值传递", "没有引用传递");
		map = new TreeMap<K, V>();
		map.put("test","treemap");
		return map;
	}
	public static void sum(){
		 long d=System.currentTimeMillis();  
		 double n=0;  
		 for(int i=0;i<Integer.MAX_VALUE;++i){// 1亿次  
		     n+=i;  
		 }  
		 System.out.println(n);
		 System.out.println(System.currentTimeMillis()-d+"....毫秒"); 
	}
}
