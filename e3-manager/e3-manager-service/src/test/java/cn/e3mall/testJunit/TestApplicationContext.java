/**
 * 
 */
package cn.e3mall.testJunit;

import java.io.IOException; 

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Alex
 * 2018年4月17日
 * <p>desc:null</p>
 */
public class TestApplicationContext {
	
	/**
	 * @throws IOException
	 *<p>desc:后台服务模块 就是起到初始化bean容器的作用</p>
	 */
	@Test
	public void test1() throws IOException{
		ApplicationContext cxt = new ClassPathXmlApplicationContext("classpath:spring/applicationContext*.xml");
		System.out.println("商品服务已启动。。。");
		System.in.read();
		System.out.println("商品服务已停止。。。");
		
	}

}
