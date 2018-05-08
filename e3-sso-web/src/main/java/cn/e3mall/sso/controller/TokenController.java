/**
 * 
 */
package cn.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.sso.service.TokenService;

/**
 * @author ALEX
 * 2018年5月8日
 * <p>desc:接受token传递的信息</p>
 */
@Controller
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	
	
	/**
	 * 版本1.处理跨域ajax请求，获取响应结果
	 * @param token
	 * @param callback 
	 * @return
	 */
/*	@RequestMapping(value="/user/token/{token}",//设置响应格式
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE"application/json;charset='utf-8'")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback) {
		E3Result e3Result = tokenService.getUserByToken(token);
		//是否为jsonp请求（ajax跨域请求的，获取返回数据的解决方案）
		if(StringUtils.isNotEmpty(callback)) {
			//返回一段js代码串
			return callback+"("+JsonUtils.objectToJson(e3Result)+")";
		}
		return e3Result;
	}*/
	
	
	
	
	
	
	/**
	 * 版本2.处理跨域ajax请求，获取响应结果
	 * @param token
	 * @param callback
	 * @return
	 */
	@RequestMapping(value="/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback) {
		E3Result e3Result = tokenService.getUserByToken(token);
		//是否为jsonp请求（ajax跨域请求的，获取返回数据的解决方案）
		if(StringUtils.isNotEmpty(callback)) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(e3Result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
			
		}
		return e3Result;
	}

}
