package com.ybliu.service;

import com.ybliu.domain.Person;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月19日 上午10:55:06
* 类说明
*/
public interface DemoService {
	public Person save(Person person);
	
	public void remove(Long id);
	
	public Person findOne(Person person);
}
