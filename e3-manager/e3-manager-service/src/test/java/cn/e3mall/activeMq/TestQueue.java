/**
 * 
 */
package cn.e3mall.activeMq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

/**
 * @author Alex 2018年4月30日
 *         <p>
 *         desc:null
 *         </p>
 */
//public class TestQueue {
//	@Test
//	public void testQueueProducer() throws Exception {
//		// 创建一个连接工厂（connectionFactory）
//		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.151:61616");
//		// connectionFactory获得一个connection
//		Connection connection = connectionFactory.createConnection();
//		// 开启一个connection
//		connection.start();
//		// connection获得session :上下文对象
//		// 第一个参数是否开启事务：如果是true 则第二个参数 课自动忽略
//		// 第二个参数 是设置消息应答模式：消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
//
//		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//		// 利用上下文session创建一个destination (queue 或者是 topic)
//		Destination queue = session.createQueue("test-queue");
//		// 利用上下文session创建一个参与该queue的生产者
//		MessageProducer producer = session.createProducer(queue);
//		// 利用上下文session创建创建一个textMeesage
//		TextMessage message = session.createTextMessage("hello ActiveMQ");
//		// 生产者session发送该则消息
//		producer.send(message);
//
//		// 关闭资源 producer session connection
//		producer.close();
//		session.close();
//		connection.close();
//	}
//
//	@Test
//	public void testQueueConsumer() throws Exception {
//		// 开启connection三步走
//		ConnectionFactory caonnectionFactory = new ActiveMQConnectionFactory("tcp://192.168.1.151:61616");
//
//		Connection connection = caonnectionFactory.createConnection();
//
//		connection.start();
//		// 通过connection创建上下文session
//		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//
//		// 上下文session大显神威
//		Queue queue = session.createQueue("test-queue");
//		MessageConsumer consumer = session.createConsumer(queue);
//
//		// consumer端 实现监听器功能 接收来自消息队列的消息
//
//		consumer.setMessageListener(new MessageListener() {
//
//			/*
//			 * 当消息到来的时候 怎么做（相当于添加事件） 事件方法：调用该方法
//			 */
//			@Override
//			public void onMessage(Message message) {
//				TextMessage tmss = (TextMessage) message;
//
//				try {
//					String messageStr = tmss.getText();
//					System.out.println(messageStr);
//
//				} catch (JMSException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//		});
//		// 是程序处于 ‘读’ 阻塞状态 等待键盘输入
//		System.in.read();
//
//		// 关闭资源
//		consumer.close();
//		session.close();
//		connection.close();
//
//	}
//
//}
