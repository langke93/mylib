package org.langke.j2ee14.ch9;

import javax.servlet.*;
import java.util.Locale;
/**
 *对请求进行监听，包含监听请求的创建和销毁以及请求中属性的改变
 */
public class RequestListenerTest 
implements ServletRequestListener,ServletRequestAttributeListener
{
	//ServletRequestListener
	public void requestDestroyed(ServletRequestEvent sre) 
	{
		log("request 销毁");
	}
	
	public void requestInitialized(ServletRequestEvent sre) 
	{
		log("request 初始化了");
		ServletRequest sr=sre.getServletRequest();
		Locale locale=null;
		locale=sr.getLocale();
		if(locale.equals(Locale.CHINA))
		sr.setAttribute("encoding","text/html;gb2312");
		else if(locale.equals(Locale.US))
		sr.setAttribute("encoding","text/html;utf-8");
		else 
		   sr.setAttribute("encoding","text/html;iso8859-1");			
		
		
	}//ServletRequestListener
	
	//ServletRequestAttributeListener
	public void attributeAdded(ServletRequestAttributeEvent event) 
	{
	    log("attributeAdded('" + event.getName() + "', '" +
	    event.getValue() + "')");
	}
	public void attributeRemoved(ServletRequestAttributeEvent event) 
	{
		log("attributeRemoved('" + event.getName() + "', '" +
	    event.getValue() + "')");

	}
	
	public void attributeReplaced(ServletRequestAttributeEvent event)
	{
		log("attributeReplaced('" + event.getName() + "', '" +
	    event.getValue() + "')");
		
	}//ServletRequestAttributeListener
	private void log(String msg)
	{
		java.io.PrintWriter out=null;
		try
		{
			out=new java.io.PrintWriter(new java.io.FileOutputStream("c:\\request_log.txt",true));
		    out.println(msg);
		    out.close();
		 }
		 catch(Exception e)
		 {
		 	out.close();
		 }		  
	}	
}
	
		