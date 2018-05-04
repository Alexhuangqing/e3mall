/**
 * 
 */
package cn.e3mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author Alex
 * 2018年4月14日
 * <p>desc:页面跳转</p>
 */
@Controller
public class PageController {
	
	/**
	 * 默认欢迎页 
	 * @return String
	 *<p>desc:视图解析器解析（无ResponseBody注解）</p>
	 */
	@RequestMapping("/")
	public 	String wellcomePage( ){
		return "index";
	}
	
	
	/**
	 * @param view rest风格
	 * @return String
	 *<p>desc:视图解析器解析（无ResponseBody注解）</p>
	 */
	@RequestMapping("/{view}")
	public String getView(@PathVariable String view){
		return view;
	}
}
