/**
 * 
 */
package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author Alex
 * 2018年4月28日
 * <p>desc:将索引库中的document封装成pojo</p>
 */
public class SearchResult implements Serializable{

	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<SearchItem> itemList;
	private long recourdCount;
	private int totalPages;
	
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	public long getRecourdCount() {
		return recourdCount;
	}
	public void setRecourdCount(long recourdCount) {
		this.recourdCount = recourdCount;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	
	
	

}
