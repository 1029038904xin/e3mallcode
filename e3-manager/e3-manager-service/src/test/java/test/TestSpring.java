package test;

import static org.junit.Assert.*;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class TestSpring {

    /* private  	JmsTemplate temp;
     
     private Destination destinantion;
	@Before
	public  void init(){
		ApplicationContext context=new  ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		temp=context.getBean(JmsTemplate.class);
		//destinantion = context.getBean(Destination.class);
	}
	
	@Test
	public void testqueue() {
	
		temp.send(destinantion, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
			
				
				
				return session.createTextMessage("服务");
			}
		});}*/
		


}
