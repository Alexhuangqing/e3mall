/**
 * 
 */
package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;

/**
 * @author Alex
 * 2018年4月15日
 * <p>desc:实现内容服务接口</p>
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{
	
	@Autowired
	private TbContentCategoryMapper ContentCategoryMapper;

	/* (non-Javadoc)
	 * @see cn.e3mall.content.service.ContentCategoryService#getContentCategoryList(java.lang.Long)
	 */
	@Override
	public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = ContentCategoryMapper.selectByExample(example);
		
		List<EasyUITreeNode> nodes = new ArrayList<>();
		for(TbContentCategory contentCategory : list){
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(contentCategory.getId());
			node.setText(contentCategory.getName());
			node.setState(contentCategory.getIsParent()?"closed":"open");;
			nodes.add(node);
		}
		return nodes;
	}

	/* (non-Javadoc)
	 * @see cn.e3mall.content.service.ContentCategoryService#addContentCategory(java.lang.Long, java.lang.String)
	 */
	@Override
	public E3Result addContentCategory(Long parentId, String name) {
		//pojo对象 封装一个创建的类节点
		TbContentCategory contentCategory = new  TbContentCategory();
		contentCategory.setParentId(parentId); //新建节点的父节点是？
		contentCategory.setName(name);//新建节点的节点名字
		contentCategory.setStatus(1);//新建节点的节点状态  可选值:1(正常),2(删除)
		contentCategory.setSortOrder(1);//新建节点的节点排序，默认都是1
		contentCategory.setIsParent(false);//新建节点的父节点是？
		Date curr = new Date();
		contentCategory.setCreated(curr);
		contentCategory.setUpdated(curr);
		//重写insert方法    获得自增主键  id自动返回给封转的对象
		ContentCategoryMapper.insert(contentCategory);
		TbContentCategory parentNode = ContentCategoryMapper.selectByPrimaryKey(parentId);
		//如果父节点是叶子节点，则需要重新调整节点的“开合”状态
		if(!parentNode.getIsParent()){
			parentNode.setIsParent(true);
			ContentCategoryMapper.updateByPrimaryKeySelective(parentNode);
		}
		
		return E3Result.ok(contentCategory);
	}
	
	
	

}
