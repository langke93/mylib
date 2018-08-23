package org.langke.j2ee14.ch5;
import java.util.Properties;
import javax.naming.*;

public class FirstJndiTest {
	
	private InitialContext ic;
	 
	public   FirstJndiTest()
	{
	 try {
        ic = getInitialContext();
      } 
      catch (Exception e) {
          e.printStackTrace();
      }
    } 
      //创建初始的上下文环境
    public InitialContext getInitialContext()throws Exception
    {
    	try
    	{
    		  Properties props = new Properties();

    		 props.put(Context.INITIAL_CONTEXT_FACTORY,
              "com.sun.jndi.fscontext.RefFSContextFactory");
             props.put(Context.PROVIDER_URL, "file:///");
            
        // Create the initial context from the properties we just created
      InitialContext initialContext = new InitialContext(props);
	    	return initialContext;
	    	
	    }
	    catch(Exception e)
	    {
	    	System.out.print("初始化异常:"+e);
	    	throw e;
	    }
    } 
         
  public static void main(String[] args) {   
         String name = "pro/proxy.jsp";
      if (args.length > 0) 
      {
      	     	
      	 name = args[0];     
       }
       System.out.println("寻找名字为： "+name);  
       
        FirstJndiTest test=new FirstJndiTest();   
    try {
      
      // Look up the object
      Object obj = test.ic.lookup(name);
      if (name.equals(""))
        System.out.println("在初始上下文中查找");
      else
        System.out.println(name + " 绑定到: " + obj);
    }
    catch (NamingException ex) {
      System.out.println("遇到名字解析异常：");
      ex.printStackTrace();
    }
  }
  
}
