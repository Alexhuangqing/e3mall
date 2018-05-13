/**
 * 
 */
package cn.e3mall.order.intercepter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

/**
 * @author ALEX
 * 2018年5月12日
 * <p>desc:在订单确认前，用户登录一定要验证通过</p>
 */
public class LoginIntercepter implements HandlerInterceptor{
	
	@Value("${SSO_URL}")
	private String SSO_URL;

	@Autowired
	private TokenService tokenService;
	@Autowired
	private CartService cartService;
	/**
	 * 执行handler之前
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//生成订单用户必须为登录状态
		//从客户端cookie中取出token信息
		String token = CookieUtils.getCookieValue(request, "token", true);
		boolean isLoginStatus = false;
		TbUser user = null;
		//从sso系统中取出用户信息
		if(StringUtils.isNotEmpty(token)) {
			E3Result e3Result = tokenService.getUserByToken(token);
			//如果sso系统中用户登录且没有过期，将用户信息放入request域中，并放行
			if(e3Result.getStatus()==200) {
				user = (TbUser)e3Result.getData();
				request.setAttribute("user", user);
				isLoginStatus = true;
				
			}
			
		}
		//如果用户没有登录，则跳转到sso系统的登录页面页面，并且携带本次的该请求的url（即在登录页面 login成功后  的url跳转信息）
		if(!isLoginStatus) {
			response.sendRedirect(SSO_URL + "/page/login?redirect=" + request.getRequestURL());
			//拦截
			return false;
		}
		//用户登录后，如果客户端购物车没有合并，则应该在这里合并购物车信息到服务端
		
		String cartList = CookieUtils.getCookieValue(request, "cart", true);
		if(StringUtils.isNotEmpty(cartList)) {
			cartService.merge(user.getId(), JsonUtils.jsonToList(cartList, TbItem.class));
			//购物车合并后，清空客户端购物车信息
			CookieUtils.deleteCookie(request, response, "cart");
		}
		return true;
	}
	
	
	/**
	 * handler执行完成之后，视图在被视图解析器解析之前
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 被视图解析器解析之后，这里可以进行异常处理
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}



}
