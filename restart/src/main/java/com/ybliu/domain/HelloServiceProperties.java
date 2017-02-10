package com.ybliu.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
*@author ybliu
*@version 创建时间 :2017年1月26日下午4:14:29
*
*/
@ConfigurationProperties(prefix="hello")
public class HelloServiceProperties {
	private static final String MSG = "world";
	
	private String msg = MSG;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
