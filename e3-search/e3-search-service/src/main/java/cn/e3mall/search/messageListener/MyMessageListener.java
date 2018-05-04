/**
 * 
 */
package cn.e3mall.search.messageListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author Alex 2018年5月1日
 *         <p>
 * 		desc:自定义消息监听器
 *         </p>
 */
public class MyMessageListener implements MessageListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {

		TextMessage textMessage = (TextMessage) message;
		try {
			String text = textMessage.getText();
			System.out.println(text);
		} catch (JMSException e) {

			e.printStackTrace();
		}
	}

}
