/**
 * 
 */
package cn.e3mall.controller;

import java.util.HashMap; 
import java.util.Map;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;


/**
 * @author Alex
 * 2018年4月14日
 * <p>desc:上传图片给后台，同时返回图片的图片服务器http地址</p>
 */
@Controller
public class PictureController {
	/**
	 * 获取配置文件中配置的图片服务器url
	 */
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	/**
	 * @param uploadFile
	 * @return String  JsonUtils将pojo转成json字符串
	 *<p>desc:上传图片，回显</p>
	 */
	@RequestMapping(value = "pic/upload",produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
	@ResponseBody
	public String fileUpload(MultipartFile uploadFile) {
		Map<Object, Object> result = new HashMap<>();
		try {
			// 利用客户端工具FastDFSClient上传
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);

			String httpPath = IMAGE_SERVER_URL +path;
			result.put("error", 0);
			result.put("url", httpPath);
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {

			result.put("error", 1);
			result.put("message", "图片上传失败");
			return JsonUtils.objectToJson(result);
		}

	}

}
