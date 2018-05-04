/**
 * 
 */
package cn.e3mall.search.mapper;

import java.util.List;

import cn.e3mall.common.pojo.SearchItem;

/**
 * @author Alex
 * 2018年4月25日
 * <p>desc:从数据库中抽取出数据</p>
 */
public interface SearchItemMapper {
    
	/**
	 * @return
	 *<p>desc:从数据库中抽取全部索引数据</p>
	 */
	List<SearchItem> getItemList();
	
	/**
	 * @param id
	 * @return
	 *<p>desc:从数据库中抽取符合id的索引数据</p>
	 */
	SearchItem getSearchItemById(Long itemId);
}
