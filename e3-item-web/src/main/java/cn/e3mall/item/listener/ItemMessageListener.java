package cn.e3mall.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.ext.TbItemExt;
import cn.e3mall.manager.service.TbItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class ItemMessageListener implements MessageListener {

	@Autowired
	private FreeMarkerConfigurer config;
	
	@Autowired
	private TbItemService service;
	
	@Value("${ITEM_TEMPLATE_NAME}")
	private  String ITEM_TEMPLATE_NAME;
	@Value("${ITEM_HTML_PATH_PRE}")
	private String ITEM_HTML_PATH_PRE;
	@Value("${ITEM_HTML_PATH_EXT}")
	private String ITEM_HTML_PATH_EXT;
	@Override
	public void onMessage(Message message) {
	if(message instanceof TextMessage){
		TextMessage msg = (TextMessage)message;
		String text;
		long itemId=0L;
		try {
			
			text = msg.getText();
			if(StringUtils.isNotBlank(text)){
				 itemId = Long.parseLong(text);
				TbItem item= service.getItembyId(itemId);
				TbItemExt ext = new TbItemExt();
				
				if (item != null){
					BeanUtils.copyProperties(item, ext);
					
				}
				
				Map<String, Object> map = new HashMap<>();
				map.put("item", ext);
				Configuration configuration = config.getConfiguration();
				Template template = configuration.getTemplate(ITEM_TEMPLATE_NAME);
				 Writer out = new FileWriter(new File(ITEM_HTML_PATH_PRE+itemId+ITEM_HTML_PATH_EXT));
                 template.process(map, out);
				out.close();
				
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}	
		
		

	}

}
