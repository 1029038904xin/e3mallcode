package cn.e3mall.manager.service;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.manager.po.TbItem;

public interface TbItemService {
	 public TbItem  getItembyId(Long id);

	public DatagridResult findAll(Integer page, Integer rows);
	public E3Result  save(TbItem  item);

}
