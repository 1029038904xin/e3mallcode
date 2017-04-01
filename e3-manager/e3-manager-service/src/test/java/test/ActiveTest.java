package test;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

public class ActiveTest {

	   @Test
	   public void QueueTest() throws JMSException{
		   
		   String brokerURL="tcp://192.168.242.128:61616";
		   //创建连接工厂
		  ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
		  //创建连接对象
		  Connection connection = factory.createConnection();
		  connection.start();
		  
		  //开启会话
		// 第一个参数：是否启用ActiveMQ的事务，如果为true，第二个参数失效。
			// 第二个参数：应答模式,AUTO_ACKNOWLEDGE为自动应答

		  Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 参数：消息队列的名称，在后台管理系统中可以看到
			Queue queue = session.createQueue("llll");
			// 第六步：通过session创建MessageProducer
			MessageProducer producer = session.createProducer(queue);
			TextMessage message = new ActiveMQTextMessage();
			message.setText("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
			producer.send(message);	
			// 第九步：关闭资源
			producer.close();
			session.close();
			connection.close();



		   
	   }
	   
	   @Test
	   public void TopicTest() throws JMSException{
		   
		   String brokerURL="tcp://192.168.242.128:61616";
		   //创建连接工厂
		  ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
		  Connection connection = factory.createConnection();
		  connection.start();
		  Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		  Topic topic = session.createTopic("我来了");
		  MessageProducer producer = session.createProducer(topic);
		  TextMessage message = new ActiveMQTextMessage(); 
		  message.setText("hahahahhahahahahahah");
		  producer.send(message);
			// 第九步：关闭资源
			producer.close();
			session.close();
			connection.close();

		  
		   
		   
		   
	   }
}
