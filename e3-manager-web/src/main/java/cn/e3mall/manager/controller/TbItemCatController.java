package cn.e3mall.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.po.TreeNodeResult;
import cn.e3mall.manager.service.TbItemCatService;
///itemCat/list
@Controller
@RequestMapping("itemCat")
public class TbItemCatController {
	
	@Autowired
	private TbItemCatService   catService;
	
	@RequestMapping("list")
	@ResponseBody
	public List<TreeNodeResult> findTreeNode( @RequestParam( value="id" ,defaultValue="0")  Long parentId){
		
		return catService.findTreeNode(parentId);
	}

}
