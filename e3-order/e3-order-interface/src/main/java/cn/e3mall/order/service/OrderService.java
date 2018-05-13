package cn.e3mall.order.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.order.pojo.OrderInfo;

/**
 * 
 * @author ALEX
 * 2018年5月13日
 * <p>desc:订单服务层工程接口</p>
 */
public interface OrderService {
	
	/**
	 * 生成订单
	 * @param orderInfo
	 * @return
	 */
	public E3Result createOrder(OrderInfo orderInfo);
}
