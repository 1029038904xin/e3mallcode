package cn.e3mall.search.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.manager.mapper.ext.TbItemExtMapper;
import cn.e3mall.manager.po.ext.TbItemExt;

@Service
public class TbItemExtServiceImpl implements TbItemExtService {

	@Autowired
	private TbItemExtMapper itemMapper;

	@Autowired
	private SolrServer solrServer;

	@Override
	public E3Result importAll() {
		// TODO Auto-generated method stub
		List<TbItemExt> list = itemMapper.queryItemList();
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		SolrInputDocument doc;
		for (TbItemExt item : list) {
			doc = new SolrInputDocument();
			doc.addField("id", item.getId());
			doc.addField("item_title", item.getTitle());
			doc.addField("item_sell_point", item.getSellPoint());
			doc.addField("item_price", item.getPrice());
			doc.addField("item_description", item.getDescription());
			doc.addField("item_category_name", item.getCatName());
			if (StringUtils.isNotEmpty(item.getImage())) {
				String[] split = item.getImage().split(",");
				if (split != null && split.length > 0) {
					doc.addField("item_image", split[0]);
				}
			}
			docs.add(doc);
		}
		try {

			solrServer.add(docs);
			solrServer.commit();
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return E3Result.build(500, "添加索引失败");
		}

		return E3Result.ok();
	}

	@Override
	public Map<String, Object> search(String q, Integer page) throws Exception {
		//创建查询条件
		SolrQuery query = new SolrQuery();
		//设置查询条件
	   if(StringUtils.isNotEmpty(q)){
		   query.setQuery(q);		   
	   }else{
		   query.setQuery("*:*");
	   }
		//设置默认查询与
	   query.set("df", "item_keywords");	   
	   //设置分页查询
	   if(page ==null){
		   page =1;
	   }
	   query.setStart(page);
	   query.setRows(20);   
		//设置升序
	   query.setSort("item_price", ORDER.asc);   
	   query.setHighlight(true);
	   query.addHighlightField("item_title");
	   query.setHighlightSimplePre("<font style='color:red'>");
	   query.setHighlightSimplePost("</font>");   
    QueryResponse response = solrServer.query(query);		
  //查询高亮
    Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
    SolrDocumentList results = response.getResults();
     //查询总数
      long totalRecords = results.getNumFound();
      //分装结果  query    totalPages   itemList    page   totalRecords  
      List<TbItemExt> itemList = new ArrayList<>();
      TbItemExt item;
      for (SolrDocument doc : results) {
    	  item = new TbItemExt();
		item.setId(Long.parseLong(doc.get("id").toString()));
		item.setPrice(Long.parseLong(doc.get("item_price").toString()));
		item.setImage(doc.get("item_image").toString());
		List<String> list =highlighting.get(doc.get("id")).get("item_title");
		if(list!=null && list.size()>0){
			item.setTitle(list.get(0));
		}else{
			item.setTitle(doc.get("item_title").toString());	
		}
		itemList.add(item);
	}
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("itemList", itemList);
      map.put("query", q);
      map.put("page", page);
      map.put("totalRecords", totalRecords);
      Long totalPages =totalRecords/20;    
		if(totalRecords % 20 !=0){
			totalPages=totalPages+1;		
		}
		map .put("totalPages", totalPages);
		
		return map;
	}

}
