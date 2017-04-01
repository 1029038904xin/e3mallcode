package cn.e3mall.search.service;

import java.util.List;
import java.util.Map;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.manager.po.ext.TbItemExt;

public interface TbItemExtService {

	public E3Result importAll();
	
	public Map<String, Object> search(String q, Integer page) throws Exception;
}
