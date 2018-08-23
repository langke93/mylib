package org.langke.data.imp;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
/**
 * 中国制造企业名录
 * @author lee
 *
 */
public class ImportCompanyDemoMake {
	/**
		0 注册成功
		1 注册失败
		2 注册成功，但邮件发送失败
		3 验证码错误
		4 邮件地址已经被注册
		5 用户名已被使用
		10 公司名已被使用
	 */
	final  static String filePath = "D:/langke/E-DATA/41.82万办公文具和礼品国际采购商数据.mdb";
	final static String target_url = "http://china.zyw.com/member.do";
	static int start_num = 922714+1;//crm9999 每换个文件，这个值要重新从数据库取,select max(to_number(replace(u_nme,'crm',''))) from memlst where u_nme like 'crm%' and u_nme!='crm2002@sohu.com';
	
	 public static void main(String[] args) throws Exception {
		 ImportCompanyDemoMake.ImpCompanyData();
	}

	public static void ImpCompanyData(){
		ConnAccess conn = new ConnAccess();
		Statement stmt = null;
		ResultSet rs =null;
		String content = null;
        HttpClient client = new HttpClient();
        Random rd = new Random();
		try{
	        
			stmt=conn.getConnect(filePath).createStatement();
			String sql = "select * from 礼品";
			rs = stmt.executeQuery(sql);
			int row_count = 0;
			loop1:
			while (rs.next()){
				start_num++;
				row_count++;
				if(start_num<928629) continue;
				//if(start_num==922714+6000) break;
				//row_count =	rs.getMetaData().getColumnCount();
				//for(int i=1;i<=row_count;i++){
					//System.out.print(rs.getString(rs.getMetaData().getColumnName(i))+"\t");		
				try{
					PostMethod method = new PostMethod(target_url);
					method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
					  int result = client.executeMethod(method);
				      if (result == HttpStatus.SC_OK) {
				            method = new PostMethod(target_url);
				            method.getParams().setContentCharset("GBK");//中文编码MD真恶心
				            String c_mc = rs.getString("单位名称");
				            String email = "";//rs.getString("邮件地址");
				            String mobile =  "";//rs.getString("电话");
				            String u_nme = "crm"+start_num;
				            String lxr = rs.getString("法人");
				            if(lxr!=null) lxr =lxr.replaceAll("添加为商业伙伴", "");
				            if(email!=null &&email.indexOf("@")!=-1){
				            	email = email.replaceAll(" ", "");//去掉空格
				            	u_nme = email;//如果有邮箱，就作为用户名
				            }
				            if(mobile!=null && mobile.indexOf("-")!=-1){
				            	mobile = mobile.replaceAll("-", "");
				            	if(mobile.indexOf(" ")!=-1)
				            		mobile = mobile.substring(0,mobile.indexOf(" "));
				            }
				            method.setRequestBody(new NameValuePair[] {
				                    new NameValuePair("method", "memberRegiest"),
				                    new NameValuePair("u_nme", u_nme),
				                    new NameValuePair("u_pss", "123456"),
				                    new NameValuePair("c_mc",  (c_mc)),
				                    new NameValuePair("name", (lxr)),
				                    new NameValuePair("email", email),
				                   // new NameValuePair("job", (rs.getString("职位"))),//职位为空，联系人有职位信息
				                    new NameValuePair("tel",(rs.getString("电话"))),
				                    //new NameValuePair("fax", (rs.getString("Fax"))),
				                    new NameValuePair("mobile",mobile),
				                    //new NameValuePair("c_type",getCompanyType(rs.getInt("经济代码"))),
				                    //new NameValuePair("c_jj",rs.getString("公司简介")),
				                    new NameValuePair("jy_add", (rs.getString("地址"))),
				                   // new NameValuePair("yb", (rs.getString("邮政编码"))),
				                    new NameValuePair("shop_mc", (c_mc)),
				                   // new NameValuePair("c_url", (rs.getString("公司主页"))),
				                   // new NameValuePair("c_zycp", (rs.getString("传真或备注"))),
				                    new NameValuePair("cl_time", (rs.getString("成立日期"))),
				                    //new NameValuePair("zc_money", (rs.getString("注册资金")+"")),
				                    //new NameValuePair("c_nyye", (rs.getString("AnnualRevenue")+"")),
				                    //new NameValuePair("c_ygrs", (String.valueOf(rs.getString("EmployeeNumber"))))
				            });
				            if (client.executeMethod(method) == HttpStatus.SC_OK) {
				            	content = row_count+","+method.getResponseBodyAsString().trim()+"\n";
				                System.out.print(content);
				            	FileUtil.FileLog(filePath.substring(filePath.lastIndexOf("/")+1, filePath.lastIndexOf("."))+".log",content);
				            }
				        }

				        method.releaseConnection();
				        if(content!=null && content.indexOf("regiestType(0)")!=-1)
				        	Thread.sleep(3000);//休息3秒，防止邮件发不出去
				}catch(Exception e){
					FileUtil.FileLog(filePath.substring(filePath.lastIndexOf("/")+1, filePath.lastIndexOf("."))+".log",row_count+","+e.getMessage()+"\n");
					e.printStackTrace(); 
					Thread.sleep(30000);//休息30秒,处理网络问题
					continue loop1;
				}
				//System.out.println();
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
	
	public static String getCompanyType(int type_id){
		//String type_list = "个体经营~1|  私营独资企业~2|  私营合伙企业~3|  私营有限责任公司~4|  私营股份有限公司~5|  国有企业~6|  集体企业~7|  股份合作企业~8|  联营企业~9|  有限责任公司~10|  有限责任公司(国有独资)~11|  一人有限责任公司~12|  其他有限责任公司~13|  股份有限公司~14|其他内资企业~15|  三来一补~16|法人分支机构~17|  合资经营企业(港或澳、台资)~18|  合作经营企业(港或澳、台资)~19|  港、澳、台商独资经营企业~20|  港、澳、台商投资股份有限公司~21|  中外合资经营企业~22|  中外合作经营企业~23|  外资企业~24|其他~25";
		switch(type_id){
			case 170: type_id=1;break;
			case 171: type_id=2;break;
			case 172: type_id=3;break;
			case 173: type_id=4;break;
			case 174: type_id=5;break;
			case 110: type_id=6;break;
			case 120: type_id=7;break;
			case 130: type_id=8;break;
			case 140: type_id=9;break;
			case 150: type_id=10;break;
			case 151: type_id=11;break;
			case 159: type_id=13;break;
			case 160: type_id=14;break;
			case 100: type_id=15;break;
			case 210: type_id=18;break;
			case 220: type_id=19;break;
			case 230: type_id=20;break;
			case 240: type_id=21;break;
			case 310: type_id=22;break;
			case 320: type_id=23;break;
			case 330: type_id=24;break;
			case 200: type_id=24;break;
			case 300: type_id=24;break;
			case 340: type_id=24;break;
			default:type_id=25;
		}
		return String.valueOf(type_id);
	}
}
