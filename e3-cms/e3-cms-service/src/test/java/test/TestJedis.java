package test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedis {
	private String host = "192.168.242.128";
	private int port = 6379;
	
	@Test
	public void testJedis(){
		Jedis jedis = new Jedis(host,port);
		
		jedis.set("s2", "ss,dd","asdas");
	
		System.out.println(	jedis.get("s2"));
		
		
		jedis.close();
		
	}
	
	@Test
	public void testJedisPool(){
		JedisPool jedisPool = new JedisPool(host,port);
		Jedis resource = jedisPool.getResource();
		resource.set("qq", "qq");
		System.out.println(resource.get("qq"));
		jedisPool.close();
		resource.close();
	}
	
	@Test
	public void testJedisCluster() {
		// 创建JedisCluster对象
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort(host, 7001));
		nodes.add(new HostAndPort(host, 7002));
		nodes.add(new HostAndPort(host, 7003));
		nodes.add(new HostAndPort(host, 7004));
		nodes.add(new HostAndPort(host, 7005));
		nodes.add(new HostAndPort(host, 7006));
		JedisCluster cluster = new JedisCluster(nodes);
		// 调用cluster的get/set方法
		cluster.set("u", "55kai");
		System.out.println(cluster.get("u"));
		// 释放资源
		cluster.close();
	}
	
	
	
	
	private ApplicationContext context;
	@Before
	public void init(){
		context=	new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		
	}

	@Test
	public void testSpringJedis(){
		
	 JedisPool bean = context.getBean(JedisPool.class);
	 System.out.println("萨达");
	Jedis jedis = bean.getResource();
	String string = jedis.set("性别", "男");
		System.out.println(string);
		jedis.close();
		
	}
	@Test
	public void testSpring(){
		
		JedisCluster bean = context.getBean(JedisCluster.class);
		bean.set("wwwwwwwww", "wwwwww");
		System.out.println(bean.get("wwwwwwwww"));
		
	}

}
