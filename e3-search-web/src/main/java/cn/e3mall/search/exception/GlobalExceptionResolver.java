/**
 * 
 */
package cn.e3mall.search.exception;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Alex
 * 2018年5月1日
 * <p>desc:定义全局异常处理器类，用于捕获表现层抛给框架的异常</p>
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {
	
	private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);
	
	/**
	 * @param handler 抛异常的方法
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		//打印到控制台
		ex.printStackTrace();
		//写入日志（常见4种级别： debug warning info error）
		logger.info("系统异常。。。",ex);
		//发邮件，发短信 使用jmail 工具包
		//显示错误页面
		ModelAndView mv = new ModelAndView();
		mv.setViewName("error/exception");
		return mv;
	}

}
