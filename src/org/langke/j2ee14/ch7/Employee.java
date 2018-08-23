package org.langke.j2ee14.ch7;
/**
 *代表雇员的值对象，和Employee表对应
 */
public class Employee implements java.io.Serializable
{
	public int id;
	public String name;
	public int age;
	public int sex;
	public float salary;
	public String description;
}