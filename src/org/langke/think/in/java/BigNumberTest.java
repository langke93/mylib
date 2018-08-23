package org.langke.think.in.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class BigNumberTest {

	public static void main(String args[]){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String filePath = "/home/tomcat/sq/webroot/sitemap";
		try {
			String sql = "select numtest from test";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.119:1521:zx","sq","sq");
			// Create a statement
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){

				System.out.println(rs.getString(1));
			}

		} catch (Exception e) {
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
}
