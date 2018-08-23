package org.langke.net.network.monitor; 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
public class ServiceCheck {

	/**
	 * @param args
	 * 检查服务和接口状态
	 */
	public static void main(String[] args) {
		http_client_();
		config_check();
		//check();
	}
	public static void http_client_(){
		String[] arr_url = {"http://10.29.60.195:8080/woyo_service_check.jsp"};
		GetMethod  method = new GetMethod (arr_url[0]);
        method.getParams().setVirtualHost("upv1.woyo.com");//自定义Host
		method.setRequestHeader("User-agent", "Mozilla/4.0 (compatible; MSIE 10.0; Windows NT 8.1; .NET CLR 2.0.50727)");
		HttpClient client =new HttpClient();
		// 设置代理服务器的IP地址和端口
		client.getHostConfiguration().setProxy("192.168.0.103", 3128);
		String res; 
		while(true){
			 try {
				int result = client.executeMethod(method);
				System.out.println(result);
				res = method.getResponseBodyAsString().trim();
				System.out.println(res);
				Header[] headers = method.getResponseHeaders();
				
				if(result == HttpStatus.SC_OK){
					for(Header header:method.getResponseHeaders())
						System.out.println(header.getName()+":"+header.getValue());
				}else{
					System.err.println(method.getStatusText());
				}
				Thread.sleep(1000);
				if(res.indexOf("ok")==-1){
					for(Header header:headers){
						System.out.println(header);
					}
					headers = method.getRequestHeaders();
					for(Header header:headers){
						System.out.println(header);
					}
					break;
				}				
			}catch(InterruptedException e){
				e.printStackTrace();
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				method.releaseConnection();
			}
		}
	}
	public static void config_check(){
		//String[] arr_url = {"http://192.168.200.62:8080/woyo_service_check.jsp"};
		String[] arr_url = {"http://10.29.60.195:8080/woyo_service_check.jsp"};
		String url = null;
		long startTime = System.currentTimeMillis();
		HttpURLConnection conn = null;		
		URL url_c ;
		StringBuffer data = new StringBuffer("");
		boolean keep = true;
		String proxy = "192.168.0.103", port = "3128";/*
        Properties systemProperties = System.getProperties();    
       systemProperties.setProperty("http.proxyHost",proxy);            
       systemProperties.setProperty("http.proxyPort",port);   */
		for(int i=0;keep;){
			try{//检查接口状态
				url = arr_url[i];
				System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
				conn = (HttpURLConnection) new URL(url).openConnection();
				conn.setRequestProperty( "User-agent", "Mozilla/9.0 (compatible; MSIE 10.0; Windows NT 8.1; .NET CLR 2.0.50727)" );
		        conn.setRequestProperty("Host", "upv1.wy");

				//conn.setDoInput(true);
				//conn.setDoOutput(true);	
				conn.setConnectTimeout(9000);			
				int code = 0;
				try {
					code = conn.getResponseCode();
				} catch (Exception e) {				//	请求失败
					e.printStackTrace();
				}
				InputStream is = null;
				InputStreamReader isr = null;
				BufferedReader br = null;
				try{
					if (code == HttpURLConnection.HTTP_OK) {
						is = conn.getInputStream();
						keep = false;
					}else{
						System.err.println(conn.getResponseCode()+conn.getResponseMessage());
						is = conn.getErrorStream();
						keep = false;
					}
					isr = new InputStreamReader(is);
					br = new BufferedReader(isr);
					String tmp;					
					System.out.println(i+":"+url);
					while ((tmp = br.readLine()) != null) {
						data.append(tmp);
					}
					System.out.println(data);
					if(data.indexOf("ok")==-1){
						System.err.println(conn.getResponseCode()+conn.getResponseMessage());
						keep = false;
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{//关闭相关流
					data.delete(0, data.length());
					if(br!=null){
						br.close();
						br = null;
					}
					if(isr!=null){
						isr.close();
						isr = null;						
					}
					if(is!=null){
						is.close();
						is = null;						
					}
				}
				Thread.sleep(500);
			}catch(Exception e){
				keep = false;
				e.printStackTrace();		
			}finally{//关闭连接 
				if(conn!=null){
					conn.disconnect();
					conn = null;
				}
			}		
		}
		System.out.println("done");
	}
	public static void check(){
		String msg = "ok";
		String[] arr_url = {
				"http://upv1.woyo.com/base.action"
				//"http://hadoop3:9209/club/_id/_search?q=category_id:1"
				,"http://upv1.woyo.com/woyo_service_check.jsp"
				//,"http://upv1.woyo.com/i_v2/videoInfo_getLoginUserInfo.action?uid=155&info=img,name,regname,nickname,img_server"
				,"http://upv1.woyo.com/BFU_authorizedVerify.action?user_id=-1&md5_key=414b4f143cd5ddc95e3f9f4ad9d88647"
				,"http://upv1.woyo.com/BFU_uploadDone.action?user_id=-1&path=/abc"
				,"http://upv1.woyo.com/json2/videoAlbumInfo_getAllVideoPlayUrl.action?videoId=0f7876117c24428dae72f85010967273"
				,"http://upv1.woyo.com/jsonkey/getVideoInfo.do?id=0f7876117c24428dae72f85010967273"
				,"http://upv1.woyo.com/i_v2/json_v2/videoInfo_backHtmlModule.action?model=sml&callback=jsonp1294202364532&widget_id=00015400004131&is_self=true&ticket=AT-155-6d1dde53-fd00-4781-b94d-5591f5512bd9&target_userid=155&params={}&other={}"
				,"http://upv1.woyo.com/jsonkey/getCategory.action"
				,"http://upv1.woyo.com/json2/videoAlbumInfo_albumList.action?userId=155",
				"http://upv1.woyo.com/LiveVideoUploadAction.jsp",
				"http://upv1.woyo.com/i/json2/videoSite_getChannelCode.action",
				"http://upv1.woyo.com/i/json2/videoSite_videoList.action?userId=155",
				"http://upv1.woyo.com/i/json2/videoSite_newVideoInfo.action?userId=155"
		};
		String url = null;
		long startTime = System.currentTimeMillis();
		HttpURLConnection conn = null;		
		StringBuffer data = new StringBuffer("");
		for(int i=0;i<arr_url.length;i++){
			try{//检查接口状态
				url = arr_url[i];
				conn = (HttpURLConnection) new URL(url).openConnection();
				//conn.setRequestProperty( "User-agent", "Mozilla/4.0 (compatible; MSIE 10.0; Windows NT 8.1; .NET CLR 2.0.50727)" );
				conn.setDoInput(true);
				conn.setDoOutput(true);	
				conn.setConnectTimeout(3000);
				int code = 0;
				try {
					code = conn.getResponseCode();
				} catch (Exception e) {				//	请求失败
					e.printStackTrace();
				}
				if (code == HttpURLConnection.HTTP_OK) {
					InputStream is = null;
					InputStreamReader isr = null;
					BufferedReader br = null;
					try{
						is = conn.getInputStream();
						isr = new InputStreamReader(is);
						br = new BufferedReader(isr);
						String tmp;					
						System.out.println(i+":"+url);
						while ((tmp = br.readLine()) != null) {
							//data.append(tmp);
							System.out.println(tmp);
						}
					}catch(Exception e){
						e.printStackTrace();
					}finally{//关闭相关流
						if(br!=null){
							br.close();
							br = null;
						}
						if(isr!=null){
							isr.close();
							isr = null;						
						}
						if(is!=null){
							is.close();
							is = null;						
						}
					}
				}
				
			}catch(Exception e){
				msg += "\n"+e.getMessage();
				e.printStackTrace();		
			}finally{//关闭连接 
				if(conn!=null){
					conn.disconnect();
					conn = null;
				}
			}		
		}
		System.out.println("done");
	}
}
