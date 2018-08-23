package org.langke.think.in.java;

import java.util.*;
/**
 * Stack有时也可以称为“后入先出”（LIFO）集合。换言之
 * ，我们在堆栈里最后“压入”的东西将是以后第一个“弹出”的
 * @author lizz
 *
 */
public class Stacks {
  static String[] months = { 
    "January", "February", "March", "April",
    "May", "June", "July", "August", "September",
    "October", "November", "December" };
  public static void main(String[] args) {
    Stack stk = new Stack();
    for(int i = 0; i < months.length; i++)
      stk.push(months[i] + " ");
    System.out.println("stk = " + stk);
    // Treating a stack as a Vector:
    stk.addElement("The last line");
    System.out.println(
      "element 5 = " + stk.elementAt(5));
    System.out.println("popping elements:");
    while(!stk.empty())
      System.out.println(stk.pop());
  }
} ///:~