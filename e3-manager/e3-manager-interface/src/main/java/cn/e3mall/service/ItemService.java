/**
 * 
 */
package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

/**
 * @author Alex 2018年4月14日
 *         <p>
 * 		desc:商品相关
 *         </p>
 */
public interface ItemService {

	/**
	 * 查询单个商品
	 * 
	 * @param id
	 * @return TbItem
	 *         <p>
	 * 		desc:查询单个商品
	 *         </p>
	 */
	public TbItem getItemById(Long id);

	/**
	 * 查询单个商品的大文本
	 * 
	 * @param id
	 * @return
	 *         <p>
	 * 		desc:查询单个商品的大文本
	 *         </p>
	 */
	public TbItemDesc getItemDescById(Long id);

	/**
	 * 表格分页信息展示
	 * 
	 * @param page
	 *            当前页码
	 * @param rows
	 *            每页数量
	 * @return 分页展示信息
	 *         <p>
	 * 		desc:分页显示所有商品
	 *         </p>
	 */
	public EasyUIDataGridResult getList(int page, int rows);

	/**
	 * 添加商品
	 * 
	 * @param tbItem
	 *            封装添加的商品信息
	 * @param desc
	 *            封装添加商品描述
	 * @return E3Result 状态结果
	 *         <p>
	 * 		desc:添加单个商品
	 *         </p>
	 */
	public E3Result addItem(TbItem tbItem, String desc);

}
