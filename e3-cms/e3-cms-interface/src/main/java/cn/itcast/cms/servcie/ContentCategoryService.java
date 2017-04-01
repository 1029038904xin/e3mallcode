package cn.itcast.cms.servcie;

import java.util.List;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.po.TreeNodeResult;

public interface ContentCategoryService {
	
	public  List<TreeNodeResult>  queryCategoryList(Long parentId);
	
	public E3Result save(Long parentId,String name);
	public E3Result update(Long id,String name);
	
	public E3Result delete(Long parentId,Long id);
	

}
