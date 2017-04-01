package cn.e3mall.cms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.po.TreeNodeResult;
import cn.e3mall.manager.mapper.TbContentCategoryMapper;
import cn.e3mall.manager.po.TbContentCategory;
import cn.e3mall.manager.po.TbContentCategoryExample;
import cn.e3mall.manager.po.TbContentCategoryExample.Criteria;
import cn.itcast.cms.servcie.ContentCategoryService;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Autowired
	private  TbContentCategoryMapper mapper;
	@Override
	public  List<TreeNodeResult> queryCategoryList(Long parentId) {
		 List<TreeNodeResult> list = new  ArrayList<TreeNodeResult>();
		if(parentId == null){
			parentId=0L;			
		}
		 TbContentCategoryExample example = new TbContentCategoryExample();
		 Criteria criteria = example.createCriteria();
		 criteria.andParentIdEqualTo(parentId);
		 List<TbContentCategory>   categorys = mapper.selectByExample(example );
		 for (TbContentCategory category : categorys) {
			 TreeNodeResult result = new TreeNodeResult();
			 result.setId(category.getId());
			 result.setText(category.getName());
			 result.setState(category.getIsParent() ? "closed" : "open");		 
			 list.add(result);
		}
		return list;
	}

	@Override
	public E3Result save(Long parentId, String name) {
		TbContentCategory category = new TbContentCategory();
		category.setName(name);
		category.setParentId(parentId);
		category.setStatus(1);
		category.setCreated(new Date());
		category.setUpdated(new Date());
		category.setIsParent(false);
		category.setSortOrder(1);
		 mapper.insert(category);
		TbContentCategory parentcategory = new TbContentCategory();
		parentcategory.setId(parentId);
        parentcategory.setIsParent(true);
		return E3Result.ok(category);
		
	}

	@Override
	public E3Result delete(Long parentId, Long id) {
		
		TbContentCategory category = mapper.selectByPrimaryKey(id);
		E3Result e3Result = new E3Result();
		if(category.getIsParent()){
		
			e3Result.setStatus(0);
			return e3Result;
		}
		mapper.deleteByPrimaryKey(id);
		e3Result.setStatus(1);
		return e3Result;
		
	}

	@Override
	public E3Result update(Long id, String name) {
		E3Result e3Result = new E3Result();
		TbContentCategory category = mapper.selectByPrimaryKey(id);
		if(StringUtils.isNotBlank(name)){
			category.setName(name);
			mapper.updateByPrimaryKeySelective(category);
			e3Result.setStatus(1);		
			return e3Result;
		}
		e3Result.setStatus(0);		
		return e3Result;
	}

}
