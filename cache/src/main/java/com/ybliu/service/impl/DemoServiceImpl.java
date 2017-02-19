package com.ybliu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ybliu.dao.PersonRepository;
import com.ybliu.domain.Person;
import com.ybliu.service.DemoService;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月19日 上午10:56:31
* 类说明
*/
@Service
public class DemoServiceImpl implements DemoService{
	@Autowired
	PersonRepository personRepository;

	@Override
	@CachePut(value="people",key="#person.id")
	public Person save(Person person) {
		Person p = personRepository.save(person);
		System.out.println("为id、key为"+p.getId()+"数据做了缓存");
		return p;
	}

	@Override
	@CacheEvict(value="people")
	public void remove(Long id) {
		System.out.println("删除了id、key为"+id+"的数据缓存");
	}

	@Override
	@Cacheable(value="people",key="#person.id")
	public Person findOne(Person person) {
		Person p = personRepository.findOne(person.getId());
		System.out.println("为id、key为："+p.getId()+"数据做了缓存");
		return p;
	}
	
	
}