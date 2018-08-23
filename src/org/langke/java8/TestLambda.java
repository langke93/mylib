package org.langke.java8;

public class TestLambda {

    public static void main(String[] args){
        (new Thread(
                ()->{
                    System.out.println("Lambda");
                }
          )).start();
    }
}
