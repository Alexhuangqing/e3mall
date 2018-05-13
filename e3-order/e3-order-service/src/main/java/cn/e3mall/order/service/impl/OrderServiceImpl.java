package cn.e3mall.order.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.mapper.TbOrderItemMapper;
import cn.e3mall.mapper.TbOrderMapper;
import cn.e3mall.mapper.TbOrderShippingMapper;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbOrderItem;
import cn.e3mall.pojo.TbOrderShipping;

/**
 * 
 * @author ALEX
 * 2018年5月13日
 * <p>desc:订单服务层实现</p>
 */
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ORDER_ID_GEN_KEY}")
	private String ORDER_ID_GEN_KEY;
	
	@Value("${ORDER_ID_GEN_START}")
	private String ORDER_ID_GEN_START;
	
	@Value("${ORDER_ID_DETAIL_GEN_KEY}")
	private String ORDER_ID_DETAIL_GEN_KEY;
	
	@Value("${ORDER_ID_DETAIL_GEN_START}")
	private String ORDER_ID_DETAIL_GEN_START;
	
	
	
	
	@Autowired
	private TbOrderMapper tbOrderMapper;//订单表
	
	@Autowired
	private TbOrderItemMapper  tbOrderItemMapper;//订单明细表
	
	@Autowired
	private TbOrderShippingMapper tbOrderShippingMapper;//订单物流表

	@Override
	public E3Result createOrder(OrderInfo orderInfo) {
		//在redis中，用于生成订单的主键
		//补全物流信息（订单的订单号，订单的订单状态，订单的创建与更新时间）
		if(!jedisClient.exists(ORDER_ID_GEN_KEY)) {
			jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_GEN_START);
		}
		String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
		//redis中自增生成的订单号
		orderInfo.setOrderId(orderId);
		// 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭 
		orderInfo.setStatus(1);
		orderInfo.setUpdateTime(new Date());
		orderInfo.setCreateTime(new Date());
		//向订单表插入数据
		tbOrderMapper.insert(orderInfo);
		//向订单明细表插入数据
		for(TbOrderItem orderItem:orderInfo.getOrderItems()) {
			if(!jedisClient.exists(ORDER_ID_DETAIL_GEN_KEY)) {
				jedisClient.set(ORDER_ID_DETAIL_GEN_KEY, ORDER_ID_DETAIL_GEN_START);
			}
			String orId = jedisClient.incr(ORDER_ID_DETAIL_GEN_KEY).toString();
			orderItem.setId(orId);
			orderItem.setOrderId(orderId);
			tbOrderItemMapper.insert(orderItem);
		}
		
		//向订单物流表插入数据
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		orderShipping.setOrderId(orderId);
		tbOrderShippingMapper.insert(orderShipping);
		//返回生成的订单信息
		return E3Result.ok(orderId);
	}

}
