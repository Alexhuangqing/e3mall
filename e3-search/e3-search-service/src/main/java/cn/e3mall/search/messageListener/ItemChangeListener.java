/**
 * 
 */
package cn.e3mall.search.messageListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.SearchItemMapper;

/**
 * @author Alex 2018年5月1日
 *         <p>
 *         desc:自定义消息监听器,完成抽取索引数据的增量更新
 *         </p>
 */
public class ItemChangeListener implements MessageListener {
	@Autowired
	private SearchItemMapper searchItemMapper;
	@Autowired
	private SolrServer solrServer;

	/*
	 * (non-Javadoc) consumer端 添加监听事件
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		try {
			Thread.sleep(100);
			TextMessage textMessage = (TextMessage) message;

			String itemId = textMessage.getText();

			// 从数据库中拿到同步数据 append同步索引库
			SearchItem searchItem = searchItemMapper.getSearchItemById(new Long(itemId));

			SolrInputDocument idoc = new SolrInputDocument();
			idoc.addField("id", searchItem.getId());
			idoc.addField("item_title", searchItem.getTitle());
			idoc.addField("item_price", searchItem.getPrice());
			idoc.addField("item_image", searchItem.getImage());
			idoc.addField("item_sell_point", searchItem.getSell_point());
			idoc.addField("item_category_name", searchItem.getCategory_name());
			solrServer.add(idoc);
			solrServer.commit();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
