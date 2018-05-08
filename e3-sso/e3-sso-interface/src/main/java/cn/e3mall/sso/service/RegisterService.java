/**
 * 
 */
package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;

/**
 * @author ALEX
 * 2018年5月7日
 * <p>desc:用户注册接口</p>
 */
public interface RegisterService {
	
	/**
	 * 注册时，校验账号
	 * @return 
	 */
	public E3Result checkData(String  param ,Integer type);
	
	/**
	 * 注册新用户
	 * @return
	 */
	public E3Result register(TbUser user);
	

}
