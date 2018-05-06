/**
 * 
 */
package cn.e3mall.activeMqSpring;

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
//public class TestSpringMq {
//
//	@Test
//	public void testSpringProducer() {
//		// 初始化spring容器
//		ApplicationContext cxt = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activeMq.xml");
//
//		// 通过class从容器中拿到JmsTemplate对象
//		JmsTemplate jmsTemplate = cxt.getBean(JmsTemplate.class);
//
//		// 通过id从容器中拿到destination（spring容器中配置了destination的两种实现）
//		Destination queue = (Destination) cxt.getBean("queueDestination");
//
//		// 发送消息
//
//		jmsTemplate.send(queue, new MessageCreator() {
//
//			@Override
//			public Message createMessage(Session session) throws JMSException {
//				return 	session.createTextMessage("hello spring-ActiveMQ");
//			}
//
//		});
//
//	}
//
//}
