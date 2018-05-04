/**
 * 
 */
package cn.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

/**
 * @author Alex
 * 2018年4月15日
 * <p>desc:无</p>
 */
@Controller
public class IndexController {
	/**
	 * 从配置文件中获取分类id
	 */
	@Value("${CONTENT_LUNBO_ID}")
	private Long CONTENT_LUNBO_ID;
	
	@Autowired
	private ContentService contentService;
	
	/**
	 * @return 跳转到首页
	 *<p>desc:视图解析</p>
	 */
	@RequestMapping("index")
	public String toIndex(Model model){
	
		List<TbContent> ad1List = contentService.getContentListByCid(CONTENT_LUNBO_ID);
		model.addAttribute("ad1List", ad1List);
		return "index";
	}

}
