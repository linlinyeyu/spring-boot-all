package com.ybliu.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ybliu.domain.Person;
import com.ybliu.support.CustomRepository;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月14日 下午1:11:39
* 类说明
*/
public interface PersonRepository extends CustomRepository<Person, Long>{
	List<Person> findByAddress(String address);
	Person findByNameAndAddress(String name,String address);
	@Query("select p from Person p where p.name= :name and p.address= :address")
	Person withNameAndAddressQuery(@Param("name")String name,@Param("address")String address);
	Person withNameAndAddressNamedQuery(String name,String address);
}
