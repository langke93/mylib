package org.langke.java6.webservice;

/** 
* 测试Java6 WS生成的客户端代码 
* 
*/ 
import org.langke.java6.webservice.client.Java6WebService;
import org.langke.java6.webservice.client.Java6WebServiceService;
public class TestClient { 
        public static void main(String[] args) { 
                //创建一个客户端服务对象 
        		Java6WebService java6WS = new Java6WebServiceService().getJava6WebServicePort(); 
                //调用服务方法，并得到方法返回值 
                String returnContent = java6WS.doSomething(); 
                //打印服务的返回值 
                System.out.println(returnContent); 
        } 
}
