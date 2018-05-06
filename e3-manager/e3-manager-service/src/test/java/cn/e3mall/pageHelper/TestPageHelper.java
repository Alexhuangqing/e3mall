/**
 * 
 */
package cn.e3mall.pageHelper;


//
//import java.util.List;  
//
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//
//import cn.e3mall.mapper.TbItemMapper;
//import cn.e3mall.pojo.TbItem;
//import cn.e3mall.pojo.TbItemExample;

/**
 * @author Alex
 * 2018年3月6日 
 * @className 
 * @Description
 */
/*public class TestPageHelper {
	@Test
	public void testPageHelper() {
		//手动扫描配置文件，将对象注册到容器,获得maper 代理对象   （spring容器只能初始化一次）
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/*");
		TbItemMapper itemMapper = ctx.getBean(TbItemMapper.class);
		//设置分页参数（在执行sql的视乎会被拦截）
		PageHelper.startPage(1, 10);
		//开始分页查询
		TbItemExample example = new TbItemExample();
		List<TbItem> result = itemMapper.selectByExample(example);
		//将结果包装，得到分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(result);
		System.out.println(pageInfo.getPageNum());
		System.out.println(pageInfo.getTotal());
		System.out.println(pageInfo.getPages());
	}

}*/
