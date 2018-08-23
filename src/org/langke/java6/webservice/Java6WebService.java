package org.langke.java6.webservice;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
 

/**
 * Java6开发WebService入门
 * 
 */
@WebService
public class Java6WebService {
	/**
	 * Web服务中的业务方法
	 * 
	 * @return 一个字符串
	 */
	public String doSomething(String ... args) {
		String returnstr = null;
	    for (String str : args) {
	    	returnstr+=str;
	    }
		if(returnstr !=null) return returnstr;
		else
		return "Hello Java6 WebService!";
	}

	/**
	 * 在运行前先执行
	 * E:\workspace\J2EE\WebRoot\WEB-INF\classes>wsgen -cp . java6.webservice.Java6WebService
	 */
	public static void main(String[] args) {
		// 发布一个WebService
		System.out.println("Start Webservice......");
		Endpoint.publish(
				"http://192.168.1.99:8080/java6ws/java6.webservice.Java6WebService",
				new Java6WebService());
	}
	/**
	 * 生成客户端
	 * E:\workspace\J2EE\WebRoot\WEB-INF\classes>wsimport -p java6.webservice.client -keep http://192.168.1.99:8080/java6ws/java6.webservice.Java6WebService?wsdl
	 */
}