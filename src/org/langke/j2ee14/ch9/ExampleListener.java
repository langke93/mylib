package org.langke.j2ee14.ch9;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionActivationListener;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;


public final class ExampleListener
    implements HttpSessionActivationListener, HttpSessionListener ,ServletContextListener{
  
   ServletContext context;
   int users=0;   
   
   //HttpSessionActivationListener
   public void sessionDidActivate(HttpSessionEvent se) 
   {   	    
   	    log("sessionDidActivate("+se.getSession().getId()+")");
   }
   
  
   public void sessionWillPassivate(HttpSessionEvent se) 
   {
   		log("sessionWillPassivate("+se.getSession().getId()+")");
   }//HttpSessionActivationListener
   
   
     //HttpSessionListener
    public void sessionCreated(HttpSessionEvent event) {

	users++;
	log("sessionCreated('" + event.getSession().getId() + "'),目前有"+users+"个用户");
	context.setAttribute("users",new Integer(users));
    }

   
    public void sessionDestroyed(HttpSessionEvent event) {

	users--;
	log("sessionDestroyed('" + event.getSession().getId() + "'),目前有"+users+"个用户");
	context.setAttribute("users",new Integer(users));

    }//HttpSessionListener
    
    //ServletContextListener
    public void contextDestroyed(ServletContextEvent sce) {

	log("contextDestroyed()-->ServletContext被销毁");
	   this.context = null;

    }

    public void contextInitialized(ServletContextEvent sce) {

	this.context = sce.getServletContext();
	log("contextInitialized()-->ServletContext初始化了");

    }//ServletContextListener
    

    private void log(String message) {	
	    
	    PrintWriter out=null;
	    try
	    {
	    	out=new PrintWriter(new FileOutputStream("c:\\logger.txt",true));
	    	out.println(new java.util.Date().toLocaleString()+"::Form ExampleListener: " + message);
	    	out.close();
	    }
	    catch(Exception e)
	    {
	    	out.close();
	    	e.printStackTrace();
	    }
    }  
}
