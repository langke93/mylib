package org.langke.j2ee14.ch7;

import java.util.*;
import java.sql.*;


/**
 *自己编写的连接池
 */
public class MyConnectionPool
{
	static private MyConnectionPool instance;
	private Hashtable connections=new Hashtable();
	private Properties dbProps;
	
	/** 
	 * 返回唯一实例.如果是第一次调用此方法,则创建实例 
	 * 
	 * @return MyConnectionPool 唯一实例 
	 */ 
	 static synchronized public MyConnectionPool getInstance() { 
		 if (instance == null) { 
		 instance = new MyConnectionPool(); 
		 } 

		 return instance; 
	 } 
	 /**
	  *私有构造方法,通过getInstance来获得实例
	  */
	private MyConnectionPool()
	{
		System.out.println("MyConnectionPool()");
		try
		{
			ParseDatabaseConfig databaseConfig=new ParseDatabaseConfig();
			databaseConfig.parse("database.conf.xml");
			dbProps=databaseConfig.getProps();
			initializePool();
		}
		catch(Exception e)
		{
		}
	}	
    
    /**
     *获得一个连接，如果还有可用的连接，那么返回，
     *否则创建一个新的连接，并且返回
     */
	 public Connection getConnection()throws SQLException
	 {
	 	Connection con=null;
/*	 	Enumeration enum=connections.keys();
	 	System.out.println("现在已有的连接:"+connections.size());
	 	
		synchronized(connections)
		{
			while(enum.hasMoreElements())
			{
				con=(Connection)enum.nextElement();
				Boolean b=(Boolean)connections.get(con);
				//如果有没有被使用的连接，那么返回
				if(b==new Boolean(false)||b.equals(new Boolean(false)))
				{

					System.out.println("use a existcon");
					try
					{
						con.setAutoCommit(true);
					}
					catch(SQLException e)
					{

						e.printStackTrace();
						connections.remove(con);
						con=getNewConnection();
					}
					connections.put(con,Boolean.TRUE);
					return con;
				}//if
				
			}//while
*/			//如果到这里还没有可以使用的连接，那么就创建一个新的连接并且返回。
		     con=getNewConnection();
		     connections.put(con,Boolean.TRUE);
			return con;
		//}
	}
	/**
	 *初始化连接池
	 */
	public void initializePool()
	{
		//initialConnections是连接池中初始连接的数量
		int initialConnections=Integer.parseInt(dbProps.getProperty("initial-connections")); 
		try
		{
		  Class.forName(dbProps.getProperty("driver"));
		for(int i=0;i<initialConnections;i++)
		{
			Connection con=getNewConnection();
			connections.put(con,Boolean.FALSE);
		}
	    }
		catch(ClassNotFoundException e)
		{
		  e.printStackTrace();	
		}
			
	}
	/**
	 *回收一个连接
	 */
	public void returnConnection(Connection con)
	{
		
		if(connections.containsKey(con))
		{
			try
			{
				if(!con.isClosed())
				connections.put(con,Boolean.FALSE);
				else
				{
					connections.remove(con);
					connections.put(getNewConnection(),Boolean.FALSE);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}	
	/**
	 *创建一个新的连接
	 */
	private Connection getNewConnection() 
	{

       System.out.println("new conn...........");
		Connection con=null;
		try
		{		
			Class.forName(dbProps.getProperty("driver"));
			con=java.sql.DriverManager.getConnection(
			      dbProps.getProperty("url"),
			      dbProps.getProperty("user"),
			      dbProps.getProperty("password"));
		}
		catch(java.lang.ClassNotFoundException e)
		{
			System.err.println("Not Found Driver:"+dbProps.getProperty("driver"));
		}
		//使用配置的属性创建一个连接。
	
	    catch(SQLException e)
	    {
	    	e.printStackTrace();
	    }
	    return con;	
	}
}

