/**
 * 
 */
package cn.e3mall.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;

/**
 * @author Alex
 * 2018年4月29日
 * <p>desc:null</p>
 */
@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	@Value("${SEARCH_PAGE_SIZE}")
	private Integer SEARCH_PAGE_SIZE;
	/**
	 * 
	 * @param keyword tomcat默认的接收字符串对象的编码是iso-8859-1
	 * @param page 搜索当前页
	 * @param model 用于页面的回显
	 * @return
	 * @throws Exception 表现层将异常抛给springMvc框架 可定义一个全局异常处理器 处理异常
	 *<p>desc:solr搜索入口</p>
	 */
	@RequestMapping("/search")
	public String searchQuery(String keyword,
			 @RequestParam(defaultValue="1") Integer page,Model model) throws Exception{
		
		//tomcat插件环境 默认是"iso-8859-1"编码 将接收的String类型转码成"utf-8"
		 keyword = new String(keyword.getBytes("iso-8859-1"),"utf-8");
		//调用服务接口
		SearchResult result = searchService.search(keyword, page, SEARCH_PAGE_SIZE);
		//向request域中添加key-value 用于页面的回显
		model.addAttribute("query",keyword);
		model.addAttribute("toatlPages",result.getTotalPages());
		model.addAttribute("page",page);
		model.addAttribute("recourdCount",result.getRecourdCount());
		model.addAttribute("itemList",result.getItemList());
		
		//返回逻辑视图
		return "search";
	}

}
