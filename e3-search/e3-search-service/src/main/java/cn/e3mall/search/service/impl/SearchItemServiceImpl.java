/**
 * 
 */
package cn.e3mall.search.service.impl;

import java.io.IOException;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.search.mapper.SearchItemMapper;
import cn.e3mall.search.service.SearchItemService;

/**
 * @author Alex
 * 2018年4月25日
 * <p>desc:将从数据库中抽取的数据导入solr</p>
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {
	@Autowired
	private SearchItemMapper searchItemMapper;
	@Autowired
	private SolrServer solrServer;
	
	/* (non-Javadoc)
	 * @see cn.e3mall.search.service.SearchItemService#importItemIndex()
	 */
	@Override
	public E3Result importItemIndex() {
		try {
			//从数据库中获得索引商品信息
			List<SearchItem> itemList = searchItemMapper.getItemList();
			//遍历所查询的商品列表，逐条将记录添加到索引库中
			for(SearchItem searchItem : itemList){
				//一条记录 对应 一个文档
				SolrInputDocument idoc = new SolrInputDocument();
				// 一个字段 对应 一个索引域
				idoc.addField("id", searchItem.getId());
				idoc.addField("item_title", searchItem.getTitle());
				idoc.addField("item_price", searchItem.getPrice());
				idoc.addField("item_image", searchItem.getImage());
				idoc.addField("item_sell_point", searchItem.getSell_point());
				idoc.addField("item_category_name", searchItem.getCategory_name());
				solrServer.add(idoc);
				solrServer.commit();
			}
			//返回成功的状态信息
			return E3Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(500,"添加商品到索引库失败");
		}
		
		
	}
	
	
	

}
