/**
 * 
 */
package cn.e3mall.activeMqSpring;

import java.io.IOException;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * @author Alex 2018年5月1日
 *         <p>
 *         desc:spring 整合 ActiveMQ 生产者
 *         </p>
 */
public class TestSpringMq {

	@Test
	public void testSpringConsumer() throws IOException {
		// 初始化spring容器
		ApplicationContext cxt = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activeMq.xml");

		//等待 接收
		System.in.read();

		

	}

}
