/**
 * 
 */
package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;


/**
 * @author Alex
 * 2018年4月14日
 * <p>desc:商品查询控制器</p>
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	
	
	
	/**
	 * @param itemId
	 * @return TbItem 
	 *<p>desc:查询单个商品</p>
	 */
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public 	TbItem getItemById(@PathVariable Long itemId ){
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	
	
	/**
	 * @param page 当前页数
	 * @param rows 每页显示数
	 * @return EasyUIDataGridResult
	 *<p>desc:分页查询列表</p>
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows){
		EasyUIDataGridResult result = itemService.getList(page, rows);
		return result;
	}
	
	
	/**
	 * @param tbItem 封装前台的表单域
	 * @param desc 封装前台的表单域
	 * @return E3Result
	 *<p>desc:填加商品，请求必须是post</p>
	 */
	@RequestMapping(value ="/item/save" ,method = RequestMethod.POST)
	@ResponseBody
	public E3Result addItem(TbItem tbItem, String desc){
		E3Result result = itemService.addItem(tbItem, desc);
		return result;
	}
	
}
