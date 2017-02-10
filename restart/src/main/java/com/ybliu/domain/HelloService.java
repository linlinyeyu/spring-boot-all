package com.ybliu.domain;
/**
*@author ybliu
*@version 创建时间 :2017年1月26日下午4:16:56
*
*/

public class HelloService {
	private String  msg;
	
	public String sayHello() {
		return "Hello"+msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
