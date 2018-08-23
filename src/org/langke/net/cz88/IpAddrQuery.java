package org.langke.net.cz88;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
 

public class IpAddrQuery {
	/**
	 * 查询IP所在的地址
	 */
	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		String ip = null;
		String addr = "";
		try{
			sql +="select ip  from iptab order by ip";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.9.1:1521:xe","bank","bank");
			// Create a statement
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ip = rs.getString("ip");
				addr = IPSeeker.getInstance().getCountry(ip);
				System.out.println(ip+","+addr);
			}
			
			//Thread.sleep(990);//休息一下
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
