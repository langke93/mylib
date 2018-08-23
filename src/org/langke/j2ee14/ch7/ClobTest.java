package org.langke.j2ee14.ch7;
import java.sql.*;
import java.io.*;

/**
 *测试操作CLOB数据
 */
public class ClobTest
{
	Connection con=null;
	String encoding = System.getProperty("file.encoding");
	/**
	 *往数据库中添加CLOB数据
	 */
	public void addClob()
	{
		try
		{
/**
 * getCharacterStream() 方法返回按unicode编码的输入流(java.io.Reader对象) 
	getAsciiStream() 方法返回按ASCII编码的输入流(java.io.InputStream对象) 
	所以如果你的数据库中有可能存储中文字符的话,就要使用前一个方法. 
 */
            //创建一个PreparedStatement实例
		   	PreparedStatement pstmt=con.prepareStatement("insert into clobtest values(1,?)");
		   	File file = new File("c:\\sleepless in java.txt") ;
			//FileInputStream fis = new FileInputStream(file);			
            FileReader rd = new FileReader(file);
		   	//把输入流设置为预处理语句的对象。
			pstmt.setCharacterStream(1, rd, (int)file.length());		      
			//执行更新
			pstmt.executeUpdate();		            
			pstmt.close();
			rd.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	 /**
	  *创建一个表
	  */
     public void createTable()
     {
     	try
     	{
	     	con.createStatement().execute("create table clobtest (id int ,myfile clob)");
	     }
	     catch(Exception e)
	     {
	     	e.printStackTrace();
	     }
    }
    /**
     *从数据库中读取CLOB数据
     */
	public void getClob()
	{
		
     try
	   {    
            //创建一个Statement实例
		   	Statement stmt=con.createStatement();
		   	ResultSet rst=stmt.executeQuery("select * from clobtest where id=1");
		   	rst.next();	
		   	//获得clob数据和它的输入流，然后通过输入流把数据写到文件中。	   	
		   	Clob clob = rst.getClob("myfile") ;
		   	//lee
	         Reader is = clob.getCharacterStream();
	         BufferedReader br = new BufferedReader(is);
	         String s = br.readLine();
	         String content="";
	         while(s!=null){
		         //byte[] temp = s.getBytes("iso-8859-1");
		         //s = new String(temp);
		         content += s+"\n";
		         s=br.readLine();
	         }
	         br.close();
	         //System.out.println(content);
		   	//lee
            FileOutputStream out=new FileOutputStream(new File("c:/k2.txt"));
            PrintStream ps = new PrintStream(out);
            ps.print(content);
            ps.close();
            out.close();

        }
        catch(Exception e){
        	e.printStackTrace();
        	
        	}
    }
    public static void main(String[] args)throws Exception
    {
    	 ClobTest test=new ClobTest();
    	 Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		 test.con=java.sql.DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:doone","doone","doone");
     
    	  //test.createTable();
          //test.addClob();
    	  test.getClob();
    }
}
            
		
