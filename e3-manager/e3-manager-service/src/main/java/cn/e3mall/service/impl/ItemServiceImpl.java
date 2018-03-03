/**
 * 
 */
package cn.e3mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.pojo.TbItemExample.Criteria;
import cn.e3mall.service.ItemService;

/**
 * @author Alex
 * 2018年3月3日 
 * @className 
 * @Description
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private  TbItemMapper tbItemMapper;
	
	
	/* (non-Javadoc)
	 * @see cn.e3mall.service.ItemService#getItemById(long)
	 */
	@Override
	public TbItem getItemById(Long id) {
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		List<TbItem> tbItems = tbItemMapper.selectByExample(example);
		if(tbItems != null && tbItems.size()>0){
			return tbItems.get(0);
		}
		return null;
	}

}
