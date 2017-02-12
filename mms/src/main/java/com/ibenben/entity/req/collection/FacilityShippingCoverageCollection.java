package com.ibenben.entity.req.collection;

import com.alibaba.fastjson.annotation.JSONField;

public class FacilityShippingCoverageCollection {
	private Integer id;
	
	@JSONField(name="fulfill_end_time_name")
	private String fulfillEndTimeName;
	
	@JSONField(name="express_departure_time_name")
	private String expressDepartureTimeName;

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

	public String getExpressDepartureTimeName() {
		return expressDepartureTimeName;
	}

	public void setExpressDepartureTimeName(String expressDepartureTimeName) {
		this.expressDepartureTimeName = expressDepartureTimeName;
	}
}
