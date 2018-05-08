/**
 * 
 */
package cn.e3mall.sso.service.impl;


import java.util.Date; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.common.utils.StringUtils;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.RegisterService;

/**
 * @author ALEX 2018年5月7日
 *         <p>
 * 		desc:注册服务实现
 *         </p>
 */
@Service
public class RegisterServiceImpl implements RegisterService {
	@Autowired
	private TbUserMapper tbUserMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.e3mall.sso.service.RegisterService#checkData(java.lang.String,
	 * java.lang.Integer)
	 */
	@Override
	public E3Result checkData(String param, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 1:校验用户名的唯一性 2:校验电话号码的唯一性 3：校验邮箱的唯一性
		if (1 == type) {
			criteria.andUsernameEqualTo(param);
		} else if (2 == type) {
			criteria.andPhoneEqualTo(param);
		} else if (3 == type) {
			criteria.andEmailEqualTo(param);
		} else {
			//data == null 验证失败
			return E3Result.build(400, "类型数据有误");
		}

		List<TbUser> list = tbUserMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			//data == false 验证失败
			return E3Result.ok(false);
		}
		//data == true 验证成功
		return E3Result.ok(true);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.e3mall.sso.service.RegisterService#register(cn.e3mall.pojo.TbUser)
	 */
	@Override
	public E3Result register(TbUser user) {
		//1.向表中插入数据前 做关键数据非空性验证  前台表单提交并没有邮箱的字段，所以无需做验证
		if(StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())||
				StringUtils.isBlank(user.getPhone())) {
			return E3Result.build(400, "注册失败，数据信息不完整");
		}
		//2.再次验证数据的合法性 ( 1:校验用户名的唯一性 2:校验电话号码的唯一性 3：校验邮箱的唯一性)
		E3Result e3Result =  checkData(user.getUsername(),1);
		if(!(boolean) e3Result.getData()) {
			return E3Result.build(400, "用户名被占用");
		}
		 e3Result =  checkData(user.getPhone(),2);
		 if(!(boolean) e3Result.getData()) {
				return E3Result.build(400, "电话号码被占用");
			}
		 //3.摘要工具进行MD5加密 补全字段
		 String md5Str = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		 user.setPassword(md5Str);
		 user.setCreated(new Date());
		 user.setUpdated(new Date());
		 
		//4.向表中添加记录
		 tbUserMapper.insert(user);
		return E3Result.ok();
	}

}
