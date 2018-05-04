/**
 * 
 */
package cn.e3mall.solrj;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @author Alex 2018年4月25日
 *         <p>
 * 		desc:null
 *         </p>
 */
public class TsetSolrj {

	/**
	 * @throws Exception
	 *             <p>
	 * 			desc:测试向索引库中添加文档
	 *             </p>
	 */
	@Test
	public void addDocument() throws Exception {
		// 创建一个solrServer对象 参数是solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://192.168.1.151:8989/solr/collection1");
		// 创建一个文档对象SolrInputDocument
		SolrInputDocument docs = new SolrInputDocument();
		// 向文档中添加域，文档中必须包含一个id域(id域被定义的是一个String类型)，所有域的名称必须在schema.xml中定义
		docs.addField("id", "test01");
		docs.addField("item_price", 123456);
		docs.addField("item_category_name", "手机");
		// 把文档写入索引库
		solrServer.add(docs);
		// 提交
		solrServer.commit();
	}

	/**
	 * @throws Exception
	 *             <p>
	 * 			desc:测试删除索引库中的文档
	 *             </p>
	 */
	@Test
	public void delDocument() throws Exception {
		// 创建一个solrServer对象 参数是solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://192.168.1.151:8989/solr/collection1");

		// 根据id域删除文档
		solrServer.deleteById("test01");

		// 通过查找 来 删除文档
		// solrServer.deleteByQuery("id:test01");

		// 提交
		solrServer.commit();
	}

	/**
	 * @throws SolrServerException
	 * @throws Exception
	 *             <p>
	 * 			desc:简单测试
	 *             </p>
	 */
	@Test
	public void testSimpleIndex() throws SolrServerException {
		// 创建一个solr服务器对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.1.151:8989/solr/collection1");

		// new一个查询条件
		SolrQuery solrQuery = new SolrQuery();
		// 设置过滤条件 不设置分页 默认是 limit 1,10
		solrQuery.setQuery("*:*");

		QueryResponse response;

		response = solrServer.query(solrQuery);

		SolrDocumentList results = response.getResults();
		for (SolrDocument doc : results) {// 遍历结果中的doc
			System.out.print(doc.get("id"));
			System.out.print(doc.get("item_title"));
			System.out.print(doc.get("item_price"));
			System.out.print(doc.get("item_image"));
			System.out.print(doc.get("item_sell_point"));
			System.out.println(doc.get("item_category_name"));

		}

	}
	
	
	/**
	 * @throws SolrServerException
	 *<p>desc:复杂查询</p>
	 */
	@Test
	public void textComplixIndex() throws SolrServerException{
		// 创建一个solr服务器对象
				SolrServer solrServer = new HttpSolrServer("http://192.168.1.151:8989/solr/collection1");

				// new一个查询条件
				SolrQuery solrQuery = new SolrQuery();
				// 设置过滤条件 不设置分页 默认是 limit 1,10  (注意查询条件的两种写法)
				solrQuery.setQuery("三星");
				
				// 设置默认查询域
				solrQuery.set("df","item_keywords");
				//开启高亮
				solrQuery.setHighlight(true);
				
			   solrQuery.addHighlightField("item_title");//将一个域 标记 为高亮域
			   
			   solrQuery.setHighlightSimplePre("<em>"); 
			   solrQuery.setHighlightSimplePost("</em>");
			   
			   QueryResponse queryResponse = solrServer.query(solrQuery);
			   
			   
			   SolrDocumentList results = queryResponse.getResults();
			   System.out.println(results.getNumFound());

				for (SolrDocument doc : results) {// 遍历结果中的doc
					System.out.print(doc.get("id"));
					Map<String, List<String>> title = queryResponse.getHighlighting().get(doc.get("id"));
					String item_title = "";
					if(title != null && title.size() == 1){
						item_title = title.get("item_title").get(0);
					}else{
						item_title = doc.get("item_title").toString();
					}
					System.out.print(item_title);
					
					System.out.print(doc.get("item_price"));
					System.out.print(doc.get("item_image"));
					System.out.print(doc.get("item_sell_point"));
					System.out.println(doc.get("item_category_name"));

				}

		
	}

}
