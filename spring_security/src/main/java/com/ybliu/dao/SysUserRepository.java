package com.ybliu.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ybliu.domain.SysUser;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月19日 下午10:37:08
* 类说明
*/
public interface SysUserRepository extends JpaRepository<SysUser, Long> {
	SysUser findByUsername(String username);
}
