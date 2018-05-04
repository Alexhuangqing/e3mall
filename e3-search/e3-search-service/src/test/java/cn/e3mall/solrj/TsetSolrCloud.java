/**
 * 
 */
package cn.e3mall.solrj;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
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
public class TsetSolrCloud {

	/**
	 * @throws Exception
	 *             <p>
	 * 			desc:测试向索引库中添加文档
	 *             </p>
	 */
	@Test
	public void addDocument() throws Exception {
		// 创建一个solrServer对象 参数是zookeeper中注册的节点列表zkHost 同时制定默认collection
		CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.1.151:2191,192.168.1.151:2192,192.168.1.151:2193");
		cloudSolrServer.setDefaultCollection("collection2");
		// 创建一个文档对象SolrInputDocument
		SolrInputDocument docs = new SolrInputDocument();
		// 向文档中添加域，文档中必须包含一个id域(id域被定义的是一个String类型)，所有域的名称必须在schema.xml中定义
		docs.addField("id", "test01");
		docs.addField("item_price", 123456);
		docs.addField("item_category_name", "手机");
		// 把文档写入索引库
		cloudSolrServer.add(docs);
		// 提交
		cloudSolrServer.commit();
	}

	/**
	 * @throws Exception
	 *             <p>
	 * 			desc:测试删除索引库中的文档
	 *             </p>
	 */
	@Test
	public void delDocument() throws Exception {
		// 创建一个solrServer对象 参数是zookeeper中注册的节点列表zkHost 同时制定默认collection
		CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.1.151:2191,192.168.1.151:2192,192.168.1.151:2193");
		cloudSolrServer.setDefaultCollection("collection2");
		// 根据id域删除文档
		cloudSolrServer.deleteById("test01");

		// 通过查找 来 删除文档
		// solrServer.deleteByQuery("id:test01");

		// 提交
		cloudSolrServer.commit();
	}

	
		
	

}
