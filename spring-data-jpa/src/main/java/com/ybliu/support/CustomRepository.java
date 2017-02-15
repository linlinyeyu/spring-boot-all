package com.ybliu.support;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月15日 下午8:47:39
* 类说明
*/
@NoRepositoryBean
public interface CustomRepository<T,ID extends Serializable>extends JpaRepository<T, ID>,JpaSpecificationExecutor<T> {
	Page<T> findByAuto(T example,Pageable pageable);
}
