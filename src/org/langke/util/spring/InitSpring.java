package org.langke.util.spring;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;



public class InitSpring {

	  public static XmlBeanFactory getXmlBeanFactoryInstance () {
		  String resource = "/applicationContext.xml";
	      XmlBeanFactory factory=new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
	      return factory;
	  }
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			//XmlBeanFactory factory = TestVideoBatch.getXmlBeanFactoryInstance();
			
/*			TvVideoBatchAction tv = (TvVideoBatchAction) new ClassPathXmlApplicationContext("/conf/spring/*.xml").getBean("tvVideoBatchAction");		
			tv.batchUpload();*/
		}
}
