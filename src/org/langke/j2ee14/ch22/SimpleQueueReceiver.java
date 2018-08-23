package org.langke.j2ee14.ch22;

import javax.jms.*;
import javax.naming.*;
import java.util.Properties;
/**
 *接收JMS消息的程序
 */
public class SimpleQueueReceiver
{
	   /*          
         *在这里我们使用了JBOSS，那么缺省的连接工厂JNDI就是java:/XAConnectionFactory
         *同时，jboss也为测试配置了名为queue/testQueue的队列。
         *你可以在应用服务器或者消息中间件上配置对应的队列和连接工厂。
         */
	   String                  queueName = "queue/testQueue";
        String 					queueConnectionName="java:/XAConnectionFactory";
        Context                 jndiContext = null;
        QueueConnectionFactory  queueConnectionFactory = null;
        QueueConnection         queueConnection = null;
        QueueSession            queueSession = null;
        Queue                   queue = null;
        QueueReceiver           queueReceiver = null;
        TextMessage             message = null;
        final int               MSG_ID=10;
        
        ServiceLocator locacor=new ServiceLocator();
        
	 public static void main(String[] args)
	 {
	 	SimpleQueueReceiver test=new SimpleQueueReceiver();
	 	test.init();
	 	test.go();
	 }
	 /**
	  *初始化连接工厂和queue对象
	  */
	 public void init()
	 {        
        
        try {
        	//查找连接工厂
            queueConnectionFactory =locacor.getQueueConnectionFactory(queueConnectionName);
           //获得目标队列
            queue = (Queue) locacor.getQueue(queueName);
        } catch (ServiceLocatorException e) {
            System.out.println("没有查找到目标对象，请确认目标已经绑定 " + 
                e.toString());
                e.printStackTrace();
            System.exit(1);
        }
    }
    /**
     *接收消息并且打印
     */
    public void go()
    {

        /*
         *创建连接，然后为连接创建session
         *从session里创建sender，然后发送消息
         */
        try {
            //创建连接
            queueConnection = 
                queueConnectionFactory.createQueueConnection();
           //从连接中创建session
            queueSession = 
                queueConnection.createQueueSession(false, 
                    Session.AUTO_ACKNOWLEDGE);
           //从session里创建receiver
            queueReceiver = queueSession.createReceiver(queue);
            //开始接收消息
            queueConnection.start();
            while (true) {
                Message m = queueReceiver.receive(1);
                if (m != null) {
                    if (m instanceof TextMessage) {
                        message = (TextMessage) m;
                        System.out.println("接收到以下消息: " +
                            message.getText());
                         System.out.println("timeStamp:"+message.getJMSTimestamp());
                        System.out.println("destination:"+message.getJMSDestination().toString());
                        System.out.println("propertity:"+message.getStringProperty("uid"));
                        System.out.println("========================");
                    } else {
                        break;
                    }
                }
            }
        } catch (JMSException e) {
            System.out.println("发生了一下错误: " + 
                e.toString());
        } finally {
            if (queueConnection != null) {
                try {
                    queueConnection.close();
                } catch (JMSException e) {}
            }
        }
    }
   
}
