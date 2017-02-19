package com.ybliu.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ybliu.domain.Person;
import com.ybliu.service.DemoService;


/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月19日 上午11:05:13
* 类说明
*/
@RestController
public class CacheController {
	@Autowired
	DemoService demoservice;
	
	@RequestMapping("/put")
	public Person put(Person person){
		return demoservice.save(person);
	}
	
	@RequestMapping("/able")
	public Person cacheable(Person person){
		return demoservice.findOne(person);
	}
	
	@RequestMapping("/evit")
	public String evit(Long id){
		demoservice.remove(id);
		return "ok";
	}
}
