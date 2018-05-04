/**
 * 
 */
package cn.e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;

/**
 * @author Alex
 * 2018年5月1日
 * <p>desc:点击搜索结果，跳转到商品详情页面</p>
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("item/{itemId}")
	public String getItemById(@PathVariable(value="itemId") Long id,Model model){
		
		TbItem tbItem = itemService.getItemById(id);
		Item item = new Item(tbItem);
		TbItemDesc itemDesc = itemService.getItemDescById(id);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}

}
