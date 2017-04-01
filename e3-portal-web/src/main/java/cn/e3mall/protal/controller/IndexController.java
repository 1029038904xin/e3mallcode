package cn.e3mall.protal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.common.po.AdResult;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.po.TbContent;
import cn.itcast.cms.servcie.TbContentService;

@Controller
public class IndexController {
	
	@Autowired
	private TbContentService contentService;
	@Value("${AD1_CATEGORY_ID}")
	private Long AD1_CATEGORY_ID;
	@Value("${AD1_WIDTH}")
	private int AD1_WIDTH;

	@Value("${AD1_HEIGHT}")
	private int AD1_HEIGHT;

	@Value("${AD1_WIDTHB}")
	private int AD1_WIDTHB;

	@Value("${AD1_HEIGHTB}")
	private int AD1_HEIGHTB;

	
	@RequestMapping("/")
	public String index( Model model){	
		List<AdResult> list = new ArrayList<>();
		List<TbContent> contentList = contentService.queryContentList(89L);
		AdResult result;
		for (TbContent tbContent : contentList) {
			result= new AdResult();
			result.setAlt(tbContent.getTitle());
			result.setHref(tbContent.getUrl());
			result.setSrc(tbContent.getPic());
			result.setSrcB(tbContent.getPic2());
			//页面属性
			result.setHeight(AD1_HEIGHT);
			result.setWidth(AD1_WIDTH);
			result.setHeightB(AD1_HEIGHTB);
			result.setWidthB(AD1_WIDTHB);
			list.add(result);		
		}	
		model.addAttribute("ad1", JsonUtils.objectToJson(list));
		return "index";
	}

	/*
	 * 
	 * */
}
