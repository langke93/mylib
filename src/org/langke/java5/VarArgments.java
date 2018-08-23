package org.langke.java5;

public class VarArgments {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VarArgments va = new VarArgments();
		System.out.println(va.testVararg("abc"));
		System.out.println(va.testVararg("abc","cdf"));
		System.out.println(va.testVararg("abc","cdf","cao"));
	}
	public String testVararg(String... args) {

	    StringBuilder sb = new StringBuilder();

	    for (String str : args) {

	        sb.append(str);

	    }

	    return sb.toString();

	}
}
