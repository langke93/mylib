package org.langke.interview;

import java.util.Arrays;
public class ArrayOrder {
	public static void main(String[] args) {
		String[] s = "5,8,7,4,3,9,1".split(",");
		int[] ii = new int[s.length];
		for(int i=0;i<s.length;i++){
			ii[i] = Integer.parseInt(s[i]);
		}
		Arrays.sort(ii);
		//asc
		for(int i=0;i<s.length;i++){
			System.out.println(ii[i]);
		}
		//desc
		for (int i = (s.length-1); i >=0; i--) {
			System.out.println(ii[i]);
		}
	}
}
