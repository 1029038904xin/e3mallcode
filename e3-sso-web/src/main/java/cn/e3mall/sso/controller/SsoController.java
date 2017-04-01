package cn.e3mall.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.po.TbUser;
import cn.e3mall.sso.service.SsoService;


@Controller
@RequestMapping("/sso")
public class SsoController {
    @Autowired
	private  SsoService service;
    
    @RequestMapping("/register")
    @ResponseBody
    public E3Result regist(TbUser  user){
    	
    	return service.register(user);
    }
    @RequestMapping("/showRegister")
    public String  showregist(TbUser  user){
    	
    	return "register";
    }
    @RequestMapping("/showLogin")
    public String  showLogin(TbUser  user){
    	
    	return "login";
    }
    @RequestMapping("/login" )
    @ResponseBody
    public E3Result  login(String username,String password,HttpServletRequest request,HttpServletResponse response ){
    	System.out.println("%%%%%%%%%%%%%%%%");
    	E3Result result = service.login(username, password);
    	if(result.getData()==null){
    		return result;
    	}

    	 cn.e3mall.sso.utils.CookieUtils.setCookie(request, response, "TT_TOKEN", result.getData().toString(),true);
    	
    	return  result;
    }
    @RequestMapping("/token/{token}")
    @ResponseBody
    public String  checkToken(@PathVariable String token,String callback ){
    	E3Result result = service.selectByToken(token);
    	if(StringUtils.isEmpty(callback)){
    		return JsonUtils.objectToJson(result);
    		
    	}
    
    	
    	return callback + "(" + JsonUtils.objectToJson(result) + ")";
    }
    @RequestMapping("/a")
    public String rr(){
    	System.out.println("asdasdasda");
    	System.out.println("asdasdasda");
    	System.out.println("asdasdasda");
    	System.out.println("asdasdasda");
    	System.out.println("asdasdasda");
return null;
    
    }
}
