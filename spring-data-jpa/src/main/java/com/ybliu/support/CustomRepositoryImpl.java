package com.ybliu.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import static com.ybliu.specs.CustomerSpecs.*;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月15日 下午8:51:57
* 类说明
*/
public class CustomRepositoryImpl<T,ID extends Serializable> extends SimpleJpaRepository<T, ID> implements CustomRepository<T, ID>{
	private final EntityManager entityManager;
	
	public CustomRepositoryImpl(Class<T> domainClass, EntityManager entityManager){
		super(domainClass, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public Page<T> findByAuto(T example, Pageable pageable) {
		// TODO Auto-generated method stub
		return findAll(byAuto(entityManager,example),pageable);
	}
}
