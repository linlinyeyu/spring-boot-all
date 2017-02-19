package com.ybliu.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ybliu.domain.Person;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月18日 下午1:12:30
* 类说明
*/
public interface PersonRepository extends JpaRepository<Person, Long> {

}
