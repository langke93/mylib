package org.langke.think.in.java;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
public class BigDecimalExample {

	public static final int END = Integer.MAX_VALUE;
	public static final int START = END - 100;
	public static void main(String args[]){
	    double fd = 15.0/100000f;//异常统计忽略业务异常以及其他异常
        BigDecimal   big   =   new   BigDecimal(fd);  
        double   f1   =   big.setScale(3,   BigDecimal.ROUND_DOWN).doubleValue();
        System.out.println(big);
        System.out.println(f1);
	    
		String bi1="20000000000";
		String bi2="90000000000";
		java.math.BigInteger bigInteger = new BigInteger(bi1);
		System.out.println(bigInteger.add(new BigInteger(bi2)));
		BigDecimal bigDec = new BigDecimal("-20111123093633.623232");
		System.out.println(bigDec.add(bigDec));
		int a=1,b=2,c=3;
		b+=((c--)/(a++));
		System.out.println(b);
		
		//交换变量
		int x=2, y=9;
		x=x+y;
		y=x-y;
		x=x-y;
		System.out.println("x="+x+"y="+y);
		
		Integer m=Integer.valueOf(2);
		String n="Java";
		n=m+n;
		//n+=m;
		System.out.println(n);
		System.out.println(""+'a'+'b');
		System.out.println("2+2="+2+2);
		String[] arr={"a","b"};
		System.out.println(String.valueOf(arr));
		System.out.println("a\u0022.length()+\u0022b".length());

		int j=0;
		for(int i=0;i<100;i++)
			j=j++;
		System.out.println(j);
		
		int count = 0;
		for(int i=START;i<END;i++)
			count++;
		System.out.println(count);
		
		int distance= 0;
		for (int val=-1;val!=0;val<<=1)
			distance++;
		System.out.println(distance);
		
		int start=Integer.MAX_VALUE-1;
		//for(int i=start;i<=start+1;i++)
		System.out.println(start);
		
		double d=0.0/0.0;//Double.NaN;
		if (d!=d) 
			System.out.println(d);
		
/*		byte s=-1;
		while(s!=0){
			s>>>=1;
		}*/
		String str=new String("0");
		String str2=new String("0");
		System.out.println(
				str==str2
				);
		
		long i= Integer.MIN_VALUE;
		while(i!=0&&i==-i){}
		
		final int START = 2000000000;
		 count =0;
		 float f=START;
		 for(;f<START+5;f++)
			 count ++;
		 System.out.println(count);
		 
		 System.out.println(decision());
		 
		 try{		 
		 }catch(Exception e){
			 System.out.println();	
		 }
		 method(000);
		 
		 String s = null;
		 System.out.println(s instanceof String);
		 ((BigDecimalExample) null).method(1);
		 for ( i=0;i<100;i++)
			new BigDecimalExample();
		 BigInteger bi = new BigInteger("1111");
		 
		 BigInteger total = BigInteger.ONE;
		 total = total.add(bi);
		 System.out.println(total);
		 
		 List list = new ArrayList();
		 list.add("langke");
		 System.out.println(withoutDuplicates(list));
		 

	}	
	public void swich(){
		 Integer a=Integer.valueOf(7);
		 Integer b=Integer.valueOf(65535);

	}
	public static void method(int i){
		System.out.println(i);
	}
	public static void method(Object i){
		System.out.println(i.toString());
	}
	 static boolean decision(){
		 try{
			 return true;
		 }finally{
			 return false;
		 }
	 }
	 static List withoutDuplicates(List original){
		 return new ArrayList (new LinkedHashSet(original));
	 }

	 
}
