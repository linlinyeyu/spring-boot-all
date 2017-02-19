package com.ybliu.domain;

import java.io.Serializable;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月19日 下午8:19:33
* 类说明
*/
public class Person implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private Integer age;
	public Person(String id, String name, Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	
}
