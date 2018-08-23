package org.langke.think.in.java;

import java.math.BigDecimal;

public class MathTest {	
	/**
	 * 
	 compareTo:将此 BigDecimal 与指定的 BigDecimal 进行比较。对于针对六个布尔比较运算符 (<, ==, >, >=, !=, <=) 中的每一个运算符的各个方法，优先提供此方法。
	 				执行这些比较的建议语句是：(x.compareTo(y) <op> 0)，其中 <op> 是六个比较运算符之一。 
			 参数：
			val - 将此 BigDecimal 与之比较的 BigDecimal。 
			返回：
			当此 BigDecimal 在数值上小于、等于或大于 val 时，返回 -1，0，或 1。
			
	 * @param x
	 * @return
	 */
	
	public static BigDecimal Factorial(BigDecimal x){
		if(x.compareTo(BigDecimal.ZERO)==0) 
			return   BigDecimal.ONE;
		else 
			if(x.compareTo(new BigDecimal("2"))==-1) 
				return x;
		else	
			return x.multiply(Factorial(x.subtract(BigDecimal.ONE)));
	}
	
	/**
	 * 1000的阶乘有多少位,用对数计算大家都知道是2568哈, 
那么开一个2568位的数组,循环求阶乘,对数组里的数超过10就进位; 
或者超过256进位,65536进位都是一样的.最后从数组最高位输出就行了.
	 * @param l
	 * @return
	 */
    static int[] v = new int[500];
    static int ten = 0;
    static int length = 1;
    public static void m(int t) {
        while (t%10==0) {
            t /= 10;
            ten++;
        }
        for (int i=0; i<length; i++) v[i]*=t;
        int value;
        for (int i=0; i<length; i++) {
            value = v[i];
            v[i] = 0;
            t = i;
            while (value>0) {
                if (length==t) length++;
                v[t] = v[t]+value%1000000;
                value /= 1000000;
                t++;
            }
        }
    }
    public static void n(int t) { for (int i=1; i<=t; i++) m(i); }
	public static  void  Factorial(){
	        v[0] = 1;
	        long time = System.currentTimeMillis();
	        n(1000);
	        System.out.println(System.currentTimeMillis()-time);

	        int i=v.length-1;
	        while (v[i]==0) i--;
	        for (; i>-1; i--) {
	            System.out.print(v[i]/100000);
	            System.out.print(v[i]/10000%10);
	            System.out.print(v[i]/1000%10);
	            System.out.print(v[i]/100%10);
	            System.out.print(v[i]/10%10);
	            System.out.print(v[i]%10);
	        }
	        for (i=0; i<ten; i++) {
	            System.out.print('0');
	        }

	}
	public static void Factorial2()
	  {
	    int[] digits = new int[2568];
	    int max_digit = 2567;
	    digits[max_digit] = 1;
	    for (int d=2;d<=1000;d++)
	    {
	      for (int k=max_digit; k<digits.length; k++)
	        digits[k] *= d;

	      int k = digits.length-1;
	      while (k>=max_digit)
	      {
	        if (digits[k]>10)
	        {
	          digits[k-1] += digits[k] / 10;
	          digits[k] = digits[k] % 10;
	          if (k-1<max_digit) max_digit = k-1;
	        }
	        k--;
	      }
	    }
	    for (int i=max_digit; i<digits.length; i++)
	    {
	      System.out.print(digits[i]);
	    }
	    System.out.println();
	  }

	
	public static void main(String arg[]){
	    double d1=(1-1/1000000d)*100;
	    double d2=100-1/1000000d;
	   System.out.println(d1);   
	   System.out.println(d2);

		long start_time = System.nanoTime();
		java.math.BigDecimal bigInteger = new BigDecimal("1000");
		bigInteger = Factorial(bigInteger);
		System.out.println(bigInteger);
		long end_time = System.nanoTime();
		long total = end_time-start_time;
		System.out.println("total:"+total+" NS");
		//Factorial();
		Factorial2();
		start_time = System.nanoTime();
		for (int i = 0; i < 10000 * 100*1; ++i) {
			  System.nanoTime();
		}
		end_time = System.nanoTime();
		System.out.println( (end_time-start_time)/(10000 * 100*1));
	}
}

