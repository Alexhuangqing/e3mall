/**
 * 
 */
package cn.e3mall.service;

import java.util.List; 

import cn.e3mall.common.pojo.EasyUITreeNode;


/**
 * @author Alex
 * 2018年4月14日
 * <p>desc:商品类别接口</p>
 */
public interface ItemCatService {
	/**
	 * 由父节点得到子目录
	 * @param parentId
	 * @return 节点列表
	 *<p>desc:null</p>
	 */
	public List<EasyUITreeNode> getCatList(long parentId);

}
