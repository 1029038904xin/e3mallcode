package test;

import java.awt.geom.CubicCurve2D;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class TestQuery {
	@Test
	public void  testQueue() throws Exception{
		
		
		String brokerURL = "tcp://192.168.242.128:61616";
		
		ActiveMQConnectionFactory f = new ActiveMQConnectionFactory(brokerURL );
		Connection connection = f.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination;
		Queue queue = session.createQueue("llll");
		MessageConsumer consumer = session.createConsumer(queue);
		
		
		consumer.setMessageListener(new MessageListener() {	
			@Override
			public void onMessage(Message message) {
				if( message instanceof TextMessage){
					   TextMessage message2 =(TextMessage)message;
					   try {
						System.out.println(message2.getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
				
			}
		});
        // 等待输入
		System.in.read();

		// 第八步：关闭资源
		consumer.close();
		session.close();
		
		connection.close();

		
	}
	@Test
	public void  testTopic() throws Exception{
		
		
		String brokerURL = "tcp://192.168.242.128:61616";
		
		ActiveMQConnectionFactory f = new ActiveMQConnectionFactory(brokerURL );
		Connection c = f.createConnection();
		c.start();
		Session session = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("我来了");
		MessageConsumer consumer = session.createConsumer(topic);
		System.out.println("3333");
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				if(message instanceof TextMessage){
					TextMessage text = (TextMessage)message;
					try {
						System.out.println(text.getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		});
		System.in.read();

		// 第八步：关闭资源
		consumer.close();
		session.close();
		
		c.close();

	}

}
