/**
 * 
 */
package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;

/**
 * @author Alex
 * 2018年4月16日
 * <p>desc:内容分类管理模块</p>
 */
@Controller
public class ContentCastController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	/**
	 * @param parentId
	 * @return List<EasyUITreeNode>
	 *<p>desc:根据父id 加载子节点</p>
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCastList(@RequestParam(name="id",defaultValue="0")Long parentId){
		List<EasyUITreeNode> result = contentCategoryService.getContentCategoryList(parentId);
		
		return result;
	}
	/**
	 * @param parentId 新增节点的父节点id
	 * @param name 新增节点的节点名称
	 * @return 返回节点的包装（前台主要获取节点id）
	 *<p>desc:null</p>
	 */
	@RequestMapping(value="/content/category/create",method=RequestMethod.POST)
	@ResponseBody
	public E3Result getContentCastList(Long parentId,String name){
		 E3Result e3Result = contentCategoryService.addContentCategory(parentId,name);
		return e3Result;
	}

}
