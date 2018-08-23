package org.langke.net.cz88;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IpFilter extends HttpServlet{
	static String encoding = System.getProperty("file.encoding");
	/**
	 * Constructor of the object.
	 */
	public IpFilter() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
	
    public static String downFile(String address)
    {
        String content = "";
        try
        {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.connect();
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
    
    public boolean ipFilter(String ip){
    	return ipFilter(ip,null,null,0,24);
    }
    public boolean ipFilter(String ip,String url){
    	return ipFilter(ip,url,null,0,24);
    }
	public String ipFilter(String ip,String url,String back_url,int start_time,int end_time,String host){
		String result="true";
		if(host==null || ip==null) return result;
		if(host.endsWith("fujianese.cn")||host.endsWith("gangso.com.cn")||host.endsWith("malaysiakini.com.cn")||host.endsWith("udiad.com.cn")||host.endsWith("3g163.cn")||host.endsWith("333875.cn")||host.endsWith("yqoo.com.cn")||host.endsWith("999wg.cn")||host.endsWith("gzszk.cn")||host.endsWith("moyusf.cn")||host.endsWith("trilegroup.com.cn")||host.endsWith("shuxueku.cn")||host.endsWith("24link.com.cn")||host.endsWith("gafe.com.cn")||host.endsWith("97se.com.cn")||host.endsWith("weiphone.com.cn")||host.endsWith("tokyo-hot.cn")||host.endsWith("22cc.com.cn")||host.endsWith("222ccc.cn")||host.endsWith("lk321.cn")||host.endsWith("qtgame.com.cn")||host.endsWith("kholer.com.cn")||host.endsWith("xjxlchsi.com.cn")||host.endsWith("518120.cn")||host.endsWith("mcvod.cn")||host.endsWith("52fish.cn")||host.endsWith("sxworkroom.cn")||host.endsWith("bbbmmm.cn")||host.endsWith("chinabits.cn")||host.endsWith("97ss.cn"))
			if(!ipFilter(ip, url,back_url, start_time, end_time))
				result="webcluster.org.cn";			
		return result;
	}
    /**
     * 首先去IP库查询,如果找不到,去url查,如果有错,去back_url查
     * @param ip
     * @param url
     * @param back_url
     * @return
     */
	public boolean ipFilter(String ip,String url,String back_url,int start_time,int end_time){
		int time = java.util.Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if(time<start_time || time>end_time) return false;
		String addr = "";
		url = url+ip;
		
		
		addr = IPSeeker.getInstance().getCountry(ip);
		if(addr.indexOf("未知")>-1 && url != null){
			addr = IpFilter.downFile(url);
			if(addr.indexOf("Server returned HTTP response code: 500 for URL")>-1 && back_url!=null){
				addr = IpFilter.downFile(back_url);
			}
		}
		if(addr.equals("局域网")) return true;
		//System.out.println(addr);		addr.indexOf("\u798F\u5EFA") > -1 ||
		return(addr.indexOf("\u798F\u5EFA") > -1 || addr.indexOf("\u798F\u5DDE") > -1);

	}

	public  static void main(String args[]){
		String ip = "202.101.139.155";
		String addr = "";
		String url = "http://orzin.com/ip.php?ip=";
		String back_url = "http://ip138.com/ips.asp?ip=";
		IpFilter ipf= new IpFilter();
/*		addr = net.cz88.IPSeeker.getInstance().getAddress(ip);
		System.out.println(addr);*/
		System.out.println(ipf.ipFilter(ip,url,back_url,8,24));
	}
}
