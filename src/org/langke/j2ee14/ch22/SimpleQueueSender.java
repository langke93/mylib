package org.langke.j2ee14.ch22;

import javax.jms.*;
import javax.naming.*;
import java.util.Properties;

public class SimpleQueueSender
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
        QueueSender             queueSender = null;
        TextMessage             message = null;
        final int               MSG_ID=10;
        
        ServiceLocator locacor=new ServiceLocator();
        
	 public static void main(String[] args)
	 {
	 	SimpleQueueSender test=new SimpleQueueSender();
	 	test.init();
	 	test.go();
	 }
	 
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
    public void go()
    {

        /*
         *创建连接，然后为连接创建session
         *从session里创建sender，然后发送消息
         */
        try {
            queueConnection = 
                queueConnectionFactory.createQueueConnection();
            queueSession = 
                queueConnection.createQueueSession(false, 
                    Session.AUTO_ACKNOWLEDGE);
            queueSender = queueSession.createSender(queue);
            message = queueSession.createTextMessage();
            for (int i = 0; i < MSG_ID; i++) {
                message.setText("这是消息"+(i + 1)+"的内容 ");
                message.setStringProperty("uid","uid"+String.valueOf(i));
                System.out.println("发送了以下消息： " + 
                    message.getText());
                queueSender.send(message);//发送消息
            }

            /* 
             * 发送一条空的控制消息，表示消息结束。
             */
            queueSender.send(queueSession.createMessage());
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
