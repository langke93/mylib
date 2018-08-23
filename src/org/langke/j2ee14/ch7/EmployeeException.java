package org.langke.j2ee14.ch7;
/**
 *异常类
 */
public class EmployeeException extends Exception
{
	public String toString()
	{
		return "at EmployeeException：可能连接数据库出错!"+this.getMessage();
	}
	public EmployeeException(String msg)
	{
		super(msg);
	}
	public EmployeeException(Throwable th)
	{
		super(th);
	}
}