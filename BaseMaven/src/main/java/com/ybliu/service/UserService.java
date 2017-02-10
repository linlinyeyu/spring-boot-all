package com.ybliu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybliu.domain.User;
import com.ybliu.mapper.UserMapper;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	/**
	 * 获取用户信息
	 */
	public User getUserById(Byte id) {
		return userMapper.selectByPrimaryKey(id);
	}
}
