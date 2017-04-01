package cn.e3mall.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.po.TreeNodeResult;
import cn.itcast.cms.servcie.ContentCategoryService;

@Controller
@RequestMapping("/contentCategory")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService service;
	
	@RequestMapping("/list")
	@ResponseBody
	public  List<TreeNodeResult> queryCategistoryL(@RequestParam(value = "id", defaultValue="0") Long parentId) {
		return service.queryCategoryList(parentId);
		
	}
	@RequestMapping("/create")
	@ResponseBody
	public  E3Result save(Long parentId, String name) {
		System.out.println("asdsa");
		return service.save(parentId, name);
		
	}
	@RequestMapping("/delete")
	@ResponseBody
	public  E3Result save(Long parentId, Long id) {
		
		return service.delete(parentId, id);
		
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public  E3Result update(String name, Long id) {
		
		return service.update(id, name);
		
	}
	

}
