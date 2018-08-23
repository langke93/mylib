package org.langke.j2ee14.ch7;
import java.util.*;
import java.sql.*;
/**
 *实现业务逻辑的类
 *@implements:EmployeeBusiness
 */
public class EmployeeBusinessImpl implements EmployeeBusiness
{
   /**
    *查找所有的雇员的信息
    * @return Collection
    */
   public Collection getAllEmployees()throws EmployeeException
   {
   	  String sql="select * from Employee";
   	  try
   	    {
   	    	//返回执行结果
   	    	return executeQuery(sql);
   	    }
   	    catch(EmployeeException e)
   	     {
   	      	throw e;
   	   }
   } 
   
   /**
    *按照雇员的id查询雇员信息
    * @param name
    * @return Employee
    */
   public Employee getTheEmployeeDetail(int id)throws EmployeeException
   {
   	    String sql="select * from Employee where id="+id;
   	    try
   	    {
   	    	//executeQuery返回的是Collection，需要把它转换成值对象。
   	    	Collection temp=executeQuery(sql);  
   	    	Iterator it=temp.iterator();
   	    	if(it.hasNext()) 	    
   	        return (Employee)it.next();
   	       else
   	       throw new EmployeeException("没有这个id的雇员");//如果没有查找到这个雇员，抛出一个异常。
   	    }
   	    catch(EmployeeException e)
   	     {
   	      	throw e;
   	    }
   	}
   	  
   
   /**
    *按照性别查找雇员信息
    * @return Collection
    */
   public Collection getEmployeeBySex(int sex)throws EmployeeException
   {
   	   String sql="select * from Employee where sex="+sex;
   	   try
   	   {
   	   	//返回执行结果
   	   	return executeQuery(sql);
   	  }
   	  catch(EmployeeException e)
   	     {
   	      	throw e;
   	    }
   	  
   }
   /**
    *增加一个雇员
    *@param emp
    */
   public void addEmployee(Employee emp)throws EmployeeException
   {
   	  String sql="insert into employee values("+emp.id+",'"+emp.name+"',"+emp.age+","+emp.sex+","+emp.salary+",'"+emp.description+"')";
   	  //调用execute方法执行SQL语句。
   	  execute(sql);
   
   }
   
   /**
    *删除一个雇员
    *@param id
    */
   public void deleteEmployee(int id)throws EmployeeException
   {
   	  String sql="delete from employee where id="+id; 
   	  //调用execute方法执行SQL语句。
   	  execute(sql);   
   }
   
   /**
    *帮助方法，用于查找数据库，返回值对象。
    */
   private Collection executeQuery(String sql)throws EmployeeException 
   {
   	 Connection con=null;
   	  try
   	  {
   	  	  //创建空的colleciton对象，它用于容纳查询的结果。
   	  	  Collection ret=new ArrayList();
	   	  //从连接池获得一个连接
	   	  con=MyConnectionPool.getInstance().getConnection();
	   	  Statement stmt=con.createStatement();
	   	  ResultSet rst=stmt.executeQuery(sql);
	   	  //把执行结果转换成值对象，然后保存在Collection中
	   	  while(rst.next())
	   	  {
	   	   	Employee emp=new Employee();
	   	  	emp.id=rst.getInt("id");
	   	  	emp.name=rst.getString("name");
	   	  	emp.sex=rst.getInt("sex");
	   	  	emp.age=rst.getInt("age");
	   	  	emp.salary=rst.getFloat("salary");
	   	  	emp.description=rst.getString("description");
	   	  	ret.add(emp);
	   	  }
	   	  rst.close();
	   	  //返回连接到连接池
	   	  MyConnectionPool.getInstance().returnConnection(con);
	   	  return ret;
	   	 }
	   	 catch(Exception e)
	   	 {
	   	 	e.printStackTrace();
	   	 	if(con!=null) MyConnectionPool.getInstance().returnConnection(con);
	   	 	throw new EmployeeException(e);
	   	 }
	  }
	  /**
	   *帮助方法，用于执行不返回ResultSet 的SQL语句
	   */
	  public void execute(String sql)throws EmployeeException 
	  {
	  	Connection con=null;
	  	try
   	     {
          //获得一个连接
	   	  con=MyConnectionPool.getInstance().getConnection();
	   	  Statement stmt=con.createStatement();
	   	  //执行SQL。
	   	  stmt.execute(sql);
	   	  //返回连接到连接池
	   	  MyConnectionPool.getInstance().returnConnection(con);
	   	 }
	   	 catch(Exception e)
	   	 {
	   	 	e.printStackTrace();
	   	 	if(con!=null) MyConnectionPool.getInstance().returnConnection(con);
	   	 	throw new EmployeeException(e);
	   	 }
	  }   	 
}
	   	 
	   	 