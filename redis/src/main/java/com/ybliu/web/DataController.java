package com.ybliu.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ybliu.domain.Person;
import com.ybliu.domain.PersonDao;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月19日 下午9:19:15
* 类说明
*/
@RestController
public class DataController {
	@Autowired
	PersonDao personDao;
	
	@RequestMapping("/set")
	public void set(){
		Person person = new Person("1","ybliu",23);
		personDao.save(person);
		personDao.stringRedisTemplateDemo();
	}
	
	@RequestMapping("/getStr")
	public String getStr(){
		return personDao.getString();
	}
	
	@RequestMapping("/getPerson")
	public Person getPerson(){
		return personDao.getPerson();
	}
}
