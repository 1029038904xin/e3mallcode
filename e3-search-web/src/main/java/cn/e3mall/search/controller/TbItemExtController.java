package cn.e3mall.search.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.search.service.TbItemExtService;

@Controller
public class TbItemExtController {
    
	@Autowired
	private TbItemExtService extService;
	@RequestMapping("/search")
	public String search(@RequestParam(defaultValue="1")  Integer page, String q,Model model){
		try {
	               	q = new String(q.getBytes("iso8859-1"),"utf-8");
                  
			         Map<String, Object> search = extService.search(q, page);
			 		// 将搜索结果放到request域中
						model.addAttribute("itemList", search.get("itemList"));
						model.addAttribute("page", page);
						model.addAttribute("totalPages", search.get("totalPages"));
			            model.addAttribute("totalRecords", search.get("totalRecords"));
						model.addAttribute("query", q);	         
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "就不让你看");
			return "error/exception";	
		}
		return "search";
	}
}
