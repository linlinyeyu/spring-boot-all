package com.ybliu.spring_boot_starter_hello;
/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月6日 下午2:25:32
* 类说明
*/
public class HelloService {
	private String msg;
	
	public String sayHello(){
		return "Hello"+msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
