package com.ibenben.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibenben.domain.User;
import com.ibenben.domain.UserSession;
import com.ibenben.mapper.UserSessionMapper;
import com.ibenben.util.RandomUtil;
import com.ibenben.util.UserUtil;

@Service
public class UserSessionService {
	@Autowired
	private UserSessionMapper userSessionMapper;
	
	public String insertUserSession(String ip){
			UserSession userSessions = new UserSession();
			User user = UserUtil.getAdminUser();
			userSessions.setUserId(UserUtil.getAdminUser().getUserId());
			Date now = new Date();
			userSessions.setCreatedTime(now);
			userSessions.setLastLoginTime(now);
			userSessions.setExpireTime(new Date(now.getTime() + 1 * 6 * 60 * 60 * 1000));
			userSessions.setIp(ip);
			userSessions.setGroupType("mms");
			String token = RandomUtil.generateToken(user.getUserId() + user.getPassword()+ now);
			userSessions.setAccessToken(token);
			userSessionMapper.insert(userSessions);
			return token;
	}
	
	public String insertApiUserSession(User user,String ip,String type){
		UserSession userSessions = new UserSession();
		userSessions.setUserId(user.getUserId());
		Date now = new Date();
		userSessions.setCreatedTime(now);
		userSessions.setLastLoginTime(now);
		userSessions.setExpireTime(new Date(now.getTime() + 1 * 6 * 60 * 60 * 1000));
		userSessions.setIp(ip);
		userSessions.setGroupType(type);
		String token = RandomUtil.generateToken(user.getUserId() + user.getPassword()+ now);
		userSessions.setAccessToken(token);
		userSessionMapper.insert(userSessions);
		return token;
	}
	
	public UserSession getUserSessionByToken(String token){
		return userSessionMapper.getUserSessionByToken(token);
	}
}
