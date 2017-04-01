package cn.e3mall.manager.po.ext;

import org.apache.commons.lang3.StringUtils;

import cn.e3mall.manager.po.TbItem;

public class TbItemExt  extends  TbItem{

	  private String catName;
	  private String[]  images;

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String[] getImages() {
		/*if(this.getImage()!=null){
			return  this.getImage().split(",");
		}
*/
		if(StringUtils.isNoneBlank(this.getImage())){
			return  this.getImage().split(",");
		}
	
		return new String[] {"www.baidu.com"};
	}

	public void setImages(String[] images) {
		
		
		this.images = images;
	}
	  
	
	
	
}
