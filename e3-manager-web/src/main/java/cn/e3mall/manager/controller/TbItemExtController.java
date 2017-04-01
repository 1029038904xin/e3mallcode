package cn.e3mall.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.manager.po.ext.TbItemExt;
import cn.e3mall.search.service.TbItemExtService;

@Controller
@RequestMapping("/index")
public class TbItemExtController {
	
	@Autowired
	private TbItemExtService extService;
	
	@RequestMapping("/importAll")
	@ResponseBody
	public E3Result imortAll(){	
		return extService.importAll();
	}
	
	

}
