package org.langke.util.xml;

import java.sql.*;
import java.io.*;

import org.langke.util.string.StringUtils;

public class XMLWriter {
	/**
	 * @param ResultSet
	 *            rs输入的结果集
	 * @return String 返回XML串
	 * @exception SQLException
	 */
	public String generateXML(final ResultSet rs) throws SQLException {
		final StringBuffer buffer = new StringBuffer(1024 * 4);
		if (rs == null)
			return "";
		if (!rs.next())
			return "";
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"); // XML的头部信息
		buffer.append("<ResultSet>\n");
		ResultSetMetaData rsmd = rs.getMetaData(); // 得到结果集的定义结构
		int colCount = rsmd.getColumnCount(); // 得到列的总数
		for (int id = 0; rs.next(); id++) { // 对放回的全部数据逐一处理
		// 格式为row id , col name, col context
			buffer.append("\t<row id=\"").append(id).append("\">\n");
			for (int i = 1; i <= colCount; i++) {
				int type = rsmd.getColumnType(i); // 获取字段类型
				buffer.append("\t\t<col name=\"" + rsmd.getColumnName(i)
						+ "\">");
				buffer.append(getValue(rs, i, type));
				buffer.append("</col>\n");
			}
			buffer.append("\t</row>\n");
		}
		buffer.append("</ResultSet>");
		rs.close();
		return buffer.toString();
	}

	public String generateSiteMap(final ResultSet rs,String filePath) throws SQLException {
		final StringBuffer buffer = new StringBuffer(1024 * 4);
		final StringBuffer siteMapIndexBuffer = new StringBuffer(1024 * 4);
		int indexFileNum =0; //文件数量
		int id;
		String fileName = "";
		String mapName = "";
		if (rs == null)
			return "";
		if (!rs.next())
			return "";
		/*********sitemap索引*********/
		siteMapIndexBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		siteMapIndexBuffer.append("   <sitemapindex xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");
		
		/*********sitemap索引*********/
		
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"); // XML的头部信息
		buffer.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");
		ResultSetMetaData rsmd = rs.getMetaData(); // 得到结果集的定义结构
		int colCount = rsmd.getColumnCount(); // 得到列的总数
		String htmlstr ="";
		htmlstr+="	<url>\n";
		htmlstr+="		<loc>http://china.zyw.com</loc>\n";
		htmlstr+="		<priority>1.00</priority>\n";
		htmlstr+="		<lastmod>"+StringUtils.formatDate(new java.util.Date(),"yyyy-MM-dd")+"T"+StringUtils.formatDate(new java.util.Date(),"HH:mm:ss"+"+08:00")+"</lastmod>\n";
		htmlstr+="		<changefreq>daily</changefreq>\n";
		htmlstr+="	</url>\n";
		buffer.append(htmlstr);
		
		for ( id = 0; rs.next(); id++) { // 对放回的全部数据逐一处理
		// 格式为row id , col name, col context
			buffer.append("\t<url>\n");
			for (int i = 1; i <= colCount; i++) {
				int type = rsmd.getColumnType(i); // 获取字段类型
				buffer.append("\t\t<" ).append(rsmd.getColumnName(i).toLowerCase()).append(">");
				buffer.append(getValue(rs, i, type));
				buffer.append("</" ).append(rsmd.getColumnName(i).toLowerCase()).append(">\n");
			}
			buffer.append("\t</url>\n");
			
			//每45000条写一个文件
			if(id>1&&id%45000==0){
				indexFileNum++;
				fileName = filePath+indexFileNum+".xml";
				mapName =  "sitemap"+indexFileNum+".xml";
				/*********sitemap索引*********/
				siteMapIndexBuffer.append("   <sitemap>\n");
				siteMapIndexBuffer.append("      <loc>http://china.zyw.com/"+mapName+"</loc>\n");
				siteMapIndexBuffer.append("      <lastmod>"+StringUtils.formatDate(new java.util.Date(),"yyyy-MM-dd")+"T"+StringUtils.formatDate(new java.util.Date(),"HH:mm:ss"+"+08:00")+"</lastmod>\n");
				siteMapIndexBuffer.append("   </sitemap>\n");
				/*********sitemap索引*********/
				
				buffer.append("</urlset>\n");
				
				//写列表文件sitemap1.xml
				try{
					FileWriter fileWriter=new FileWriter(fileName);
					fileWriter.write(buffer.toString());
					fileWriter.close();
					buffer.delete(0, buffer.length());//清除原数据
					buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"); // XML的头部信息
					buffer.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		//处理最后一个文件
		if(id>1&&id%45000==0){
			
		}else{
			indexFileNum++;
			fileName = filePath+indexFileNum+".xml";
			mapName =  "sitemap"+indexFileNum+".xml";
			/*********sitemap索引*********/
			siteMapIndexBuffer.append("   <sitemap>\n");
			siteMapIndexBuffer.append("      <loc>http://china.zyw.com/"+mapName+"</loc>\n");
			siteMapIndexBuffer.append("      <lastmod>"+StringUtils.formatDate(new java.util.Date(),"yyyy-MM-dd")+"T"+StringUtils.formatDate(new java.util.Date(),"HH:mm:ss"+"+08:00")+"</lastmod>\n");
			siteMapIndexBuffer.append("   </sitemap>\n");
			/*********sitemap索引*********/			
			buffer.append("</urlset>\n");
			//写列表文件sitemap1.xml
			try{
				FileWriter fileWriter=new FileWriter(fileName);
				fileWriter.write(buffer.toString());
				fileWriter.close();
				buffer.delete(0, buffer.length());//清除原数据
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		/*********sitemap索引*********/
		siteMapIndexBuffer.append("   </sitemapindex>");
		
		/*********sitemap索引*********/
		rs.close();
		return siteMapIndexBuffer.toString();
	}
	
	/**
	 * This method gets the value of the specified column
	 * 通用的读取结果集某一列的值并转化为String表达
	 * 
	 * @param ResultSet
	 *            rs 输入的纪录集
	 * @param int
	 *            colNum 第几列
	 * @param int
	 *            type 数据类型
	 */
	private String getValue(final ResultSet rs, int colNum, int type)
			throws SQLException {
		switch (type) {
		case Types.ARRAY:
		case Types.BLOB:
		case Types.CLOB:
		case Types.DISTINCT:
		case Types.LONGVARBINARY:
		case Types.VARBINARY:
		case Types.BINARY:
		case Types.REF:
		case Types.STRUCT:
			return "undefined";
		default: {
			Object value = rs.getObject(colNum);
			if (rs.wasNull() || (value == null))
				return ("null");
			else
				return (value.toString());
		}
		}
	}

	// 测试例程
	public static void main(String args[]) throws SQLException, IOException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:doone","system","123");
			System.out.println("connected.");
			// Create a statement
			Statement stmt = conn.createStatement();
	
			// Do the SQL "Hello World" thing
			System.out.println("here is the table rows view");
			ResultSet rs = stmt.executeQuery("select * from tab");
			while (rs.next()) {
				System.out.println(rs.getString(1)); // W?username
				System.out.println(rs.getString(2)); // W?password
			}
			// call toxml function
			System.out.println("here is the xml output");
			rs.close();
			rs = stmt.executeQuery("select * from tab");
			String xmlstring = (new XMLWriter()).generateXML(rs);
			System.out.println(xmlstring);
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
