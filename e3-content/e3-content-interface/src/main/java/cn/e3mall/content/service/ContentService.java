/**
 * 
 */
package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContent;

/**
 * @author Alex
 * 2018年4月17日
 * <p>desc:广告内容服务接口</p>
 */
public interface ContentService {
	
	/**
	 * @param tbContent 封装内容的pojo
	 * @return 状态
	 *<p>desc:缓存同步，在进行数据库数据更新时，要删除缓存，下次查询时，缓存就会更新</p>
	 */
	public E3Result addContent(TbContent tbContent);
	
	/**
	 * @param cid 分类id
	 * @return 展示的内容
	 *<p>desc:在查询的业务逻辑中增加  缓存同步操作</p>
	 */
	public List<TbContent> getContentListByCid(Long cid);

}
