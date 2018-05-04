/**
 * 
 */
package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.pojo.TbItemExample.Criteria;
import cn.e3mall.service.ItemService;

/**
 * @author Alex 2018年4月14日
 *         <p>
 *         desc:null
 *         </p>
 */
@Service
public class ItemServiceImpl implements ItemService {
	/**
	 * Autowired 根据类型注入 Resource 根据id注入（如下 Destination 有两种实现 只能根据id注入）
	 */
	@Autowired
	private TbItemMapper tbItemMapper;

	@Autowired
	private TbItemDescMapper tbItemDescMapper;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Resource
	private Destination topicDestination;

	@Autowired
	private JedisClient jedisClient;

	@Value(value = "ITEM_INFO_PRE")
	private String ITEM_INFO;
	@Value(value = "ITEM_EXPIRE_TIME")
	private Integer ITEM_EXPIRE_TIME;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.e3mall.service.ItemService#getItemById(long)
	 */
	@Override
	public TbItem getItemById(Long id) {
		try {
			// 查找缓存 缓存操作是独立的 异常不应该不影响正常的操作
			String itemStr = jedisClient.get(ITEM_INFO + ":" + id + ":BASE");
			if (StringUtils.isNotEmpty(itemStr)) {
				return JsonUtils.jsonToPojo(itemStr, TbItem.class);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		List<TbItem> tbItems = tbItemMapper.selectByExample(example);
		if (tbItems != null && tbItems.size() > 0) {
			try {
				// 将查找的数据加入缓存
				jedisClient.set(ITEM_INFO + ":" + id + ":BASE", JsonUtils.objectToJson(tbItems.get(0)));
				// 设置过期时间
				jedisClient.expire(ITEM_INFO + ":" + id + ":BASE", ITEM_EXPIRE_TIME);
			} catch (Exception e) {

				e.printStackTrace();
			}

			return tbItems.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.e3mall.service.ItemService#getList(int, int)
	 */
	@Override
	public EasyUIDataGridResult getList(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);

		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();

		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(total);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.e3mall.service.ItemService#addItem(cn.e3mall.pojo.TbItem,
	 * java.lang.String)
	 */
	@Override
	public E3Result addItem(TbItem tbItem, String desc) {
		final Long id = IDUtils.genItemId();
		// 添加记录到商品表
		tbItem.setId(id);
		tbItem.setStatus((byte) 1);
		Date createde = new Date();
		Date Updated = new Date();
		tbItem.setCreated(createde);
		tbItem.setUpdated(Updated);
		tbItemMapper.insert(tbItem);
		// 添加记录到商品描述表
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(id);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(createde);
		tbItemDesc.setUpdated(Updated);
		tbItemDescMapper.insert(tbItemDesc);
		// 将id 添加到ActiveMQ 用于append同步索引库
		jmsTemplate.send(topicDestination, new MessageCreator() {
			// 为保证consumer在接收时，insert事务已经完成提交
			@Override
			public Message createMessage(Session session) throws JMSException {

				return session.createTextMessage(id + "");
			}

		});

		return E3Result.ok();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.e3mall.service.ItemService#getItemDescById(java.lang.Long)
	 */
	@Override
	public TbItemDesc getItemDescById(Long id) {

		try {
			// 查找缓存 缓存操作是独立的 异常不应该不影响正常的操作
			String itemStr = jedisClient.get(ITEM_INFO + ":" + id + ":BASE");
			if (StringUtils.isNotEmpty(itemStr)) {
				return JsonUtils.jsonToPojo(itemStr, TbItemDesc.class);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(id);
		if (itemDesc != null) {
			try {
				// 将查找的数据加入缓存
				jedisClient.set(ITEM_INFO + ":" + id + ":DESC", JsonUtils.objectToJson(itemDesc));
				// 设置过期时间
				jedisClient.expire(ITEM_INFO + ":" + id + ":DESC", ITEM_EXPIRE_TIME);
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		return itemDesc;
	}

}
