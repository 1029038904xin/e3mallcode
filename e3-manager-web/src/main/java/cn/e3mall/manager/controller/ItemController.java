package cn.e3mall.manager.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.service.TbItemService;



@Controller
@RequestMapping("item")
public class ItemController {

	@Autowired
  private TbItemService service;
	@RequestMapping("test1/{id}")
	@ResponseBody
	public TbItem  test(@PathVariable("id")Long id){

		return service.getItembyId(id);
	}
	@RequestMapping("list")
	@ResponseBody
	public DatagridResult  showItemList(Integer page ,Integer rows){
		return  service.findAll(page,rows);
	
	}
	
	@RequestMapping("save")
	@ResponseBody
	public E3Result  save(TbItem item){
		return  service.save(item);
		
	}
	
}
