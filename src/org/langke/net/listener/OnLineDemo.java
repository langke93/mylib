package org.langke.net.listener;
import java.util.* ;

import javax.servlet.* ;
import javax.servlet.http.* ;

public class OnLineDemo implements ServletContextListener,HttpSessionListener,HttpSessionAttributeListener
	{
		// 声明一个ServletContext对象
		private ServletContext application = null ;
		public void contextInitialized(ServletContextEvent sce)
		{
			// 容器初始化时，向application中存放一个空的容器
			this.application = sce.getServletContext() ;
			this.application.setAttribute("application_user_info",new ArrayList()) ;
		}

		public void contextDestroyed(ServletContextEvent sce)
		{}

		public void sessionCreated(HttpSessionEvent se)
		{}
		public void sessionDestroyed(HttpSessionEvent se)
		{
			// 将用户名称从列表中删除
			List l = (List)this.application.getAttribute("application_user_info") ;
			String session_user_name = (String)se.getSession().getAttribute("session_user_name") ;
			if(session_user_name!=null){
				l.remove(session_user_name) ;
				this.application.setAttribute("application_user_info",l) ;				
			}
		}

		public void attributeAdded(HttpSessionBindingEvent se)
		{
			// 如果登陆成功，则将用户名保存在列表之中
			List l = (List)this.application.getAttribute("application_user_info") ;
			if(l==null){
				this.application.setAttribute("application_user_info",new ArrayList()) ;
				 l = (List)this.application.getAttribute("application_user_info") ;
			}
			String session_name = (String)se.getName();
			if(session_name!=null){
				String session_user_name = (String)se.getSession().getAttribute("session_user_name");
				if(session_user_name!=null){
					l.add(session_user_name) ;
					this.application.setAttribute("application_user_info",l) ;
				}
			}
		}
		public void attributeRemoved(HttpSessionBindingEvent se)
		{}
		public void attributeReplaced(HttpSessionBindingEvent se)
		{}
	};
	/*
	  <listener>
		<listener-class>listener.OnLineDemo</listener-class>
	  </listener>
	*/