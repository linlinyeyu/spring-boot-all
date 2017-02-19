package com.ybliu.service;

import com.ybliu.domain.Person;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月18日 下午1:13:28
* 类说明
*/
public interface DemoService {
	public Person savePersonWithRollBack(Person person);
	public Person savePersonWithOutRollBack(Person person);
}
