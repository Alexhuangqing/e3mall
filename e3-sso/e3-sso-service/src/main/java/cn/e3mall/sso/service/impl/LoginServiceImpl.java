/**
 * 
 */
package cn.e3mall.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.LoginService;

/**
 * @author ALEX
 * 2018年5月8日
 * <p>desc:用户登录实现</p>
 */
@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private TbUserMapper tbUserMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${EXPIRE_TIME}")
	private Integer EXPIRE_TIME;
	

	/* (non-Javadoc)
	 * @see cn.e3mall.sso.service.LoginService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public E3Result login(String username, String password) {
		
		//1.根据username查询数据库，并且验证密码
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		
		criteria.andUsernameEqualTo(username);
		
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if(list==null||list.size()==0) {
			return E3Result.build(400, "用户名或密码错误");
		}
		
		 TbUser user = list.get(0);
		
		if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
			return E3Result.build(400, "用户名或密码错误");
		}
		
		//2.利用redis模拟session，以token为k，用户信息为v，将用户信息（安全考虑，密码要置空）存入redis中，并设置过期时间
		
		String token = UUID.randomUUID().toString();
		user.setPassword(null);
		jedisClient.set("SESSION:"+token,JsonUtils.objectToJson(user));
		jedisClient.expire("SESSION:"+token, EXPIRE_TIME);
		
		//3.将token信息封装，返回
		return E3Result.ok(token);
	}

	

}
