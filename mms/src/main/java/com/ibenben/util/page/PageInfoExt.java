/**
 * 
 */
package com.ibenben.util.page;

import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * @author sszheng
 *
 * Created on 2016年7月27日 下午3:17:03
 * @param <T>
 */
public class PageInfoExt<T> extends PageInfo<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * @param list
	 */
	public PageInfoExt(List<T> list) {
		super(list);
		
	}

	private String result = "ok";

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
