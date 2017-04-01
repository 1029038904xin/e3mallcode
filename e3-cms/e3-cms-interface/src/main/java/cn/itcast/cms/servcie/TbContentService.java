package cn.itcast.cms.servcie;

import java.util.List;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.manager.po.TbContent;

public interface TbContentService {
	//rows、page、categoryId
	public DatagridResult  queryContentListByCid(Integer rows, Integer page, Long categoryId);
	
	public E3Result save(TbContent  content);
	
	public List<TbContent> queryContentList(Long categoryId);

}
