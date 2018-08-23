package org.langke.j2ee14.ch9;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.Locale;

public class DispatcherFilter implements Filter
{
	//初始化filter
	public void init(FilterConfig config) throws ServletException {
    }
    /**
     *具体的过滤方法
     */
    public  void doFilter(ServletRequest srequest, ServletResponse  sresponse, FilterChain chain)
        throws IOException, ServletException
    {
                     
        HttpServletRequest request = (HttpServletRequest)srequest;
        java.util.Enumeration params=request.getParameterNames();
        //把请求中所有参数类型为String的对象值设置转为大写。
        while(params.hasMoreElements())
        {
        	String paramName=(String)params.nextElement();
        	Object obj =request.getParameter(paramName);
        	if(obj instanceof String){
        	request.setAttribute(paramName,((String)obj).toUpperCase());}
        }
        chain.doFilter(request,sresponse);        
    }
	
	 //销毁过滤器
	public void destroy()
	{

	}
}