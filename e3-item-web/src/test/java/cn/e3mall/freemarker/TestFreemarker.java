/**
 * 
 */
package cn.e3mall.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author Alex
 * 2018年5月2日
 * <p>desc:测试网页静态化</p>
 */
public class TestFreemarker {
	//模板目录  数据源  字符集 输出
	@Test
	public void genfile() throws Exception{
		//1.第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
		Configuration configuration = new Configuration(Configuration.getVersion());
		
		
		//2.设置一个模板文件的目录（dir）
		configuration.setDirectoryForTemplateLoading(new File("E:/mall1workspace/e3-item-web/src/main/webapp/ftl"));
		//3.设置字符编码集 (utf-8)
		configuration.setDefaultEncoding("utf-8");
		
		
		//4.加载一个模板文件hello.ftl(前面设置了模板文件目录)
		Template template = configuration.getTemplate("hello.ftl");
		//5.数据源 是一个pojo 或者 是一个map对象
		Map<String,Object> dataModel = new HashMap<>();
		dataModel.put("hello", "this is my first ftl");
		
		//6.带有目标文件的输出流
		Writer out = new FileWriter(new File("C:/Users/Administrator/Desktop/hello.html"));
		//7.利用template生成静态页面
		template.process(dataModel, out);
		//8.关闭输出
		out.close();
	}

}

