/**
 * 
 */
package cn.e3mall.content.service.impl;

import java.util.Date; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;

/**
 * @author Alex
 * 2018年4月17日
 * <p>desc:广告内容服务接口实现</p>
 */
@Service
public class ContentServiceImpl implements ContentService{
	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value(value="${CONTENT_LIST}")
	private String CONTENT_LIST;

	/* (non-Javadoc)
	 * @see cn.e3mall.content.service.ContentService#addContent(cn.e3mall.pojo.TbContent)
	 */
	@Override
	public E3Result addContent(TbContent tbContent) {
		Date date = new Date();
		tbContent.setCreated(date);
		tbContent.setUpdated(date);
		tbContentMapper.insert(tbContent);
		try {
			//key-value==> 业务名-（分类id,list详情）
			jedisClient.hdel(CONTENT_LIST,tbContent.getCategoryId()+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return E3Result.ok();
	}

	/* (non-Javadoc)
	 * @see cn.e3mall.content.service.ContentService#getContentListByCid(java.lang.Long)
	 */
	@Override
	public List<TbContent> getContentListByCid(Long cid) {
		//优先考虑在缓存中查找数据
		try {
			String json = jedisClient.hget(CONTENT_LIST, cid+"");
			if(StringUtils.isNotEmpty(json)){
				return JsonUtils.jsonToList(json, TbContent.class);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 *缓存操作报错 不能影响 从数据库中查询数据 ，即使缓存 出错 正常从数据库查询也能操作
		 *因此 将缓存操作的异常捕捉进行处理 
		 */
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		//带有content列  的内容
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		
		//如果是从数据库中取出的数据，则将数据库中数据存入缓存中
		try {
			if(list.size() > 0){
				jedisClient.hset(CONTENT_LIST, cid+"", JsonUtils.objectToJson(list));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
