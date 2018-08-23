package org.langke.util.mail;

import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class Mail {
	
	/**
	 * 发送邮件
	 * @param content 邮件内容
	 * @param toMail 发送对象
	 * @param title 邮件标题
	 * @return
	 */
	public static boolean sendMail(String content,String toMail,String title){
		try {
//			String smtp = "smtp.zyw.com";
//			String mail = "bbs@zyw.com";
//			String password = "999";
			
			String smtp = getValue("smtp");
			String mail = getValue("mail");
			String password = getValue("password");
			
			Properties prope= new Properties();
			prope.put("mail.transport.protocol","smtp");
			prope.put("mail.smtp.class","com.sun.mail.smtp.SMTPTransport");
			prope.put("mail.smtp.auth","true");
			prope.put("mail.smtp.port","25");
			prope.put("mail.smtp.host",smtp);
			//创建会话
			Session session=Session.getInstance(prope);
			InternetAddress fromAddress = new InternetAddress(mail,"资源网");
			MimeMessage message = new MimeMessage(session);
			message.setFrom(fromAddress);
			InternetAddress toAddress = new InternetAddress(toMail);
			message.setRecipient(Message.RecipientType.TO,toAddress);
			//邮件标题
			message.setSubject(title);
			//发送时间
			Calendar now = Calendar.getInstance();
			message.setSentDate(now.getTime());
			
			
			message.setContent(content,"text/html;charset=UTF-8");
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
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 获取资源文件的值
	 * @param key
	 * @return
	 */
	private static String getValue(String key){
		try {			
			Properties properties = new Properties();
			properties.load(Mail.class.getResourceAsStream("/mail.properties"));
			return properties.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		Mail.sendMail("测试", "888@qq.com", "测试");
	}
}
