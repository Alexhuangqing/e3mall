/**
 * 
 */
package cn.e3mall.search.service;

import cn.e3mall.common.utils.E3Result;

/**
 * @author Alex
 * 2018年4月25日
 * <p>desc:null</p>
 */
public interface SearchItemService {
	/**
	 * 一键将数据库的商品信息导入到solr索引库中
	 * @return
	 *<p>desc:null</p>
	 */
	public E3Result importItemIndex();
	
	
	

}
