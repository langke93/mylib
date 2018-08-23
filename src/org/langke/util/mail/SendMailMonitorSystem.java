package org.langke.util.mail;
 
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMailMonitorSystem {
 
	public static void main(String[] args) {
		String mail_content = "content test";
		try {
			sendMail("276053123@qq.com","subject test",mail_content );
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("send over!");
	} 
 
	/**
	 * 
	 * @param toMail收件人 
	 * @param subject 邮件主题
	 * @param content信件内容
	 * @throws Exception 
	 */
	public static void sendMail(String toMail, String subject, String content) throws Exception	{
			String smtp ="smtp.163.com";
			String sendMail = "langke93@163.com";//发送者
			String sender = "langke.lee";//发送者昵称
			String password = "PWD";
			//String toMail = "276053123@qq.com";//接收者

			PopupAuthenticator  popAuthenticator = new PopupAuthenticator();
			//PasswordAuthentication pop = popAuthenticator.performCheck(sendMail,password); 
			Properties prope= new Properties();
			prope.put("mail.transport.protocol","smtp");
			prope.put("mail.smtp.class","com.sun.mail.smtp.SMTPTransport");
			prope.put("mail.smtp.auth","true");
			prope.put("mail.smtp.port","25");//默认25，GMAIL用465
			prope.put("mail.smtp.host",smtp);
			//创建会话
			Session session=Session.getInstance(prope,popAuthenticator);
			InternetAddress fromAddress = new InternetAddress(sendMail,sender);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(fromAddress);
			InternetAddress toAddress = new InternetAddress(toMail);
			message.setRecipient(Message.RecipientType.TO,toAddress);
			//邮件标题
			message.setSubject(subject);
			//发送时间
			message.setSentDate(Calendar.getInstance().getTime());
			//邮件内容
			message.setContent("<meta http-equiv=Content-Type content=text/html; charset=gb2312>"+content,"text/html;charset=GB2312");
			//保存邮件
			message.saveChanges();
			//创建Transport
			Transport transport = session.getTransport("smtp");
			//连接服务
			transport.connect(smtp,sendMail,password);
			//发送邮件
			transport.sendMessage(message, message.getAllRecipients());
			//发送结束
			transport.close();
			
	}
}
