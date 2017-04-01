package cn.e3mall.manager.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.manager.utils.FastDFSClient;

@RequestMapping("pic")
@Controller
public class PicController {
	@Value("${imgurl}")
	private String   imgurl;
	
   @RequestMapping("upload")
   @ResponseBody
	 public   Map<String ,Object> upload(MultipartFile  uploadFile) {
		 Map<String ,Object> map = new HashMap<>();
		 if(uploadFile ==null){
			 map.put("error",1);
			 map.put("message","上传文件不能为空");
			 return map;
		 }
	  
		 String filename = uploadFile.getOriginalFilename();
		 if(StringUtils.isEmpty(filename)){
			 map.put("error",1);
			 map.put("message","上传文件名称不能为空");
			 return map;
		 }
		 //获取文件扩张明
		 String extName = filename.substring(filename.indexOf(".")+1);
		 if(!extName.toUpperCase().equals("JPG")){
			 
			 map.put("error",1);
			 map.put("message","上传文件格式必须为JPG");
			 return map;
		 }
		 FastDFSClient client;
		try {
			client = new FastDFSClient();
			 String imgpath = client.uploadFile(uploadFile.getBytes(), extName);
			 String realpath =imgurl+imgpath;
			 map.put("error",0);
			 map.put("url",realpath);
			 return map;
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 map.put("error",1);
			 map.put("message","上传失败");
			 return map;
		}
		
		 
		 
		
	 }
}
