package org.langke.j2ee14.ch9;

import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
/**
 *第一个Servlet
 */
public class TestFilterServlet2 extends javax.servlet.http.HttpServlet
{
	/**
	 *处理get请求
	 */
	public void doGet(HttpServletRequest request,HttpServletResponse response)
		throws javax.servlet.ServletException,java.io.IOException
	{ 
       processRequest(request,response);
    }
    /**
     *处理post请求
     */
    public void doPost(HttpServletRequest request,HttpServletResponse response)
		throws javax.servlet.ServletException,java.io.IOException
	{
		processRequest(request,response);
	}
	/**
	 *帮助方法，获得被过滤后的参数的值
	 */
	public void processRequest(HttpServletRequest request,HttpServletResponse response)
		throws javax.servlet.ServletException,java.io.IOException
	{
		response.setContentType("text/html;charset=gb2312");
        PrintWriter out = response.getWriter();
        out.println("在"+request.getAttribute("action")+"方法下进行了过滤<br>");
        out.println("过滤后的参数值是:");
        out.println(request.getAttribute("country"));  
     } 	
}




    
	