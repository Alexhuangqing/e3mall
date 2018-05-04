/**
 * 
 */
package cn.e3mall.common.pojo;

import java.io.Serializable;


/**
 * @author Alex
 * 2018年4月14日
 * <p>desc:封装返回的一条目录记录</p>
 */
public class EasyUITreeNode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String text;
	/**
	 * 是否有子目录  open |  closed
	 */
	private String state;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	

}
