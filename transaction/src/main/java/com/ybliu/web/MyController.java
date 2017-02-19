package com.ybliu.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ybliu.domain.Person;
import com.ybliu.service.DemoService;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月18日 下午1:27:56
* 类说明
*/
@RestController
public class MyController {
	@Autowired
	DemoService demoService;
	
	@RequestMapping("/rollback")
	public Person rollback(Person person){
		return demoService.savePersonWithRollBack(person);
	}
	
	@RequestMapping("/norollback")
	public Person noRollback(Person person){
		return demoService.savePersonWithOutRollBack(person);
	}
}