/**
 * 
 */
package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;

/**
 * @author ALEX
 * 2018年5月8日
 * <p>desc:登录服务接口</p>
 */
public interface LoginService {
	
	
	/**
	 * 因为需要通过表现层设置cookie，所以这里返回token信息（封装到E3Result） 给表现层
	 * @param username
	 * @param password
	 * @return
	 */
	public E3Result login(String username,String password);

}
