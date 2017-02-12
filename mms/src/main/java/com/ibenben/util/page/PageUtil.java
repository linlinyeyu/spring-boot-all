/**
 * 
 */
package com.ibenben.util.page;

import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.ibenben.util.BeanUtil;

/**
 * @author sszheng
 *
 * Created on 2016年7月27日 下午3:53:10
 */
public class PageUtil {
	public static void setPageCondition(PageCondition pageCondition) {
		if(pageCondition.getPage() != null && pageCondition.getPageSize() != null && pageCondition.getPageSize() !=0 ) {
			PageHelper.startPage(pageCondition.getPage(), pageCondition.getPageSize());
		}
		
		if(StringUtil.isNotEmpty(pageCondition.getOrder())) {
			PageHelper.orderBy(pageCondition.getOrder() + " " + pageCondition.getSort());
		}
	}
	
	public static void setPageCondition(Map<String, Object> params) {
		PageCondition pc = new PageCondition();
		BeanUtil.transMap2Bean(params, pc);
		
		setPageCondition(pc);
	}
	
}
