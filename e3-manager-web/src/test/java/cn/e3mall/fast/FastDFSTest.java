/**
 * 
 */
package cn.e3mall.fast;




import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;


/**
 * @author Alex
 * 2018年4月14日
 * <p>desc:测试</p>
 */
public class FastDFSTest{
	@Test
	public void  testFileUpload() throws Exception{
		
		// 1、加载配置文件，配置文件中的内容就是tracker服务的地址
		ClientGlobal.init("E:/www/e3mall_user/e3-manager-web/src/main/resources/conf/client.conf");
		// 2、创建一个TrackerClient对象。直接new一个。
		TrackerClient trackerClient = new TrackerClient();
		// 3、由TrackerClient对象得到TrackerServer。
		TrackerServer trackerServer = trackerClient.getConnection();
		// 4、声明一个storageServer引用。
		StorageServer storageServer = null;
		// 5、创建一个storageClient对象，传入storageServer空引用与向导TrackerServer。
		StorageClient storageClient = new StorageClient(trackerServer,storageServer);
		// 6、由storageClient上传文件调用upload_appender_file(..)。 并返回一个数组 数组拼起来即时地址
		String[] appender_file = storageClient.upload_file("C:/Users/Administrator/Desktop/2.png", "png", null);
		for(String str:appender_file ){
			System.out.println(str);
		}
	}

}
