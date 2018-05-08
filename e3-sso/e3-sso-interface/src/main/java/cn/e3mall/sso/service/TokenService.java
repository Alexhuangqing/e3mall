/**
 * 
 */
package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;

/**
 * @author ALEX
 * 2018年5月8日
 * <p>desc:用token获取用户信息</p>
 */
public interface TokenService {
	
	
	/**
	 * 用token读取用户信息
	 * @param token
	 * @return
	 */
	public E3Result getUserByToken(String token);

}
