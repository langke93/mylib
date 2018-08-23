package org.langke.net.hessian;

import com.caucho.hessian.server.HessianServlet;

public class BasicService extends HessianServlet implements BasicAPI {
	  private String _greeting = "Hello, world";

	  public void setGreeting(String greeting)
	  {
	    _greeting = greeting;
	  }

	  public String hello()
	  {
	    return _greeting;
	  }
	}
