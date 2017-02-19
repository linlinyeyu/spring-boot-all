package com.ybliu.domain;
/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月19日 下午10:34:03
* 类说明
*/
public class Msg {
	private String title;
	private String content;
	private String etraInfo;
	public Msg(String title, String content, String etraInfo) {
		super();
		this.title = title;
		this.content = content;
		this.etraInfo = etraInfo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEtraInfo() {
		return etraInfo;
	}
	public void setEtraInfo(String etraInfo) {
		this.etraInfo = etraInfo;
	}
	
	
}
