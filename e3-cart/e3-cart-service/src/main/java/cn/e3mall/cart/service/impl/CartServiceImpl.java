/**
 * 
 */
package cn.e3mall.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;

/**
 * @author ALEX
 * 2018年5月10日
 * <p>desc:用户登录后，购物车实现</p>
 */
@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Value("${CART_PREFIX}")
	private String CART_PREFIX;

	/* (non-Javadoc)
	 * @see cn.e3mall.cart.service.CartService#addCartItem(java.lang.Long, java.lang.Long, int)
	 */
	@Override
	public E3Result addCartItem(Long userId, Long itemId, int num) {
		
		//1.redis中判断是否有商品
		Boolean hexists = jedisClient.hexists(CART_PREFIX+":"+userId, itemId+"");
		//2.如果有的话就更新数量
		if(hexists) {
			//拿到购物车中信息并更新
			String itemStr = jedisClient.hget(CART_PREFIX+":"+userId, itemId+"");
			TbItem tbItem = JsonUtils.jsonToPojo(itemStr, TbItem.class);
			tbItem.setNum(tbItem.getNum()+num);
			//将更新的商品信息放入购物车（redis）
			jedisClient.hset(CART_PREFIX+":"+userId, itemId+"", JsonUtils.objectToJson(tbItem));
			
			return E3Result.ok();
		}
		//3.如果没有，就就添加商品
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		//将商品数量调整成购物车存储的商品数量
		tbItem.setNum(num);
		//调整购车中图片的展示
		String images = tbItem.getImage();
		if(StringUtils.isNotEmpty(images)) {
			tbItem.setImage(images.split(",")[0]);
		}
		jedisClient.hset(CART_PREFIX+":"+userId, itemId+"", JsonUtils.objectToJson(tbItem));
		return E3Result.ok();
	}

	/* (non-Javadoc)
	 * @see cn.e3mall.cart.service.CartService#updateNum(java.lang.Long, java.lang.Long, int)
	 */
	@Override
	public E3Result updateNum(Long userId, Long itemId, int num) {
		
		//更新商品的数量，先从购物车中拿到商品
		String itemStr = jedisClient.hget(CART_PREFIX+":"+userId, itemId+"");
		
		TbItem tbItem = JsonUtils.jsonToPojo(itemStr, TbItem.class);
		//更新商品数量
		tbItem.setNum(num);
		jedisClient.hset(CART_PREFIX+":"+userId, itemId+"",JsonUtils.objectToJson(tbItem));
		
		
		return E3Result.ok();
	}

	/* (non-Javadoc)
	 * @see cn.e3mall.cart.service.CartService#merge(java.lang.Long, java.util.List)
	 */
	@Override
	public E3Result merge(Long userId, List<TbItem> cartList) {
		
		for(TbItem tbItem:cartList) {
			//将cookie中的商品信息添加到redis中
			addCartItem(userId,tbItem.getId(),tbItem.getNum());
		}
		
		return E3Result.ok();
	}

	/* (non-Javadoc)
	 * @see cn.e3mall.cart.service.CartService#delItem(java.lang.Long, java.lang.Long)
	 */
	@Override
	public E3Result delItem(Long userId, Long itemId) {
		
		jedisClient.hdel(CART_PREFIX+":"+userId, itemId +"");
		return E3Result.ok();
	}

	/* (non-Javadoc)
	 * @see cn.e3mall.cart.service.CartService#getCartList(java.lang.Long)
	 */
	@Override
	public List<TbItem> getCartList(Long userId) {
		//获取hash的map中的value集合
		List<String> hvals = jedisClient.hvals(CART_PREFIX+":"+userId);
		//生成购物车商品信息
		List<TbItem> cartList = new ArrayList<>();
		for(String itemStr:hvals) {
			cartList.add(JsonUtils.jsonToPojo(itemStr, TbItem.class));
			
		}
		
		return cartList;
	}

}
