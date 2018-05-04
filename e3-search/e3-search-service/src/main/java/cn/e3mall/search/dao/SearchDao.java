/**
 * 
 */
package cn.e3mall.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;

/**
 * @author Alex
 * 2018年4月28日
 * <p>desc:根据查询条件从solr中检索商品信息</p>
 */
@Repository
public class SearchDao {
	@Autowired
	private SolrServer solrServer;
	
	public SearchResult  query(SolrQuery query) throws SolrServerException{
		
		SearchResult searchResult = new SearchResult();
		
		QueryResponse queryResponse = solrServer.query(query);
		
		SolrDocumentList results = queryResponse.getResults();
		 
		searchResult.setRecourdCount(results.getNumFound());
		List<SearchItem> itemList = new ArrayList<SearchItem>();
		
		for (SolrDocument doc : results) {// 遍历结果中的doc
			SearchItem item = new SearchItem();
			item.setId((String) doc.get("id"));
			Map<String, List<String>> title = queryResponse.getHighlighting().get(doc.get("id"));
			String item_title = "";
			if(title != null && title.size() == 1){
				item_title = title.get("item_title").get(0);
			}else{
				item_title = doc.get("item_title").toString();
			}
			item.setTitle(item_title);
			
			item.setPrice((long) doc.get("item_price"));
			item.setImage((String) doc.get("item_image"));
			item.setSell_point((String) doc.get("item_sell_point"));
			item.setCategory_name((String) doc.get("item_category_name"));
			itemList.add(item);
		}
		searchResult.setItemList(itemList);
		return searchResult;
		
	}

}
