/**
 * 
 */
package cn.e3mall.content.service;

import java.util.List;
import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;;

/**
 * @author Alex 2018年4月15日
 *         <p>
 *         desc:内容服务接口
 *         </p>
 * 
 */
public interface ContentCategoryService {
	
	/**
	 * @param parent
	 * @return List<EasyUITreeNode>
	 *<p>desc:得到商品的目录结构</p>
	 */
	public List<EasyUITreeNode> getContentCategoryList(Long parent);
	/**
	 * @param parentId
	 * @param name
	 * @return E3Result
	 *<p>desc:添加一个内容分类节点，同时分配给前台一个节点id</p>
	 */
	public E3Result addContentCategory(Long parentId,String name);
}
