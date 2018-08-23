package org.langke.spring.aop;

import java.util.Date;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

public class MainApp {
	/**
	 * @param args
	 * @author Kerluse Benn
	 */
	public static void main(String[] args) throws Exception {
		// TODO Add your codes here
		BeanFactory factory = new XmlBeanFactory(new FileSystemResource("bean.xml"));
		BookService bookService = (BookService) factory.getBean("BookService");
		IAuditable auditable = (IAuditable) bookService;
		System.out.print(bookService.OrderBook("Kerluse Benn","Professional C#"));
		auditable.setLastModifiedDate(new Date());
		System.out.println(" 订购时间为" + auditable.getLastModifiedDate());
		Thread.sleep(3000);
		System.out.print(bookService.OrderBook("Kerluse Benn","Expert j2ee one-on-one"));
		auditable.setLastModifiedDate(new Date());
		System.out.println(" 订购时间为"+ auditable.getLastModifiedDate());
	}
}