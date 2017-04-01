package cn.e3mall.search.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class TestQuene  implements MessageListener{

	@Override
	public void onMessage(Message message) {
	    if(message instanceof  TextMessage){
	    	TextMessage text =(TextMessage)message;
	    	try {
				System.out.println("监听到了"+text.getText());
				System.out.println("");
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    }
		
		
	}

}
