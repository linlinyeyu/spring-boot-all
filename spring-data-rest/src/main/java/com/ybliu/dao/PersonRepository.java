package com.ybliu.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ybliu.domain.Person;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月18日 上午11:32:14
* 类说明
*/
public interface PersonRepository extends JpaRepository<Person, Long>{
	Person findByNameStartsWith(String name);
}
