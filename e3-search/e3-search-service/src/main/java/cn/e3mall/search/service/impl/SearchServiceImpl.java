/**
 * 
 */
package cn.e3mall.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.dao.SearchDao;

import cn.e3mall.search.service.SearchService;

/**
 * @author Alex
 * 2018年4月28日
 * <p>desc:实现solr搜索，封转搜索结果</p>
 */
@Service
public class SearchServiceImpl implements SearchService{
	@Autowired
	private SearchDao searchDao;

	/* (non-Javadoc)
	 * @see cn.e3mall.search.service.Search#search(java.lang.String, int, int)
	 */
	@Override
	public SearchResult search(String keyword, int page, int pageSize) throws SolrServerException {
		//new 一个solr条件对象
		SolrQuery solrQuery = new SolrQuery();
		//设置搜索关键ci
		solrQuery.setQuery(keyword);
		//设置默认查询 大域
		solrQuery.set("df", "item_keywords");
		//设置查询分页信息
		solrQuery.setStart((page-1)*pageSize);
		solrQuery.setRows(pageSize);
		//开启高亮标记
		solrQuery.setHighlight(true);
		
		//添加 高亮 显示的  小域
		solrQuery.addHighlightField("item_title");
		//设置高亮作用标签
		solrQuery.setHighlightSimplePre("<em color='red'>");
		solrQuery.setHighlightSimplePost("</em >");
		
        //执行查询，得到封装的pojo
		
		SearchResult searchResult = searchDao.query(solrQuery);
		//完善pojo
		int totalPages = (int) (searchResult.getRecourdCount()/pageSize);
		totalPages = (int)searchResult.getRecourdCount()%pageSize>0?totalPages++:totalPages;
		searchResult.setTotalPages(totalPages);
		return searchResult;
	}

	
}
