package com.ybliu;
/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月10日 下午8:58:36
* 类说明
*/
public class Person {
	private String name;
	private Integer age;
	private String address;
	
	public Person(){
		super();
	}
	public Person(String name,Integer age,String address){
		super();
		this.name = name;
		this.age = age;
		this.address = address;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
