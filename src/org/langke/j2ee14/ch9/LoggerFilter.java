package org.langke.j2ee14.ch9;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.FilterConfig;

public class LoggerFilter implements Filter
{
    protected FilterConfig filterConfig;
    
    /**
     *初始化过滤器,和一般的Servlet一样，它也可以获得初始参数。
     */
    public void init(FilterConfig config) throws ServletException {
        this.filterConfig = config;
    }
    
    /**
      *进行过滤处理，这个方法最重要，所有过滤处理的代码都在此实现。
      */
     public  void doFilter(ServletRequest srequest, ServletResponse  sresponse, FilterChain chain)
        throws IOException, ServletException {
                     
        HttpServletRequest request = (HttpServletRequest)srequest;
        String clientIp=request.getRemoteAddr();
        String clientUser=request.getRemoteUser();
        //把访问信息保存在Log文件中。
        filterConfig.getServletContext().log("客户端："+clientIp+"的用户："+clientUser+"在："+new java.util.Date()+"访问了:"+request.getRequestURI());
        // 把处理权发送到下一个
       chain.doFilter(srequest,sresponse);  
    }   

	public void setFilterConfig(final FilterConfig filterConfig)
	{
		this.filterConfig=filterConfig;
	}
    
    //销毁过滤器
	public void destroy()
	{
		this.filterConfig=null;
	}
}

