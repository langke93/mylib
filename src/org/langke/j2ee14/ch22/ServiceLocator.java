package org.langke.j2ee14.ch22;
import javax.jms.*;
import javax.jms.Queue;
import javax.naming.*;
import java.util.*;
/**
 *JMS服务定位器
 */
public class  ServiceLocator
{
   private InitialContext ic;
   
   public ServiceLocator()
   {
   	  ic=this.getInitialContext();
   }   
    
   /**
    * @param qConnFactoryName
    * @return QueueConnectionFactory
    */
  public  QueueConnectionFactory getQueueConnectionFactory(String qConnFactoryName) 
                                                 throws ServiceLocatorException 
  {
      QueueConnectionFactory factory = null;
      try {
         factory = (QueueConnectionFactory) ic.lookup(qConnFactoryName);
      } catch (NamingException ne) {
        throw new ServiceLocatorException(ne);
      } catch (Exception e) {
            throw new ServiceLocatorException(e);
      }
      return factory;
    }
   
   /**
    * @param queueName
    * @return Queue
    */
    public  Queue getQueue(String queueName) throws ServiceLocatorException {
      Queue queue = null;
      try {
        queue =(Queue)ic.lookup(queueName);
      } catch (NamingException ne) {
            throw new ServiceLocatorException(ne);
      } catch (Exception e) {
            throw new ServiceLocatorException(e);
      }
      return queue;
    }
   
   /**
    * @param topicConnFactoryName
    * @return TopicConnectionFactory
    */
    public  TopicConnectionFactory getTopicConnectionFactory(String topicConnFactoryName) throws ServiceLocatorException {
      TopicConnectionFactory factory = null;
      try {
        factory = (TopicConnectionFactory) ic.lookup(topicConnFactoryName);
      } catch (NamingException ne) {
         throw new ServiceLocatorException(ne);
      } catch (Exception e) {
            throw new ServiceLocatorException(e);
      }
      return factory;
    }

   
   /**
    * @param topicName
    * @return Topic
    */
   public  Topic getTopic(String topicName) throws ServiceLocatorException {
      Topic topic = null;
      try {
          topic = (Topic)ic.lookup(topicName);
      } catch (NamingException ne) {
         throw new ServiceLocatorException(ne);
      } catch (Exception e) {
            throw new ServiceLocatorException(e);
      }
      return topic;
    }
   /**
    *获得初始上下文
    */
	public InitialContext getInitialContext()
    {
    	Properties p=new Properties();
    	try
    	{
    		p.load(ServiceLocator.class.getResourceAsStream("config.properties"));
    	
    
    	return new InitialContext(p);
         }
    	catch(NamingException e)
    	{
    		e.printStackTrace();
        }
        catch(java.io.IOException e)
    	{
    		e.printStackTrace();
        }
    	System.out.println("error");
    	return null;
    }
}