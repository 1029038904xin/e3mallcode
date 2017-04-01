package cn.e3mall.manager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.po.TreeNodeResult;
import cn.e3mall.manager.mapper.TbItemCatMapper;
import cn.e3mall.manager.po.TbItemCat;
import cn.e3mall.manager.po.TbItemCatExample;
import cn.e3mall.manager.po.TbItemCatExample.Criteria;
@Service
public class TbItemCatServiceImpl implements TbItemCatService {

	 @Autowired
	 private TbItemCatMapper catMapper;
	@Override
	public List<TreeNodeResult> findTreeNode(Long parentId) {
		if(parentId==null)
			parentId=0L;
		
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = catMapper.selectByExample(example);
		 List<TreeNodeResult> nodelist=new ArrayList<TreeNodeResult>();
		for (TbItemCat cat : list) {
			TreeNodeResult result = new TreeNodeResult();
		     
			result.setId(cat.getId());
			result.setText(cat.getName());
			result.setState(cat.getIsParent()? "closed":"open");			
			nodelist.add(result);
		}	
		return nodelist;
	}

}
