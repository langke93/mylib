package org.langke.j2ee14.ch7;
import java.sql.*;
import java.io.*;

import oracle.jdbc.OracleResultSet;
import oracle.sql.BLOB;
/**
 *测试操作blob数据
 */
public class BlobTest
{
	Connection con=null;
	/**
	 *往数据库中添加BLOB数据
	 */
	public void addBlob()
	{
		try
		{

            //创建一个PreparedStatement实例
		   	PreparedStatement pstmt=con.prepareStatement("insert into blobtest values(1,?)");
		   	File file = new File("c:\\draw3D.jpg") ;
			FileInputStream fis = new FileInputStream(file);			
            //把输入流设置为预处理语句的对象。
			pstmt.setBinaryStream(1, fis, (int)file.length());		      
			//执行更新
			pstmt.executeUpdate();		            
			pstmt.close();
			fis.close();

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
	     	con.createStatement().execute("create table blobtest (id int ,pic blob,"+
	     	  "constraint pk_blobtest primary key(id));");
	     }
	     catch(Exception e)
	     {
	     	e.printStackTrace();
	     }
    }
    /**
     *从数据库中读取BLOB数据
     */
	public void getBlob()
	{
		
     try
	   {    
            //创建一个Statement实例
		   	Statement stmt=con.createStatement();
		   	ResultSet rst=stmt.executeQuery("select * from blobtest where id=1");
		   	rst.next();	
		   	//获得blob数据和它的输入流，然后通过输入流把数据写到文件中。	   	
		   	Blob blob = rst.getBlob("pic") ;
            FileOutputStream out=new FileOutputStream(new File("c:/test.jpg"));
            InputStream in=blob.getBinaryStream();
            int i;
            while((i=in.read())!=-1)
            out.write(i);
            //关闭输入、输出流.
            in.close();
            out.close();

        }
        catch(Exception e){
        	e.printStackTrace();
        	
        	}
    }
    public static void main(String[] args)throws Exception
    {
    	Class.forName("org.gjt.mm.mysql.Driver").newInstance();
    	BlobTest test=new BlobTest();
        test.con=java.sql.DriverManager.getConnection("jdbc:mysql://127.0.0.1/j2ee14","root","langke");
    	test.createTable();
    	test.addBlob();
    	test.getBlob();
    }
    public void addBlobForOracle(){
    	try {
			con.setAutoCommit(false);
			Statement stmt = con.createStatement();
			stmt.execute("insert into blobtest alues(1,empty_blob())");
			ResultSet rset = stmt.executeQuery("select pic from blobtest for update");
			BLOB blob = null;
				while(rset.next()){
					blob = ((OracleResultSet)rset).getBLOB(1);
					System.out.print(blob.getLength());
				}
			try {
				FileInputStream instream = new FileInputStream("c:/test1.jsp");
				OutputStream outstream = blob.getBinaryOutputStream();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
    	
    }
}
            
		
