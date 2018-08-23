package org.langke.util.image;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 

public final class CreateBlobThumbnails {
	static String basePath = "E:/workspace/sq/ROOT";
	public static void main(String args[]){
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		String fileName = "";
		String targetImage = "temp.png";
		if(args.length>1){
			System.out.println("Usage: java BulkCreate basePath  ");
			return;
		}else if(args.length==1){
			basePath = args[0];
		}
		try {
			String sql = "";
			sql +="select seq_user_id,photo,length(photo) pos from sys_user_photo where length(photo)  != 0";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:datebase","bank","1");
			// Create a statement
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();
			//stmt3 = conn.createStatement();
			rs = stmt.executeQuery(sql);
			byte[] b = new byte[1024];   
			while (rs.next()) {
				Blob blobIn = rs.getBlob("photo".toUpperCase());  
				int seq_user_id = rs.getInt("seq_user_id".toUpperCase());
				long pos = rs.getLong("pos".toUpperCase());
				try {
					conn.setAutoCommit(false);
					stmt2.executeUpdate("UPDATE sys_user_photo set photo=EMPTY_BLOB() where seq_user_id="+seq_user_id+" ");//先清除原数据
					rs2= stmt2.executeQuery("SELECT photo FROM sys_user_photo WHERE seq_user_id="+seq_user_id+" FOR UPDATE");
					if(rs2.next()){

							oracle.sql.BLOB blobNew = (oracle.sql.BLOB) rs2.getBlob("photo".toUpperCase());
						
							Thumbnails.createBlobThumbnails(blobIn.getBinaryStream(), blobNew.getBinaryOutputStream(),256, 256,"PNG");
							/*BufferedOutputStream out = new BufferedOutputStream(blobNew.getBinaryOutputStream());

							BufferedInputStream in = new BufferedInputStream(new FileInputStream(targetImage));

							int c;

							while ((c=in.read())!=-1) {

							out.write(c);

							}

							in.close();

							out.close();*/
							
					}
					
					conn.commit();
					System.out.println(seq_user_id+",finish");
				}catch (Exception e) {
					conn.rollback();
					System.err.println(seq_user_id+",error");
					e.printStackTrace();
				}finally{
					rs2.close();
				}
			}
			
			Thread.sleep(0);//休息一下
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				rs.close();
				stmt.close();
				stmt2.close();
				//stmt3.close();
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
