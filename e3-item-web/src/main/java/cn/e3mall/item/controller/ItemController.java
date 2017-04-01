package cn.e3mall.item.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.ext.TbItemExt;
import cn.e3mall.manager.service.TbItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private TbItemService service;
	
	@RequestMapping("/{itemId}")
	public String showDetail(@PathVariable Long itemId, Model model) {
		   TbItem itembyId = service.getItembyId(itemId);
		//使用扩展类解决多图片问题
		TbItemExt itemExt = new TbItemExt();
		//使用工具类复制对象属性
		if (itembyId != null)
			BeanUtils.copyProperties(itembyId, itemExt);
		model.addAttribute("item", itemExt);
		return "item";
	}


	
}
