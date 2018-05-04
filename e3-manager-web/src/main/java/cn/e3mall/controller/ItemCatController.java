
package cn.e3mall.controller;

import java.util.List;   

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.service.ItemCatService;


/**
 * @author Alex
 * 2018年4月14日
 * <p>desc:商品分类目录显示器</p>
 */
@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * @param parentId  父id 默认值为0
	 * @return List<EasyUITreeNode> 
	 *<p>desc:根据父id得到目录列表</p>
	 */
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList
	(@RequestParam(name="id",defaultValue="0") Integer parentId){
		List<EasyUITreeNode> result = itemCatService.getCatList(parentId);
		
		return result;	
	}
}
