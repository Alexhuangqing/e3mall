/**
 * 
 */
package cn.e3mall.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.service.ItemService;

/**
 * @author ALEX 2018年5月8日
 *         <p>
 *         desc:购入车操作入口
 *         </p>
 */
@Controller
public class CartController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CartService cartService;

	/**
	 * 默认购物车中商品存放5天（cookie默认时在每次关闭浏览器时，被清空）
	 */
	@Value("${EXPIRE_CART_TIME}")
	private Integer EXPIRE_CART_TIME;

	/**
	 * 向购物车中添加一定数量的某条商品
	 * 
	 * @param request
	 * @param response
	 * @param itemId
	 * @param num
	 * @return
	 */
	@RequestMapping("/cart/add/{itemId}")
	public String addCartItem(HttpServletRequest request, HttpServletResponse response, //
			@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num) {
		
		//校验是否为登录用户，如果为登录用户，则使用redis存放购物车信息
		TbUser user = (TbUser)request.getAttribute("user");
		
		if(user != null) {
			cartService.addCartItem(user.getId(),itemId,num);
			return "cartSuccess";
		}
		
		
		
		
		// 1.从cookie中取出购物车
		List<TbItem> cartList = getCartListFromCookie(request);

		boolean isIncrease = false;

		for (TbItem tbItem : cartList) {
			// 2.遍历购物车，如果记录有 数量加num
			if (tbItem.getId() == itemId.longValue()) {// 此时的id是两个包装的对象，不能直接相等
				tbItem.setNum(tbItem.getNum() + num);
				isIncrease = true;
				break;
			}

		}

		// 3.如果没有找到，则自己向购物车中添加一条商品
		if (!isIncrease) {
			TbItem item = itemService.getItemById(itemId);
			// 整理应该显示的记录 符合显示规则
			item.setNum(num);//调整购物车中商品数量
			if (StringUtils.isNotEmpty(item.getImage())) {
				item.setImage(item.getImage().split(",")[0]);//调整购物车中展示的图片
			}
			cartList.add(item);
		}

		// 4.更新cookie中存放的list串
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), EXPIRE_CART_TIME, true);

		return "cartSuccess";

	}

	/**
	 * 去购物车结算时，获取购物车清单
	 * 只有获取了清单页面后，才能在页面上修改或删除购物车中已有的商品
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String getCartList(HttpServletRequest request,HttpServletResponse response,Model model) {
		//如用户为登录用户，则需要合并将cookie中购物车与redis中购物车的信息，同步redis,删除cookie中cart;
		//
		TbUser user = (TbUser) request.getAttribute("user");
		List<TbItem> cartList = getCartListFromCookie(request);
		if(user!=null) {
			//为登录用户  从客户端拿到购物车信息 
			//将客户端中购物车信息合并到服务端，用户登录后，购物车信息就会跨客户端
			cartService.merge(user.getId(), cartList);
			//清空客户端中购物车信息
			CookieUtils.deleteCookie(request, response, "cart");
			//从服务端返回购物车信息
			cartList = cartService.getCartList(user.getId());
			
		}
		model.addAttribute("cartList", cartList);
		return "cart";
	}

	/**
	 * 购物车中商品数量变化时，更新本地cookie中cart
	 * 
	 * @return
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result updateNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request, HttpServletResponse response) {
		
		//如用户为登陆用户，则修改redis中购物车的数量
		TbUser user = (TbUser) request.getAttribute("user");
		if(user!=null) {
			cartService.updateNum(user.getId(), itemId, num);
			return E3Result.ok();
		}
		
		// 1.从cookie中获取购物车
		List<TbItem> cartList = getCartListFromCookie(request);
		// 2.遍历购物车，给指定id的商品更新num
		for (TbItem tbItem : cartList) {
			if (tbItem.getId().longValue() == itemId) {
				tbItem.setNum(num);
				break;
			}
		}
		// 3.更新cookie中购物车
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), EXPIRE_CART_TIME, true);
		// 4.响应ajax请求
		return E3Result.ok();
	}

	/**
	 * 购物车中删除商品，重定回会购物车列表页面（刷新效果）
	 * 
	 * @param itemId
	 * @param request
	 * @return
	 */
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		
		//如用户为登陆用户，则删除redis中的cart中的商品
		TbUser user = (TbUser) request.getAttribute("user");
		if(user!=null) {
			cartService.delItem(user.getId(), itemId);
			//页面的重定向请求
			return "redirect:/cart/cart.html";
		}
		
		
		// 1.从cookie中获取购物车
		List<TbItem> cartList = getCartListFromCookie(request);

		// 2.遍历购物车，删除特定id的商品
		for (TbItem tbItem : cartList) {
			if (tbItem.getId().longValue() == itemId) {
				cartList.remove(tbItem);
				break;
			}
		}

		// 3.更新cookie中的购物车
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), EXPIRE_CART_TIME, true);

		// 4.重定向回到购物车清单页面,做页面重定向请求

		return "redirect:/cart/cart.html";
	}

	/**
	 * 从cookie中取出购物车
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private List<TbItem> getCartListFromCookie(HttpServletRequest request) {
		String cartListStr = CookieUtils.getCookieValue(request, "cart", true);
		if (StringUtils.isNotEmpty(cartListStr)) {
			return JsonUtils.jsonToList(cartListStr, TbItem.class);
		}
		return new ArrayList<TbItem>();
	}

}
