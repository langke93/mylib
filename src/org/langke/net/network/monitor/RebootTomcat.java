package org.langke.net.network.monitor;

/** 
* 监控服务器tomcat状态  
* lizz
*/
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date; 
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
        
public class RebootTomcat { 
	/**
	 * 检测Tomcat服务，并自动重启
	 * @throws NullPointerException
	 */
    private static void keepTomcatAlive() throws NullPointerException {
        String s = null;
        String cmd = null;
        String runningTomcat = null;
        String pid = null;
        boolean isOutOfMemary = false;
        java.io.BufferedReader in; 
        //检查着页能否访问
        try {
          URL url = new URL("http://url.cn/index.jsp");
          URLConnection con = url.openConnection();
          in = new java.io.BufferedReader(new java.io.InputStreamReader(con.getInputStream()));
          con.setConnectTimeout(180000);
          con.setReadTimeout(300000);
          while ((s = in.readLine()) != null) {
            if (s!=null && s.length() > 0&&s.indexOf("logo_body_begin")!=-1) {
              // 如果in.readLine的内容不为空，则证明当前的tomcat容器可用，不过操作直接返回
            	System.out.println(getNowDate()+"：Tomcat运行良好");
              return;
            }
          }
          in.close();
        } catch (Exception ex) {
        	System.out.println(getNowDate());
        	ex.printStackTrace();
        }
        System.out.println(getNowDate()+s);
        //检查正在运行的Tomcat
        try {
			cmd = "ps -ef|grep java";
			s = getRuntimeResult(cmd);
			if(s!=null && s.indexOf("/home/tomcat/apache-tomcat-5.5.25/")!=-1){
				runningTomcat = "/home/tomcat/apache-tomcat-5.5.25/";
			}else if(s!=null && s.indexOf("/home/tomcat/cluster1/")!=-1){
				runningTomcat = "/home/tomcat/cluster1/";
			}  
        } catch (Exception e) {
        	e.printStackTrace();
        }
        System.out.println("异常Tomcat:"+runningTomcat);
        if(runningTomcat==null) return;
        //检查是否Out Of Memary
        try {
	          cmd = "tail -v -n 30000 "+runningTomcat+"logs/catalina.out|grep Exception";
	          s = getRuntimeResult(cmd);
	          if(s!=null && s.indexOf("java.lang.OutOfMemoryError")!=-1){ 
	        	  isOutOfMemary=true ;
	              System.out.println("java.lang.OutOfMemoryError");
	          }else if(s!=null && s.indexOf("Couldn't get connection because we are at maximum connection count")!=-1){
	        	 isOutOfMemary=true ;	
	              System.out.println("Couldn't get connection because we are at maximum connection count");
	          }else{
	              System.out.println("其它异常");	        	  
	          }
        } catch (Exception e) {
          e.printStackTrace();
        }

		try {
			sendMail(getNowDate()+runningTomcat+s  ,"mail@qq.com");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("Send mail over !");
        //if(isOutOfMemary==false) return;
        //取得异常Tomcat的PID
        System.out.println("取异常Tomcat的进程ID");
        try {
        	cmd = "ps -ef | grep "+runningTomcat+" | grep -v tail | grep -v vi | grep -v grep | awk '{print $2}'";
            s = getRuntimeResult(cmd);
        	pid = s.trim();
            System.out.println("PID:"+pid);
        } catch (Exception e) {
          e.printStackTrace();
        }
        killTomcat(pid);
        startTomcat(runningTomcat);
      }
    
    /**
     * 保持Passport Tomcat持续运行
     * @throws NullPointerException
     */
    private static void keepPassportTomcatAlive() throws NullPointerException {
        String s = null;
        String cmd = null;
        String arr_cmd[] = null; 
        String runningTomcat = null;
        String pid = null;
        boolean isOutOfMemary = false;
        java.io.BufferedReader in; 
        //检查着页能否访问
        try {
          URL url = new URL("http://test.url.cn/:8083/common/user_is_login.jsp");
          URLConnection con = url.openConnection();
          in = new java.io.BufferedReader(new java.io.InputStreamReader(con.getInputStream()));
          con.setConnectTimeout(10000);
          con.setReadTimeout(40000);
          while ((s = in.readLine()) != null) {
            if (s!=null && s.length() > 0&&s.indexOf("responseText")!=-1) {
              // 如果in.readLine的内容不为空，则证明当前的tomcat容器可用，不过操作直接返回
            	System.out.println(getNowDate()+"：Passport Tomcat运行良好");
              return;
            }
          }
          in.close();
        } catch (Exception ex) {
        	System.out.println(getNowDate()+"Passport Tomcat");
        	ex.printStackTrace();
        }
        System.out.println(getNowDate()+s);
        //检查正在运行的Tomcat
        try {
        	cmd = "ps -ef | grep java";
            s = getRuntimeResult(cmd);
            if(s!=null && s.indexOf("/home/tomcat/passport-tomcat-5.5.25/")!=-1)
            	  runningTomcat = "/home/tomcat/passport-tomcat-5.5.25/";    
        } catch (Exception e) {
          e.printStackTrace();
        }
        System.out.println("异常Passport Tomcat:"+runningTomcat);
        if(runningTomcat==null) return;
        //检查是否Out Of Memary
        try {
          java.lang.Process p = java.lang.Runtime.getRuntime().exec("tail -v -n 30000 "+runningTomcat+"logs/catalina.out|grep Exception");
          in = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
          while ((s = in.readLine()) != null) {
              if(s!=null && s.indexOf("java.lang.OutOfMemoryError")!=-1)
            	  isOutOfMemary = true;
          }
          in.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
        System.out.println("异常信息Passport Tomcat:"+s);

		try {
			sendMail(getNowDate()+runningTomcat+s  ,"mail@qq.com");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
       // if(isOutOfMemary==false) return;
        //取得异常Tomcat的PID
        try {
        	cmd = "ps -ef | grep "+runningTomcat+" | grep -v tail | grep -v vi | grep -v grep | awk '{print $2}'";
            s = getRuntimeResult(cmd);
        	pid = s.trim();
            System.out.println("PID:"+pid);
        } catch (Exception e) {
          e.printStackTrace();
        }
        killTomcat(pid);
        startTomcat(runningTomcat);
      }
    
     //kill Tomcat进程
      public static void killTomcat(String pid) {
        try {
			System.out.println("------------------Start Kill Tomcat---------------------"+pid);
			String s = getRuntimeResult("kill -9 "+pid); 
			System.out.println(s); 
			System.out.println(getNowDate() + " Tomcat is stop " );
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      //启动Tomcat
      public static void startTomcat(String runningTomcat) {
    	if(runningTomcat!=null){
    		if(runningTomcat.equals("/home/tomcat/apache-tomcat-5.5.25/"))//停止的Tomcat
    			runningTomcat = "/home/tomcat/cluster1/";//将要启动的Tomcat
    		else if(runningTomcat.equals("/home/tomcat/cluster1/"))
    			runningTomcat = "/home/tomcat/apache-tomcat-5.5.25/";
    		else if(runningTomcat.equals("/home/tomcat/passport-tomcat-5.5.25/"))
    			runningTomcat = "/home/tomcat/passport-tomcat-5.5.25/";
    	}
        try {
			  System.out.println(getNowDate()+"------------------Start Tomcat---------------------");
			  String s = getRuntimeResult("sh "+runningTomcat+"bin/startup.sh &"); 
			  System.out.println(s); 
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      /**
       * 当前时间
       * @return
       */
      public static String getNowDate(){
		Date now = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Etc/GMT-8"));
		return  df.format(now);
      }

  	/**
  	 * 执行命令并返回结果 ,命令可以带参数
  	 * @param cmd
  	 * @return
  	 */
  	public static String getRuntimeResult(String cmd){
  		String encoding = System.getProperty("file.encoding");
  		int c;
  		String str="";
  		String estr="";
  		String arr_cmd[] = null;
  		try{
  			Process child = null;
        	arr_cmd  = new String[]{"/bin/sh","-c",cmd};//这种形式参数，可以解决ERROR: Unsupported SysV option.问题；  
  			child = Runtime.getRuntime().exec(arr_cmd);
  			//取得命令执行结果
  			java.io.InputStream in = child.getInputStream();
  			while ((c = in.read()) != -1) {
  				str+=((char)c);
  			}
  			str = new String(str.getBytes("iso-8859-1"),encoding); 
  			//取得异常信息
  			java.io.InputStream err = child.getErrorStream();
  			while((c = err.read())!=-1){
  				estr+=((char)c);
  			}
  			estr = new String(estr.getBytes("iso-8859-1"),encoding); 
  			in.close();
  			try {
  				child.waitFor();
  			} catch (InterruptedException e) {
  				e.printStackTrace();
  			}
  		}catch(Exception e){
  			System.out.println("getRuntimeResult() "+cmd);
  			e.printStackTrace();
  		}
  		if(str!=null && str.length()>0){
  			return str;
  		}else return estr;
  	}
  	/**
  	 * 
  	 * @param name用户昵称
  	 * @param content信件内容
  	 * @param toMail收件人
  	 * @param title标题
  	 * @throws Exception 
  	 */
  	public static void sendMail(String content,String toMail) throws Exception	{
  			String smtp ="smtp.163.com";
  			String mail = "mail@163.com";//发送者
  			String password = "passwd";
  			//String toMail = "mail@qq.com";//接收者
  			String sender = "资源网定时检测系统";
  			String name = "资源网定时检测系统";
  			String title = "资源网定时检测系统";
  			//String enicuser = Resources.getValue(path,"enicname");

  			PopupAuthenticator  popAuthenticator = new PopupAuthenticator();
  			//PasswordAuthentication pop = popAuthenticator.performCheck(enicuser,enicpass); 
  			Properties prope= new Properties();
  			prope.put("mail.transport.protocol","smtp");
  			prope.put("mail.smtp.class","com.sun.mail.smtp.SMTPTransport");
  			prope.put("mail.smtp.auth","true");
  			prope.put("mail.smtp.port","25");//默认25，GMAIL用465
  			prope.put("mail.smtp.host",smtp);
  			//创建会话
  			Session session=Session.getInstance(prope,popAuthenticator);
  			InternetAddress fromAddress = new InternetAddress(mail,sender);
  			MimeMessage message = new MimeMessage(session);
  			message.setFrom(fromAddress);
  			InternetAddress toAddress = new InternetAddress(toMail);
  			message.setRecipient(Message.RecipientType.TO,toAddress);
  			//邮件标题
  			message.setSubject(title);
  			//发送时间
  			message.setSentDate(Calendar.getInstance().getTime());
  			//邮件内容
  			message.setContent("<meta http-equiv=Content-Type content=text/html; charset=gb2312><h3>"+name+"</h3>"+content,"text/html;charset=GB2312");
  			//保存邮件
  			message.saveChanges();
  			//创建Transport
  			Transport transport = session.getTransport("smtp");
  			//连接服务
  			transport.connect(smtp,mail,password);
  			//发送邮件
  			transport.sendMessage(message, message.getAllRecipients());
  			//发送结束
  			transport.close();
  			
  	}      
      public static void main(String[] args) {
            keepTomcatAlive(); 
            keepPassportTomcatAlive(); 
    }

}
