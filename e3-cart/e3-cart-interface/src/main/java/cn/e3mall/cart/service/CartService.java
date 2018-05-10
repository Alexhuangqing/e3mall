/**
 * 
 */
package cn.e3mall.cart.service;

import java.util.List;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;

/**
 * @author ALEX
 * 2018年5月10日
 * <p>desc:redis中存放购物车接口</p>
 */
public interface CartService {
	
	/**
	 * @param userId  登录用户的id
	 * @param itemId  要添加商品的id
	 * @param num     要添加商品的数量
	 * @return
	 */
	public E3Result addCartItem(Long userId, Long itemId, int num);
	
	/**
	 * @param userId  登录用户的id
	 * @param itemId  更新商品的id
	 * @param num	     要更新商品的数量
	 * @return
	 */
	public E3Result updateNum(Long userId, Long itemId, int num);
	
	
	/**
	 * @param userId    登录用户的id
	 * @param cartList  客户端cookie中存放的购物车信息
	 * @return
	 */
	public E3Result merge(Long userId,List<TbItem> cartList);

	
	
	/**
	 * 删除购车中的商品
	 * @return
	 */
	public E3Result delItem(Long userId, Long itemId);
	
	
	
	/**
	 * 从服务端返回购物车消息
	 * @param userId
	 * @return
	 */
	public List<TbItem> getCartList(Long userId);
}
