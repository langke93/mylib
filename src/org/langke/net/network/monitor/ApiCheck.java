package org.langke.net.network.monitor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Random;


public class ApiCheck {

	public static void check(){
		String[] arr_url = {
				"http://10.10.21.126:9101/shopConfig/getShopAdminlist"
		};
		Random random = new Random();
		int min=1,max=2691;
		int randNumber = random.nextInt(max-min+1)+min;
		String[] parms = {"{\"userId\":"+randNumber+"}"};
		String url = null;
		long startTime = System.currentTimeMillis();
		HttpURLConnection conn = null;		
		StringBuffer data = new StringBuffer("");
		int code = 0;
		String logLevel = "INFO";
		for(int i=0;i<arr_url.length;i++){
			try{//检查接口状态
				url = arr_url[i];
				conn = (HttpURLConnection) new URL(url).openConnection();
				//conn.setRequestProperty( "User-agent", "Mozilla/4.0 (compatible; MSIE 10.0; Windows NT 8.1; .NET CLR 2.0.50727)" );
				conn.setDoInput(true);
				conn.setDoOutput(true);// 是否输入参数
				conn.setRequestMethod("POST");// 提交模式
		        conn.setConnectTimeout(3000);//连接超时 单位毫秒
		        conn.setReadTimeout(3000);//读取超时 单位毫秒
		        // 表单参数与get形式一样
		        byte[] bypes = parms[i].getBytes();
		        conn.getOutputStream().write(bypes);// 输入参数
				try {
					code = conn.getResponseCode();
				} catch (Exception e) {				//	请求失败
					e.printStackTrace();
				}
				InputStream is = null;
				InputStreamReader isr = null;
				BufferedReader br = null;
				if (code == HttpURLConnection.HTTP_OK) {
					try{
						is = conn.getInputStream();
						isr = new InputStreamReader(is);
						br = new BufferedReader(isr);
						String tmp;					
						//System.out.println(i+":"+url);
						while ((tmp = br.readLine()) != null) {
							data.append(tmp);
							//System.out.println(tmp);
						}
					}catch(Exception e){
						System.out.println("ERROR:"+Calendar.getInstance().getTime()+url+parms[i]+"data:"+data);
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
				}else{
					is = conn.getErrorStream();
					if(is!=null){
						isr = new InputStreamReader(is);
						br = new BufferedReader(isr);
						String tmp;					
						while ((tmp = br.readLine()) != null) {
							data.append(tmp);
						}
						System.out.println("ERROR:"+data);
					}
				}
				
			}catch(Exception e){
				System.out.println("ERROR:"+Calendar.getInstance().getTime()+url+parms[i]);
				e.printStackTrace();		
			}finally{//关闭连接 
				if(conn!=null){
					conn.disconnect();
					conn = null;
				}
				long usetime = System.currentTimeMillis()-startTime;
				
				if(usetime>100l)
					logLevel = "WARN:";
				else
					logLevel = "INFO:";
				System.out.println(logLevel+Calendar.getInstance().getTime()+url+parms[i]+",code:"+code+",time:"+usetime);
			}		
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		while(true){
			check();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}

	}

}
