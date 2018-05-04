/**
 * 
 */
package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Alex
 * 2018年3月18日 
 * @className EasyUIDataGridResult
 * @Description 与页面交互的json格式数据的 pojo
 */
public class EasyUIDataGridResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long total;
	private List<?> rows;
	
	
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
	
	
	
	

}
