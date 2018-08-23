package org.langke.j2ee14.ch9;

import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
/**
 *第一个Servlet
 */
public class TestFilterServlet extends javax.servlet.http.HttpServlet
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
	 *帮助方法，用于处理不同的请求
	 */
	public void processRequest(HttpServletRequest request,HttpServletResponse response)
		throws javax.servlet.ServletException,java.io.IOException
	{
		response.setContentType("text/html;charset=gb2312");
        PrintWriter out = response.getWriter();        
        String action=request.getParameter("action");
        //把视图派发到目的地。
        out.println("正在把视图派发到目的<br>");
        RequestDispatcher d = request.getRequestDispatcher("/servlet/testfilter2");        
        if(action.equals("forward"))
	    d.forward(request, response);
	    else
	    d.include(request,response);       
     } 	
}




    
	