package cn.e3mall.manager.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.mapper.TbItemMapper;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.TbItemExample;
import redis.clients.jedis.JedisCluster;
@Service
public class TbItemServiceImpl  implements TbItemService{
	@Autowired
	private JmsTemplate template;
	@Autowired
	private JedisCluster cluster;
	@Value("${REDIS_ITEM_KEY_PRE}")
	private String REDIS_ITEM_KEY_PRE;
    @Value("${REDIS_ITEM_EXPIRE}")
	private int REDIS_ITEM_EXPIRE;
   @Resource(name="itemTopic")
	private Destination  destinantion;
	
   @Autowired
	private TbItemMapper mapper;
	@Override
	public TbItem getItembyId(Long id) {
		if(id==null){
			return null;
		}
		try {
			//查询redis集群
			String itemJson = cluster.get(REDIS_ITEM_KEY_PRE+id);
			if(StringUtils.isNoneBlank(itemJson)){
				return JsonUtils.jsonToPojo(itemJson, TbItem.class) ;
				
			}
			
			//查不到查询数据库
			TbItem key = mapper.selectByPrimaryKey(id);
			//将查到的数据放入redis集群
			cluster.set(REDIS_ITEM_KEY_PRE+id, JsonUtils.objectToJson(key));
			
		  //设置有效期
			cluster.expire(REDIS_ITEM_KEY_PRE+id, REDIS_ITEM_EXPIRE);
		
			return  key;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return  null;
	}
	@Override
	public DatagridResult findAll(Integer page, Integer rows) {
		//PageHelper.startPage(pageNum, pageSize, count);
		if(page==null){
			page=1;
		}
		if(rows==null){
			rows=20;
		}
		PageHelper.startPage(page, rows);
		
		TbItemExample example = new TbItemExample() ;
		List<TbItem> list = mapper.selectByExample(example);
		PageInfo<TbItem > pageInfo = new PageInfo<>(list);
		System.out.println("总记录数：" + pageInfo.getTotal());
		System.out.println("总页数：" + pageInfo.getPages());
		DatagridResult datagridResult = new DatagridResult();
		datagridResult.setRows(pageInfo.getList());
		datagridResult.setTotal(pageInfo.getTotal());
		return datagridResult;
	}
	@Override
	public E3Result save(TbItem item) {
		//生成商品id
		final long genItemId = IDUtils.genItemId(); 
		item.setId(genItemId);
		item.setStatus((byte )1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//补全update  status  creaet
		//执行插入数据
		mapper.insert(item);
		template.send(destinantion, new  MessageCreator(){
			@Override
			public Message createMessage(Session session) throws JMSException {			
				return session.createTextMessage(genItemId+"");
			}		
		});
		return E3Result.ok();
	}
	

}
