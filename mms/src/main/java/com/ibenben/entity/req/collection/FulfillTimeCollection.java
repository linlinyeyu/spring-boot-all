package com.ibenben.entity.req.collection;

import com.alibaba.fastjson.annotation.JSONField;

public class FulfillTimeCollection {
	
	private Integer id;
	
	@JSONField(name="fulfill_end_time_name")
	private String fulfillEndTimeName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFulfillEndTimeName() {
		return fulfillEndTimeName;
	}

	public void setFulfillEndTimeName(String fulfillEndTimeName) {
		this.fulfillEndTimeName = fulfillEndTimeName;
	}
}
