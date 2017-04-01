package cn.e3mall.manager.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.po.DatagridResult;
import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.manager.mapper.TbItemMapper;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.TbItemExample;
@Service
public class MessageListener  implements TbItemService{
   @Autowired
	private TbItemMapper mapper;
	@Override
	public TbItem getItembyId(Long id) {
		///TbItem selectByPrimaryKey = mapper.selectByPrimaryKey(id);
		// TODO Auto-generated method stub
		System.out.println(id);
		TbItem key = mapper.selectByPrimaryKey(id);
		System.out.println("*******"+key);
		return  key;
	}
	@Override
	public DatagridResult findAll(@RequestParam(defaultValue = "1")Integer page, Integer rows) {
		//PageHelper.startPage(pageNum, pageSize, count);
		if(page==null){
			page=1;
		}
		if(rows==null){
			rows=20;
		}
		PageHelper.startPage(page, rows);
		
		TbItemExample example = new TbItemExample() ;
		List<TbItem> list = mapper.selectByExample(example);
		PageInfo<TbItem > pageInfo = new PageInfo<>(list);
		System.out.println("总记录数：" + pageInfo.getTotal());
		System.out.println("总页数：" + pageInfo.getPages());
		DatagridResult datagridResult = new DatagridResult();
		datagridResult.setRows(pageInfo.getList());
		datagridResult.setTotal(pageInfo.getTotal());
		return datagridResult;
	}
	@Override
	public E3Result save(TbItem item) {
		//生成商品id
		long genItemId = IDUtils.genItemId(); 
		item.setId(genItemId);
		item.setStatus((byte )1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//补全update  status  creaet
	
		//执行插入数据
		mapper.insert(item);
		
		return E3Result.ok();
	}
	

}
