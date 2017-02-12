/**
 * 
 */
package com.ibenben.util.page;

import java.io.Serializable;

/**
 * @author sszheng
 *
 * Created on 2016年7月27日 下午3:53:45
 */
public class PageCondition implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1280389679103980380L;
	private Integer page;
	private Integer pageSize;
	private String order;
	private String sort = "asc";
	
	/**
	 * 
	 */
	public PageCondition() {
		super();
	}
	/**
	 * @param page
	 * @param pageSize
	 * @param order
	 * @param sort
	 */
	public PageCondition(Integer page, Integer pageSize, String order, String sort) {
		super();
		this.page = page;
		this.pageSize = pageSize;
		this.order = order;
		this.sort = sort;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	
}
