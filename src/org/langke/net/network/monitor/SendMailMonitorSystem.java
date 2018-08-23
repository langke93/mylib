package org.langke.net.network.monitor;
 
import java.io.File;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
 
 
 

public class SendMailMonitorSystem {

	/**
	 * @param args
	 */
	final static String target_url = "http://url.cn/Proxool_Admins";
	final static String toMail = "mail@qq.com";
	final static String  now = Calendar.getInstance().getTime().toLocaleString(); 

	public static void main(String[] args) {
		PostUrl();
		diskFree();

	}
	public static void diskFree(){
		try {
	    	File[] roots = File.listRoots();//获取磁盘分区列表
	    	long freeSize = 0;
	    	for (File file : roots) {
	    		freeSize = file.getUsableSpace()/1024/1024/1024;
	    		if(freeSize<3){//磁盘可用空间小于3G，发送邮件警报
					sendMail(now+"磁盘可用空间=" +freeSize+"G","mail@qq.com");
					sendMail(now+"磁盘可用空间=" +freeSize+"G" ,"mail@qq.com");
	    		}
	    	}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	public static void PostUrl() {
		String content = null;
		HttpClient client = new HttpClient();
		try {
			PostMethod method = new PostMethod(target_url);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
			int result = client.executeMethod(method); 
			if (result == HttpStatus.SC_OK) {
				method = new PostMethod(target_url);
				method.getParams().setContentCharset("GBK");
				//?alias=jdbc%2Foracle&tab=snapshot&detail=more
				method.setRequestBody(new NameValuePair[] { 
						new NameValuePair("alias", "jdbc%2Foracle"), 
	                    new NameValuePair("tab","snapshot"),
	                    new NameValuePair("detail","more")
	                    });
				if (client.executeMethod(method) == HttpStatus.SC_OK) {
					content = method.getResponseBodyAsString().trim() ;
					if(content.indexOf("(available)")!=-1){
						System.out.println(now+"系统正常");
					}else{
						sendMail(now+content ,"mail@qq.com");
						sendMail(now+content ,"mail@qq.com");
						System.out.print(now+content);
					}
				}
			}else{
				sendMail(now+"连接服务器异常！"+result  ,"mail@qq.com");
				sendMail(now+"连接服务器异常！"+result  ,"mail@qq.com");
				System.out.print(now+"连接服务器异常！"+result);
			}
		} catch (Exception e) {
			System.out.print(now+"连接服务器异常！"+e.getMessage());
			//e.printStackTrace();
			try{
				sendMail(now+"连接服务器异常！"+e.getMessage() ,"mail@qq.com");
				sendMail(now+"连接服务器异常！"+e.getMessage() ,"mail@qq.com");
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}

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
}
