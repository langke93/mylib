package org.langke.think.in.java;

public class IntegerUseAge {
	public static void main(String arg[]){
		int i = Integer.MAX_VALUE;
		int x = 61;
		System.out.format("%s %s",even(x),x%2==0?true:false);
		System.out.println();
		System.out.format("%s %s",i,++i);
		float f = Float.MAX_VALUE;
		System.out.println();
		System.out.format("%f %f",f,++f);
		int val ;
		f=val = i = i+1;
		System.out.println();
		System.out.printf("f:%f val:%s i:%s", f,val,i);
		
	}
	/**
	 * 判断是否偶数
	 * @param i
	 * @return
	 */
	public static boolean even(Integer i){
		return Integer.toBinaryString(i).endsWith("0");
	}
}
