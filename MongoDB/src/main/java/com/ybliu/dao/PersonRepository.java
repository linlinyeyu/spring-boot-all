package com.ybliu.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ybliu.domain.Person;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月19日 下午6:59:50
* 类说明
*/
public interface PersonRepository extends MongoRepository<Person, String>{
	Person findByName(String name);
	
	@Query("{'age':?0}")
	List<Person> withQueryFindByAge(Integer age);
} 
