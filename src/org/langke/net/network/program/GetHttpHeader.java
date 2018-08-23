package org.langke.net.network.program;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
 /**
  * 取HTTP报头信息
  * @author lizz
  *
  */
public class GetHttpHeader {

    public static String downFile(String address)
    {
        String content = "";
    	String encoding = System.getProperty("file.encoding");
        try
        {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.connect();
            String contentType  = con.getContentType();
            if(contentType!=null){
            	if(contentType.indexOf("charset")!=-1)
            		encoding = contentType.substring(contentType.indexOf("charset=")+8, contentType.length());
            }          	
            InputStream fi = con.getInputStream();
            int ch;
            while((ch = fi.read()) != -1) 
                content = (new StringBuilder(String.valueOf(content))).append((char)ch).toString();
            fi.close();
            con.disconnect();
            content = new String(content.getBytes("iso-8859-1"), encoding);
        }
        catch(SocketTimeoutException e)
        {
        	content+=(e.getMessage());
        }
        catch(NullPointerException e)
        {
        	content+=(e.getMessage());
        }
        catch(IOException e)
        {
        	content+=(e.getMessage());
        }
        return content;
    }	
    /**
     * 用HttpURLConnection取报头信息
     * @param address
     * @return
     */
    public static String getHeader(String address){
		String content=address+"\n";
    	String encoding = System.getProperty("file.encoding");
    	try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.connect();
            String contentType  = con.getContentType();
            if(contentType!=null){
            	if(contentType.indexOf("charset")!=-1)
            		encoding = contentType.substring(contentType.indexOf("charset=")+8, contentType.length());
            }          	 
            Map<String, List<String>>  header =  con.getHeaderFields();
            //用Map.Entry遍历Map
            Set<Entry<String, List<String>>> params = header.entrySet();    
            for (Map.Entry entry : params) {    
                content+=(entry.getKey() + " : " + entry.getValue())+"\n";    
            }   
           // content +=  con.getContentType();
            
            con.disconnect();
          //  content = new String(content.getBytes("iso-8859-1"), encoding);
        }
        catch(Exception e)      {
        	e.printStackTrace();
        }
        return content;
    }
    public static String getHttpClientHeader(String url){
		String content = "";
    	try{
    		PostMethod method = new PostMethod(url);
    		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            HttpClient client = new HttpClient();
    		  int result = client.executeMethod(method); 
      		  System.out.println(method.getStatusLine());
      		  System.out.println(result);
    		  if (result == HttpStatus.SC_OK) {
    			  
    		  }
    		  if (client.executeMethod(method) == HttpStatus.SC_OK) {
	            //	content +=  method.getResponseBodyAsString().trim()+"\n";
    		  }

		        method.releaseConnection();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return content;
    }
    public static String htmlFilter(String inputString) {
   	 if (inputString==null) return null;
   	  String htmlStr = inputString; // 含html标签的字符串
   	  String textStr = "";
   	  java.util.regex.Pattern p_script;
   	  java.util.regex.Matcher m_script;
   	  java.util.regex.Pattern p_style;
   	  java.util.regex.Matcher m_style;
   	  java.util.regex.Pattern p_html;
   	  java.util.regex.Matcher m_html;
   	  try {
   	   String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script>]*?>[\s\S]*?<\/script>
   	   // }
   	 //  String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style>]*?>[\s\S]*?<\/style>
   	   // }
   	  // String regEx_html = "<a[^>]+>|</a>"; // 定义HTML标签的正则表达式

   	   p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
   	   m_script = p_script.matcher(htmlStr);
   	   htmlStr = m_script.replaceAll(""); // 过滤script标签

/*    	   p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
   	   m_style = p_style.matcher(htmlStr);
   	   htmlStr = m_style.replaceAll(""); // 过滤style标签

   	   p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
   	   m_html = p_html.matcher(htmlStr);
   	   htmlStr = m_html.replaceAll(""); // 过滤html标签
*/
   	   textStr = htmlStr;

   	  } catch (Exception e) {
   		  e.printStackTrace();
   	  }

   	  return textStr;
   	}
    public static void main(String ag[]){
    	//System.out.println(getHttpClientHeader("http://localhost/sqcn/index.html"));
    	//System.out.println(getHeader("http://localhost/sqcn/index.html"));
    	System.out.println(getHeader("http://www.163.com"));
    	/*    	System.out.println(getHeader("http://www.baidu.com"));
    	System.out.println(getHeader("http://www.google.com"));
    	System.out.println(getHeader("http://www.qq.com"));
    	System.out.println(getHeader("http://www.xiqiaoan.com"));
    	System.out.println(getHeader("http://oa.xiqiaoan.com"));*/
    }

}
