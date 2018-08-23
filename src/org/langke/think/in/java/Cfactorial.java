package org.langke.think.in.java;

import java.io.IOException;
import java.math.BigDecimal;

public class Cfactorial {
	static int N = 30000;  //1k阶乘
    static int ff(int[] a)
    {
        int i, j, c, t;
        int m = 1;
        for(i = 1; i <= N; ++i)
        {
            for(j = 0, c = 0; j < m; ++j)
            {
                t    = a[j]*i + c;
                a[j] = t%1000;
                c    = t/1000;
            }
            for(;c != 0;)
            {
                a[m] = c%1000;
                m++;
                c   /= 1000;
            }
        }
        return m;
    }
    public static void main(String[] args)   throws IOException{
    	long start_time = java.util.Calendar.getInstance().getTimeInMillis();

        int[] a = new int[9999999];
        int i, m, k;
        a[0] = 1;
        m = ff(a);
        
        for(i = m-1, k = 0; i >= 0; --i, ++k)
        {            
            if(k == 5) //格式化输出
            {
                k = 0;
                System.out.println("");
            }
            System.out.print(a[i]);
            //System.out.printf("%d,", a[i]);
        }
        System.out.println("一共有"+m+"个数位");
		long end_time = java.util.Calendar.getInstance().getTimeInMillis();
		long total = end_time-start_time;
		System.out.println("total:"+total+" MS");
    }

}
