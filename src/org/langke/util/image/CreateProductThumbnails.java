package org.langke.util.image;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public final class CreateProductThumbnails {
	static String basePath = "E:/workspace/sq/ROOT";
	public static void main(String args[]){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String fileName = "";
		Statement stmt2 = null;
		if(args.length==0){
			System.out.println("Usage: java BulkCreate basePath  ");
			return;
		}else{
			basePath = args[0];
		}
		try {
			String sql = "";
			sql +="select a.cp_id,b.cptp_path from t_product a,t_cptp b where  a.cptp_view_path is null and b.cptp_path!='/photo/not_up_photo.gif' and  a.cp_id = b.cp_id and b.status=1";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.2:1521:zyw","user","name");
			// Create a statement
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();
			rs = stmt.executeQuery(sql);
			String cp_id = null;
			String cptp_path = null;
			String targetPath = null;
			while (rs.next()) {
				cp_id = rs.getString("cp_id".toUpperCase());
				cptp_path = rs.getString("cptp_path".toUpperCase());
				if(cp_id == null ||cptp_path == null || cptp_path.equals("/photo/not_up_photo.gif")){//没有图片不生成缩略图
				}else{

					try {
						targetPath = basePath + cptp_path.substring(0, cptp_path.lastIndexOf('/'))+File.separator+"thumbnails"+File.separator;
						if(cp_id.equals("8363")) System.out.println(cptp_path);
						fileName = cptp_path.substring(cptp_path.lastIndexOf('/'));
						String p = basePath+cptp_path;
						File file2 = new File(p);
						if(!file2.exists()){// 如果文件不存在
							cptp_path = ("/photo/not_up_photo.gif");
						}else{
							File file1 = new File(targetPath);
							if(!file1.isDirectory()){
								file1.mkdirs();
							}
							Thumbnails.saveImageAsJpg(basePath+cptp_path, targetPath+fileName, 128, 128);
							cptp_path = cptp_path.substring(0, cptp_path.lastIndexOf('/'))+File.separator+"thumbnails"+fileName;
							cptp_path = cptp_path.replace("\\", "/");
							System.out.println(cp_id+","+cptp_path);
						}
					}catch (Exception e) {
						e.printStackTrace();
						System.err.println(cp_id+":"+e.getMessage());
						cptp_path = ("/photo/not_up_photo.gif");
					}
				}
				sql = "update t_product set cptp_view_path = '#cptp_view_path#' where cp_id=#cp_id#";
				sql = sql.replaceAll("#cptp_view_path#", cptp_path);
				sql = sql.replaceAll("#cp_id#", cp_id);
				stmt2.executeUpdate(sql);
			}
			
			Thread.sleep(990);//休息一下
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			try{
				rs.close();
				stmt.close();
				stmt2.close();
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
