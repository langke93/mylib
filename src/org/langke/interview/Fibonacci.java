package org.langke.interview;

public class Fibonacci {

    //递归
    public static void FibonacciOne(int n){
        for(int i=1;i<n+1;i++){
            System.out.print(FibonacciRec(i)+"\t");
            if(i%5==0)
                System.out.println();
        }
    }
    public static int FibonacciRec(int n){
        if(n==1 || n==2)
            return 1;
        else
            return FibonacciRec(n-1)+FibonacciRec(n-2);
    }
    
    //数组
    public static void FibonacciTwo(int n){
        if(n==1)
            System.out.println(1);
        else if(n==2)
            System.out.println(1+"\t"+1);
        else{
            int arr[] = new int [n];
            arr[0] = arr[1] = 1;
            int i=0;
            for(i=2;i<arr.length;i++){
                arr[i] = arr[i-1]+arr[i-2];
            }
            for(i=0;i<arr.length;i++){
                System.out.print(arr[i]+"\t");
                if((i+1)%5==0)
                    System.out.println();
            }
        }
    }
    
    //递推方式
    public static void FibonacciThree(int n){
        if(n==1)
            System.out.println(1);
        else if(n==2)
                System.out.println(1+"\t"+1);
        else{
            int x=1,y=1,k=0;
            System.out.print(x+"\t"+y+"\t");
            for(int i=1;i<n-1;i++){
                k=x+y;
                x=y;
                y=k;
                System.out.print(k+"\t");
                if((i+2)%5==0)
                    System.out.println();
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("递归方式Fibonacci");
        FibonacciOne(15);
        System.out.println("数组方式Fibonacci");
        FibonacciTwo(15);
        System.out.println("递推Fibonacci");
        FibonacciThree(15);
    }
}
