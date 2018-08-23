package org.langke.think.in.java;

/**
 * 利率计算
 * @author Administrator
 *
 */
public class InterestRate {

    public static void main(String[] args) {
        double money = 126d*10000d;
        double InterestRate = 0.0441d;
        double year = 20d;
        double bx = 0d;  
        double bj = 0d;
        double sybj = money;
        double lx = 0d;
        double totalBx = 0d;
        double totalBj = 0d;
        double LxBx = 0;//year*79563.59d-money;
        double LxBj = 0d;//money*InterestRate*(20*12/2+0.5)+money;
        double bxsybj = money;
        for(int y=1;y<=year;y++){
            bx = (double) (money*InterestRate*Math.pow(1d+InterestRate,year)/(Math.pow(1d+InterestRate,year)-1d));
            LxBx = year*money*InterestRate*Math.pow((1+InterestRate),year)/(Math.pow(1+InterestRate,year)-1)-money;
            bxsybj-=LxBx;
            totalBx +=bx;
            bj = money/year+sybj*InterestRate;
            totalBj+=bj;
            sybj -=(money/year);//剩余本金
            System.out.println("年："+y+" 本息："+bx/12d+" 已还本金："+bxsybj+" 本金："+bj/12d+" 已还本金："+totalBj);
        }
        System.out.println("总还款:本息"+String.valueOf(totalBx)+" 本金："+totalBj);
        

        float yuegon = 3403;
        float total = 0;
        for (int i=1;i<=5*12;i++){
            total +=yuegon;
            System.out.println(i+"gong:"+total+"月供本金"+yuegon);
            yuegon+=11;
        }
        
         yuegon = 5250;
         total = 0;
        for (int i=1;i<=5*12;i++){
            total +=yuegon;
            System.out.println(i+"gong:"+total+"月供本金"+yuegon);
           // yuegon+=11;
        }
        
    }

}
