/**

 * 
 */
package cn.e3mall.service.impl;

import java.util.ArrayList;  
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.pojo.TbItemCatExample.Criteria;
import cn.e3mall.service.ItemCatService;



/**
 * @author Alex
 * 2018年4月14日
 * <p>desc:null</p>
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper tbItemCatMapper;

	
	/* (non-Javadoc)
	 * @see cn.e3mall.service.ItemCatService#getCatList(long)
	 */
	@Override
	public List<EasyUITreeNode> getCatList(long parentId) {
		//生成模板实例
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//根据实例执行查询
		List<TbItemCat> TbItemCats = tbItemCatMapper.selectByExample(example);
		//根据返回结果包装成表现层可交互pojo
		List<EasyUITreeNode> result = new ArrayList<>();
		for(TbItemCat tbItemCat:TbItemCats){
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			result.add(node);
		}
		return result;
	}

}
