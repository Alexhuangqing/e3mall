/**
 * 
 */
package cn.e3mall.content.testJedis;

import org.junit.Test; 
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.common.jedis.JedisClient;

/**
 * @author Alex 2018年4月23日
 *         <p>
 * 		desc:将jedis客户端整合到spring中
 *         </p>
 */
public class TestJedisClient {

	@Test
	public void testJedisClient() throws Exception {
		ApplicationContext cxt =// 
				new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		/**
		 * 策略模式  一个接口  多个实现类
		 */
		JedisClient jedisClient = cxt.getBean(JedisClient.class);
		jedisClient.set("classpath", "resource");
		System.out.println(jedisClient.get("classpath"));
	}

}
