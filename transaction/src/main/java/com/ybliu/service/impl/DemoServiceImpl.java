package com.ybliu.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ybliu.dao.PersonRepository;
import com.ybliu.domain.Person;
import com.ybliu.service.DemoService;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月18日 下午1:15:01
* 类说明
*/
@Service
public class DemoServiceImpl implements DemoService{
	@Autowired
	PersonRepository personRepository;

	@Override
	@Transactional(rollbackFor={IllegalArgumentException.class})
	public Person savePersonWithRollBack(Person person) {
		Person p = personRepository.save(person);
		
		if(person.getName().equals("ybliu")){
			throw new IllegalArgumentException("ybliu已存在");
		}
		return p;
	}

	@Override
	@Transactional(noRollbackFor={IllegalArgumentException.class})
	public Person savePersonWithOutRollBack(Person person) {
		Person p = personRepository.save(person);
		
		if(person.getName().equals("ybliu")){
			throw new IllegalArgumentException("ybliu");
		}
		return p;
	}
	
	
}
