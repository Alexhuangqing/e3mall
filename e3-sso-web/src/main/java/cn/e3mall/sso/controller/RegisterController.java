/**
 * 
 */
package cn.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.remoting.exchange.Request;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.RegisterService;

/**
 * @author ALEX
 * 2018年5月7日
 * <p>desc:注册新用户表现层</p>
 */
@Controller
public class RegisterController {
	
	@Autowired
	private RegisterService registerService;
	
	
	
	@RequestMapping("/page/register")
	public String toRegisterPage() {
		return "register";
	}
	
	
	
	/**
	 * 验证用户名（1） ，电话号码（2），邮箱（3）的唯一性
	 * @param param 类型的具体数据
	 * @param type 类型
	 * @return
	 */
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public E3Result cheakData(@PathVariable String param,@PathVariable Integer type) {
		 
		return registerService.checkData(param, type);
		
	}
	
	
	/**
	 * 用户注册
	 * @return
	 */
	@RequestMapping(value= "/user/register" ,method=RequestMethod.POST)
	@ResponseBody
	public E3Result register(TbUser user) {
		return registerService.register(user);
	}
	
	
	
	
	

}
