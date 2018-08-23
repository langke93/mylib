package org.langke.util.xml;

import java.sql.*;

/**
 * 站点地图
 * @author lee
 *
 */

public class SiteMap {
	public static void main(String args[]){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String filePath = "/home/tomcat/sq/webroot/sitemap";
		try {
			String sql = "";
			//"select 'http://china.zyw.com'||path as loc,0.5 as priority ,to_char(adddate,'yyyy-mm-dd')||'T'||to_char(adddate,'hh24:mi:ss')||'+08:00' lastmod,'daily' changefreq from t_newsins where 1=1 order by lastmod desc";
			sql += " select * from ( ";
			sql += " 		select 'http://china.zyw.com'||path as loc,0.5 as priority ,to_char(adddate,'yyyy-mm-dd')||'T'||to_char(adddate,'hh24:mi:ss')||'+08:00' lastmod,'daily' changefreq from t_newsins where DEL_STATUS=1 "; 
			sql += " 		union ";
			sql += " 		select 'http://china.zyw.com/store/out/'||c_id||'.htm' as loc,0.5 as priority,to_char(JRSJ,'yyyy-mm-dd') || 'T' ||to_char(JRSJ, 'hh24:mi:ss') || '+08:00' lastmod, 'daily' changefreq from t_company,memlst where c_id in(select c_id from t_shop ) and memlst.u_id=t_company.u_id and  sh_status = 0   and memlst.u_lck = 0 ";
			sql += " 		union ";
			sql += " 		select  'http://china.zyw.com/product/deail/'||cp_id||'/'||a.c_id||'.htm' as loc,0.5 as priority,to_char(CP_FBSJ,'yyyy-mm-dd') || 'T' ||to_char(CP_FBSJ, 'hh24:mi:ss') || '+08:00' lastmod, 'daily' changefreq from t_product a,t_company b ,memlst c where a.c_id=b.c_id and b.u_id=c.u_id and a.cpsh = 0 and c.u_lck = 0 ";
			sql += " 		 )order by lastmod desc ";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.2:1521:zyw","user","pwd");
			// Create a statement
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			String xmlstring = (new XMLWriter()).generateSiteMap(rs,filePath);
			System.out.println(xmlstring);

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
