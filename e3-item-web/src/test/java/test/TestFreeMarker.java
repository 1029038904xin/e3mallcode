package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.expression.MapAccessor;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class TestFreeMarker {

	  @Test
	  public void   freeMark() throws Exception{
		  //创建configuration
		  Configuration conf = new Configuration(Configuration.getVersion());
		  //设置模板的路径
		  conf.setDirectoryForTemplateLoading(new File("E:\\e3mall\\e3-item-web\\src\\main\\resources\\ftl"));
		  //设置编码格式
		  conf.setDefaultEncoding("utf-8");
		  //获取模板
		  Template tem = conf.getTemplate("hello.ftl");
		  //创建数据类型
		  Map  map = new HashMap<>();
		  map.put("你好", "freeMarker");
		  //创建文件的输出流
		  Writer out = new FileWriter(new File("E:\\freemarkerOut\\hello.html"));
		  //调用模板的process方法
		  tem.process(map, out);
		  //关闭流
		  out.close();
		  
	  }
	  @Test
	  public void   freeMarkPojo() throws Exception{
		  //创建configuration
		  Configuration conf = new Configuration(Configuration.getVersion());
		  //设置模板的路径
		  conf.setDirectoryForTemplateLoading(new File("E:\\e3mall\\e3-item-web\\src\\main\\resources\\ftl"));
		  //设置编码格式
		  conf.setDefaultEncoding("utf-8");
		  //获取模板
		  Template tem = conf.getTemplate("hello.ftl");
		  //创建数据类型
		  Map  map = new HashMap<>();
		 // map.put("你好", "freeMarker");
		  map.put("student", new Student(1, "zhangsan"));
		  map.put("today", new Date());
		  
			List<Student> stuList = new ArrayList<>();
			stuList.add(new Student(1, "张三1"));
			stuList.add(new Student(2, "张三2"));
			stuList.add(new Student(3, "张三3"));
			stuList.add(new Student(4, "张三4"));
			stuList.add(new Student(5, "张三5"));
			stuList.add(new Student(6, "张三6"));
			map.put("stulist", stuList);

		  
		  
		  //创建文件的输出流
		  Writer out = new FileWriter(new File("E:\\freemarkerOut\\pojo.html"));
		  //调用模板的process方法
		  tem.process(map, out);
		  //关闭流
		  out.close();
		  
	  }
}
