package cn.e3mall.search.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.manager.mapper.ext.TbItemExtMapper;
import cn.e3mall.manager.po.ext.TbItemExt;

public class ItemMessageListener implements MessageListener {

	@Autowired
	private SolrServer server;

	@Autowired
	private TbItemExtMapper mapper;

	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				Thread.sleep(1000);

				TextMessage msg = (TextMessage) message;

				String text = msg.getText();
				if (StringUtils.isNoneEmpty(text)) {
					Long itemId = Long.parseLong(text);

					TbItemExt item = mapper.queryItemById(itemId);

					SolrInputDocument doc = new SolrInputDocument();
					doc.addField("id", itemId);
					doc.addField("item_title", item.getTitle());
					doc.addField("item_sell_point", item.getSellPoint());
					doc.addField("item_category_name", item.getCatName());
					doc.addField("item_price", item.getPrice());
					if (item.getImage() != null) {
						String[] arr = item.getImage().split(",");
						doc.addField("item_image", arr[0]);
					}
					server.add(doc);
					server.commit();

				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
