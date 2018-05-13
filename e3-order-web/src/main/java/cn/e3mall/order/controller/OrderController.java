/**
 * 
 */
package cn.e3mall.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;

/**
 * @author ALEX
 * 2018年5月12日
 * <p>desc:订单表现层工程</p>
 */
@Controller
public class OrderController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 跳转到订单展示页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/order-cart")
	public String getOrderCart(HttpServletRequest request,Model model) {
		//验证用户必须登陆,且拿到用户信息
		TbUser user = (TbUser)request.getAttribute("user");
		//可选购地址列表（包含收件人信息）
		//可选支付方式列表
		//购物车清单
		List<TbItem> cartList = cartService.getCartList(user.getId());
		model.addAttribute("cartList",cartList);
		return "order-cart";
	}

	
	/**
	 * 确认订单页面
	 * @param orderInfo 封装页面订单表，订单明细表，订单物流表相关信息
	 * @return
	 */
	@RequestMapping(value="/order/create",method = RequestMethod.POST)
	public String orderCreate(OrderInfo orderInfo,//
			HttpServletRequest request,HttpServletResponse response, Model model) {
		//拿到用户信息 
		TbUser user = (TbUser)request.getAttribute("user");
		//补全用户信息
		orderInfo.setUserId(user.getId());
		
		//生成订单，返回订单号
		E3Result e3Result = orderService.createOrder(orderInfo);
		
		if(e3Result.getStatus() == 200) {
			//清空服务端购物车
			cartService.clear(user.getId());
			model.addAttribute("orderId",e3Result.getData().toString() );
		}
		
		//将订单数据及相关金额返回给页面
		model.addAttribute("payment", orderInfo.getPayment());
		
		return "success";
	}
}
