/**
 * 
 */
package cn.e3mall.content.testJedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;



/**
 * @author Alex
 * 2018年4月20日
 * <p>desc:测试redis客户端</p>
 */
/**
 * @author Alex
 * 2018年4月20日
 * <p>desc:null</p>
 */
public class TestJedis {
	
	/**
	 * 
	 *<p>desc:单个jedis  每次存取   创建 销毁 性能低</p>
	 */
	@Test
	public void testJedis() throws Exception{
		//创建资源
		Jedis jedis = new Jedis("192.168.1.151",6379);
		jedis.set("testJedis", "javaTest");
		String VTestJedis = jedis.get("testJedis");
		System.out.println(VTestJedis);
		//销毁资源
		jedis.close();
	}
	
	
	/**
	 * 维护一个单例的连接池
	 *<p>desc:性能更好</p>
	 */
	@Test
	public void testJedisPool() throws Exception{
		//创建连接池
		JedisPool jedisPool = new  JedisPool("192.168.1.151",6379);
		//获取资源
		Jedis jedis = jedisPool.getResource();
		String VTestJedis = jedis.get("testJedis");
		System.out.println(VTestJedis);
		//返还资源
		jedis.close();
		//关闭连接池
		jedisPool.close();
		
	}
	
	
	/**
	 * 系统维护一个单例的集群客户端
	 *<p>desc:集群解决方案</p>
	 */
	@Test
	public void testJedisCluster() throws Exception{
		
		JedisPoolConfig config = new JedisPoolConfig();
		       config.setMaxTotal(60000);//设置最大连接数  
		       config.setMaxIdle(1000); //设置最大空闲数 
		       config.setMaxWaitMillis(3000);//设置超时时间  
		       config.setTestOnBorrow(true);
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.1.151",7001));
		nodes.add(new HostAndPort("192.168.1.151",7002));
		nodes.add(new HostAndPort("192.168.1.151",7003));
		nodes.add(new HostAndPort("192.168.1.151",7004));
		nodes.add(new HostAndPort("192.168.1.151",7005));
		nodes.add(new HostAndPort("192.168.1.151",7006));
		JedisCluster jedisCluster = new JedisCluster(nodes,config);
		
		
		jedisCluster.set("testNode", "Cluster Hello");
		String vJedisCluster = jedisCluster.get("testNode");
		System.out.println(vJedisCluster);
		
		
		jedisCluster.close();
		
		
	}

}
