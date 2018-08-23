package org.langke.data.imp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnAccess {
	private Connection conn=null;
	final String filePath = "E:/langke/资源网/E-DATA/中国制造企业名录/2008年化学药品原药制造企业老总手机号码通讯名录.mdb";
	final String strurl="jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="+filePath+";";

	public  Connection getConnect() throws Exception{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		conn=DriverManager.getConnection(strurl);
		return this.conn;
	}	
	public  Connection getConnect(String filePath) throws Exception{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		conn=DriverManager.getConnection("jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="+filePath+";");
		return this.conn;
	}
	public void close() throws Exception{
		conn.close();
	}
	
	/**
	 * 测试查询
	 *
	 */
	public static void queryDataTest(){
		ConnAccess conn = new ConnAccess();
		Statement stmt = null;
		ResultSet rs =null;
		try{
			stmt=conn.getConnect().createStatement();
			String sql = "select * from 企业名录";
			rs = stmt.executeQuery(sql);
			while (rs.next()){
				int row_count = 0;
				row_count =	rs.getMetaData().getColumnCount();
				System.out.println((rs.getInt("职工人数")));
				/*for(int i=1;i<=row_count;i++){
					System.out.print(rs.getString(rs.getMetaData().getColumnName(i))+"\t");					
				}*/
				System.out.println();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				rs.close();
				stmt.close();
				conn.close();
			}catch(Exception e){
				e.printStackTrace();				
			}
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		queryDataTest();
	}
}
