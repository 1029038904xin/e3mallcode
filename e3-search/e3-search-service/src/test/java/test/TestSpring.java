package test;

import java.io.IOException;

import javax.jms.Destination;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class TestSpring {
	

	@Before
	public  void init(){
		ApplicationContext context=new  ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		
	}
	@Test
	public void test() throws IOException{
		System.in.read();
		
		
	}

}
