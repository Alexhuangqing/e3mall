/**
 * 
 */
package cn.e3mall.cart.interception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

/**
 * @author ALEX
 * 2018年5月9日
 * <p>desc:校验用户是否登录，如登录就将信息放入请求域中</p>
 */
public class LoginInterception implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;
	
	/* 进入请求的方法之前
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//return true：放行请求,  return false:拦截请求
		//1.从cookie中拿到token
		String token = CookieUtils.getCookieValue(request, "token");
		//2.如果有token，根据token从sso系统中拿到用户信息
		if(StringUtils.isBlank(token)) {
			return true;
		}
		E3Result e3Result = tokenService.getUserByToken(token);
		
		
		//3.如果用户信息没有过期,则该状态时登录状态，将用户信息放到request域中
		if(e3Result.getStatus()!= 200) {
			return true;
		}
		
		request.setAttribute("user", (TbUser)e3Result.getData());
		return true;
	}
	
	
	/* 执行完请求的方法，返回ModelAndView之前
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndview)
			throws Exception {
		
		
	}
	
	
	
	/* 返回ModelAndView之后，在这里可以进行异常处理
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		

	}



}
