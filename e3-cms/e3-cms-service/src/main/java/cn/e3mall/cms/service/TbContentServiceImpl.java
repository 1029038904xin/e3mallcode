package cn.e3mall.cms.service;



import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.mapper.TbContentMapper;
import cn.e3mall.manager.po.TbContent;
import cn.e3mall.manager.po.TbContentExample;
import cn.e3mall.manager.po.TbContentExample.Criteria;
import cn.itcast.cms.servcie.TbContentService;
import redis.clients.jedis.JedisCluster;
@Service
public class TbContentServiceImpl implements TbContentService {
  @Autowired
	private  TbContentMapper  mapper;
  
  @Autowired
  private JedisCluster cluster;
  @Value("${REDIS_CONTENT_KEY")
  private String REDIS_CONTENT_KEY;
	
	@Override
	public DatagridResult queryContentListByCid(Integer rows, Integer page, Long categoryId) {
		if(page==null){		
			page=1;
		}
		if(rows==null){		
			rows=1;
		}		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		
		PageHelper.startPage(page, rows);
		List<TbContent> list = mapper.selectByExample(example);
		PageInfo<TbContent > info = new PageInfo<>(list);
		
		System.out.println("总记录数：" + info.getTotal());
		System.out.println("总页数：" + info.getPages());
		DatagridResult datagridResult = new DatagridResult();
		datagridResult.setRows(info.getList());
		datagridResult.setTotal(info.getTotal());
		return datagridResult;
	}

	@Override
	public E3Result save(TbContent content) {
		// 补全属性
				content.setCreated(new Date());
				content.setUpdated(new Date());
				// 插入数据库
				mapper.insert(content);
				// 返回结果
				return E3Result.ok();

		
	}

	@Override
	public List<TbContent> queryContentList(Long categoryId) {
	    if(categoryId ==null){
	    	return null;
	    }
	    try {
	    	String hget = cluster.hget(REDIS_CONTENT_KEY, categoryId+"");
	    	if(StringUtils.isNotEmpty(hget)){	    		
	    		return JsonUtils.jsonToList(hget, TbContent.class);
	    	}    			
		} catch (Exception e) {
			// TODO: handle exception
		}	    
    TbContentExample example  = new TbContentExample();
    Criteria criteria = example.createCriteria();
    criteria.andCategoryIdEqualTo(categoryId);
	List<TbContent> list = mapper.selectByExample(example  );
	try {		
		cluster.hset(REDIS_CONTENT_KEY, categoryId+"", JsonUtils.objectToJson(list));		
	} catch (Exception e) {
	}
		return list;
	}

}
