package cn.e3mall.sso.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.mapper.TbUserMapper;
import cn.e3mall.manager.po.TbUser;
import cn.e3mall.manager.po.TbUserExample;
import cn.e3mall.manager.po.TbUserExample.Criteria;
import redis.clients.jedis.JedisCluster;
@Service
public class SsoServiceImpl implements SsoService {

	@Autowired
	private TbUserMapper  mapper;
	
	@Autowired
	private JedisCluster cluster;
	
	@Override
	 //判断user是否为空
	//判断username是否为空
	//判断密码是为空
	//判断手机号是否为空
	// 对用户名唯一性校验
	//对手机号进行唯一校验
	// 校验成功，则需要对密码加密
	// 需要补全用户的created和updated属性
	// 插入到用户表
	public E3Result register(TbUser user) {
		 //判断user是否为空
		if(user==null){
			return E3Result.build(500, "用户信息不能为空");
			
		}
		//判断username是否为空
		if(StringUtils.isEmpty(user.getUsername())){
			return E3Result.build(500, "用户名不能为空");
		}
		//判断密码是为空
		if(StringUtils.isEmpty(user.getPassword())){
			return E3Result.build(500, "密码不能为空");
		}
		//判断手机号是否为空
		if(StringUtils.isEmpty(user.getPhone())){
			return E3Result.build(500, "手机号不能为空");
		}
		// 对用户名唯一性校验
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(user.getUsername());
		List<TbUser> list = mapper.selectByExample(example );
		if(list!=null && list.size()>0){
			return E3Result.build(500, "用户名已存在");
		}
		example.clear();
		//对手机号进行唯一校验
		Criteria criteria2 = example.createCriteria();
		criteria2.andUsernameEqualTo(user.getPhone());
		List<TbUser> list2 = mapper.selectByExample(example );
		if(list2!=null && list2.size()>0){
			return E3Result.build(500, "手机号已存在");
		}
		// 校验成功，则需要对密码加密
		String md5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		
		user.setPassword(md5);
		// 需要补全用户的created和updated属性
		user.setUpdated(new Date());
		user.setCreated(new Date());
		// 插入到用户表
		mapper.insert(user);
		return E3Result.ok();
	}


	@Override
	public E3Result login(String username, String password) {
		//将提交上来的数据进行校验
		if(StringUtils.isEmpty(username)){
			return E3Result.build(500, "用户名不能为空");
			
		}
		if(StringUtils.isEmpty(password)){
			return E3Result.build(500, "密码不能为空");
			
		}
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = mapper.selectByExample(example );
		if(list ==null ||    list.size()!=1){
			return E3Result.build(500, "该用户不存在");
		}
		//生成UUID
		String token = UUID.randomUUID().toString();
		TbUser user = list.get(0);
		password = DigestUtils.md5DigestAsHex(password.getBytes());
		if(!password.equals(user.getPassword())){
			return E3Result.build(500, "密码错误");
		}
		try {
			cluster.hset("Item"+token, "user", JsonUtils.objectToJson(user));
			cluster.expire("Item" + token, 864000);
		} catch (Exception e) {
			return E3Result.build(500, "登录失败");
		}

		return E3Result.ok(token);
	}


	@Override
	public E3Result selectByToken(String token) {
		if(StringUtils.isEmpty(token)){
			return E3Result.build(500, "您还没有登录");
		}
		
			String hget = cluster.hget("Item"+token, "user");
			if(StringUtils.isEmpty(token)){
				return E3Result.build(500, "您还没有登录");
			}
			TbUser user = JsonUtils.jsonToPojo(hget, TbUser.class);
			cluster.expire("Item"+token, 86400);
			return E3Result.ok(user);
			

	
	}
}
