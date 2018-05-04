/**
 * 
 */
package cn.e3mall.search.service;

import cn.e3mall.common.pojo.SearchResult;

/**
 * @author Alex
 * 2018年4月28日
 * <p>desc:提供索引查询服务</p>
 */
public interface SearchService {
	
	public SearchResult search(String keyword,int page ,int pageSize) throws Exception;

}
