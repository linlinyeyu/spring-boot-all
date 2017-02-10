package com.ybliu;
/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月8日 下午6:28:11
* 类说明
*/
public class Person {
	private String name;
	private Integer age;
	
	public Person(){
		super();
	}
	
	public Person(String name,Integer age){
		super();
		this.name = name;
		this.age = age;
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
