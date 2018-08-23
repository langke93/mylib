package org.langke.java5;

public class Comment {
 
	  @Deprecated

	    public String str;

	    public static void main(String[] args) {

	            new SubMain().doSomething();

	    }

	    public void doSomething() {

	            System.out.println("Done.");

	    }

	}



class SubMain extends Comment {

    @Override
 

    public void doSomething() {

          java.util.ArrayList aList = new java.util.ArrayList();

          aList.add(new Integer(0));

            System.out.println("Done by SubMain.");

    }


}
