package org.langke.think.in.java;

public class RandomBounds {
  static void usage() {
	    System.err.println("Usage: \n\t" +
	      "RandomBounds lower\n\t" +
	      "RandomBounds upper");
	    System.exit(1);
	  }
	  public static void main(String[] args) {
	    //if(args.length != 1) usage();
		String str="lower";
	    if(str.equals("lower")) {
	      while(Math.random() != 0.0){
	        System.out.println(Math.random()); // Keep trying
	      }
	      System.out.println("Produced 0.0!");
	    } 
	    else if(args[0].equals("upper")) {
	      while(Math.random() != 1.0)
	        ; // Keep trying
	      System.out.println("Produced 1.0!");
	    } 
	    else 
	      usage();
	  }
}
