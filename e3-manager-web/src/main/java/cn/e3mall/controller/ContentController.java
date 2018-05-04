/**
 * 
 */
package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;

/**
 * @author Alex
 * 2018年4月17日
 * <p>desc:内容服务模块</p>
 */
@Controller
public class ContentController {
	
	@Autowired
	ContentService contentService;
	
	/**
	 * @param tbContent 封装前端的表单
	 * @return 状态pojo
	 *<p>desc:无</p>
	 */
	@RequestMapping(value="/content/save" ,method = RequestMethod.POST)
	@ResponseBody
	public E3Result addContent(TbContent tbContent){
		E3Result result = contentService.addContent(tbContent);
		
		return result;
	}

}
