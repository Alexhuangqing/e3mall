/**
 * 
 */
package cn.e3mall.item.pojo;

import cn.e3mall.pojo.TbItem;

/**
 * @author Alex 2018年5月1日
 *         <p>
 * 		desc:null
 *         </p>
 */
public class Item extends TbItem {

	public Item(TbItem tbItem) {
		this.setId(tbItem.getId());
		this.setTitle(tbItem.getTitle());
		this.setSellPoint(tbItem.getSellPoint());
		this.setPrice(tbItem.getPrice());
		this.setNum(tbItem.getNum());
		this.setBarcode(tbItem.getBarcode());
		this.setImage(tbItem.getImage());
		this.setCid(tbItem.getCid());
		this.setStatus(tbItem.getStatus());
		this.setCreated(tbItem.getCreated());
		this.setUpdated(tbItem.getUpdated());
	}

	public String[] getImages() {
		String image = getImage();
		String[] images = null;
		if (image != null && !"".equals(image)) {
			images = image.split(",");
		}
		return images;
	}

}
