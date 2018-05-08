/**
 * 
 */
package cn.e3mall.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

/**
 * @author ALEX 2018年5月8日
 *         <p>
 * 		desc:token获取用户信息实现
 *         </p>
 */
@Service
public class TokenServiceImpl implements TokenService {
	@Autowired
	private JedisClient jedisClient;

	@Value("${EXPIRE_TIME}")
	private Integer EXPIRE_TIME;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.e3mall.sso.service.TokenService#getUserByToken(java.lang.String)
	 */
	@Override
	public E3Result getUserByToken(String token) {
		// 1.从redis中获取用户信息
		String userStr = jedisClient.get("SESSION:" + token);

		if (StringUtils.isBlank(userStr)) {
			return E3Result.build(400, "用户登录过期");
		}

		// 2.更新用户的过期时间
		jedisClient.expire("SESSION:" + token, EXPIRE_TIME);

		return E3Result.ok(JsonUtils.jsonToPojo(userStr, TbUser.class));
	}

}
