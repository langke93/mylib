package org.langke.j2ee14.ch22;
public class ServiceLocatorException extends Exception
{
	public ServiceLocatorException(String msg)
	{
		super(msg);
	}
	public ServiceLocatorException(Exception e)
	{
		super(e);
	}
}