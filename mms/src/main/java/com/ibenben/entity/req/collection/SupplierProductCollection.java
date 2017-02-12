/**
 * 
 */
package com.ibenben.entity.req.collection;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author sszheng
 *
 * Created on 2016年8月17日 下午4:10:22
 */
public class SupplierProductCollection implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JSONField(name="product_supplier_id")
	private int productSupplierId;
	@JSONField(name="product_id")
	private int productId;
	
	public int getProductSupplierId() {
		return productSupplierId;
	}
	public void setProductSupplierId(int productSupplierId) {
		this.productSupplierId = productSupplierId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
}
