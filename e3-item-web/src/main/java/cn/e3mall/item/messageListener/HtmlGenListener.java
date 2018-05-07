/**
 * 
 */
package cn.e3mall.item.messageListener;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * @author ALEX
 * 2018年5月7日
 * <p>desc:监听商品添加信息，生成静态页面</p>
 */
public class HtmlGenListener implements MessageListener{
	
	@Autowired
	private FreeMarkerConfigurer freemarkerConfig;
	
	@Autowired
	private ItemService itemService;
	
	@Value("${GEN_HTML}")
	private String GEN_HTML;

	@Override
	public void onMessage(Message message) {
		try {
			//1.监听itemAddTopic频道  获取添加商品的id 
			TextMessage textMessage = (TextMessage)message;
			String  itemId = textMessage.getText();
			
		
			
			//2.查询数据库（redis/mysql），获得商品信息（等待1秒钟，确保数据库商品添加事务已提交）
			Thread.sleep(1000);
			TbItem tbItem = itemService.getItemById(new Long(itemId));
			
			
			TbItemDesc itemDesc = itemService.getItemDescById(new Long(itemId));
			//3.得到configration对象，加载生成html的模板(template)文件，
			Configuration configuration = freemarkerConfig.getConfiguration();
			 Template template = configuration.getTemplate("item.ftl");
			
			 //将信息添加到数据集中
			Map<String,Object> dataModel = new HashMap<>();
			dataModel.put("item", new Item(tbItem));
			dataModel.put("itemDesc", itemDesc);
			
			//4.制定生成模板文件的位置，开启输出流，生成html文件，关闭流
			Writer out =new FileWriter(GEN_HTML + itemId +".html");
			template.process(dataModel, out);
			out.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	}

}
