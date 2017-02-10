package com.ybliu.domain;
/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月9日 下午6:41:22
* 类说明
*/
public class YbliuResponse {
	private String responseMessage;
	
	public YbliuResponse(String responseMessage){
		this.responseMessage = responseMessage;
	}
	
	public String getResponseMessage(){
		return responseMessage;
	}
}
