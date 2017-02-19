package com.ybliu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ybliu.dao.SysUserRepository;
import com.ybliu.domain.SysUser;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月19日 下午10:38:43
* 类说明
*/
public class CustomUserService implements UserDetailsService {
	@Autowired
	SysUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser user = userRepository.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("用户名不存在");
		}
		return user;
	}

}
