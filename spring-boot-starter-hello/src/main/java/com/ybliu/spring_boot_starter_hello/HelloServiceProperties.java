package com.ybliu.spring_boot_starter_hello;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月6日 下午2:20:26
* 类说明
*/
@ConfigurationProperties(prefix="hello")
public class HelloServiceProperties {
	private static final String MSG = " world";
	
	private String msg = MSG;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
