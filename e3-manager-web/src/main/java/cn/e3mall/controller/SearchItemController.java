/**
 * 
 */
package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.search.service.SearchItemService;

/**
 * @author Alex
 * 2018年4月25日
 * <p>desc:后台一键导入商品信息到索引库</p>
 */
@Controller
public class SearchItemController {
	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping(value ="/index/item/import", method = RequestMethod.POST )
	@ResponseBody
	public E3Result importItemList(){
		E3Result e3Result = searchItemService.importItemIndex();
		return e3Result;
	}

}
