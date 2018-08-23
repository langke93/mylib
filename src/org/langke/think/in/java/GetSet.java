/**
 * 
 */
package org.langke.think.in.java;

import java.util.Calendar;

/**
 * @author lee
 *
 */
public class GetSet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GetSet getSet = new GetSet();
		getSet.setYsq(1);
		getSet.setYsq(getSet.getYsq()+1);
		System.out.println(getSet.getYsq());
		
		java.util.Date date = new java.util.Date();
		System.out.println(Calendar.getInstance().getTime());
	}
	private int ysq ;
	public int getYsq() {
		return ysq;
	}
	public void setYsq(int ysq) {
		this.ysq = ysq;
	}
	

}
