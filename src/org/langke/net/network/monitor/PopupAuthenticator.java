package org.langke.net.network.monitor;
/*
 * 对邮箱进行认证
 * @author zxb
 * */
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class PopupAuthenticator extends Authenticator {

	String username=null; 
	String password=null; 
	
	public PopupAuthenticator(){
		
	} 
	/**
	 * 
	 * @param user 邮箱用户名
	 * @param pass 邮箱密码
	 * @return
	 */
	public PasswordAuthentication performCheck(String user,String pass){ 
		username = user; password = pass; return getPasswordAuthentication(); 
	} 
	
	protected PasswordAuthentication getPasswordAuthentication() { 
		return new PasswordAuthentication(username, password); 
	}
}
