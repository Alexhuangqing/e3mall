/**
 * 
 */
package cn.e3mall.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.sso.service.LoginService;

/**
 * @author ALEX 2018年5月8日
 *         <p>
 *         desc:用户登录入口
 *         </p>
 */
@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	
	
	@RequestMapping("/page/login")
	public String toRegisterPage() {
		return "login";
	}

	@RequestMapping("/user/login")
	@ResponseBody
	public E3Result login(String username,String password,
			HttpServletRequest request,HttpServletResponse response) {
		//1.进行登录，获取e3Result
		E3Result e3Result = loginService.login(username, password);
		//2.是否登录成功
		if(e3Result.getStatus()==200) {
			//登录成功后，取出token，response响应cookie给浏览器，浏览器将cookie保存到本地文件中
			String token = e3Result.getData().toString();
			CookieUtils.setCookie(request, response, TOKEN_KEY, token);
		
		}
		return e3Result;
	}
	

}
