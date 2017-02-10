package com.ybliu.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
*@author ybliu
*@version 创建时间 :2017年1月26日下午2:36:45
*
*/
@Component
@ConfigurationProperties(prefix="author")
public class AuthorSettings {
	private String name;
	private Long age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getAge() {
		return age;
	}
	public void setAge(Long age) {
		this.age = age;
	}
}
