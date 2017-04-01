package cn.e3mall.manager.service;

import java.util.List;

import cn.e3mall.common.po.TreeNodeResult;
import cn.e3mall.manager.po.TbItemCat;

public interface TbItemCatService {

	 public List<TreeNodeResult> findTreeNode(Long parentId);
}
