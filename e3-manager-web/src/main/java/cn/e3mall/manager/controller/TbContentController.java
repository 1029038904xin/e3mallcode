package cn.e3mall.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.manager.po.TbContent;
import cn.itcast.cms.servcie.TbContentService;

@Controller
@RequestMapping("/content")
public class TbContentController {

	@Autowired
	private TbContentService service;
	@RequestMapping("/query/list")
	@ResponseBody
	public DatagridResult quertList( Integer rows ,Integer page,Long categoryId ){
		
		
		return service.queryContentListByCid(rows, page, categoryId);
	}
	@RequestMapping("/save")
	@ResponseBody
	public E3Result save( TbContent content ){
		
		
		return service.save(content);
	}
	
}
