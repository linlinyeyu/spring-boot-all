/**
 * 
 */
package com.ibenben.service.weishop;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibenben.config.datasource.TargetDataSource;
import com.ibenben.domain.User;
import com.ibenben.domain.weishop.AdminSessions;
import com.ibenben.mapper.weishop.AdminSessionsMapper;
import com.ibenben.mapper.weishop.AdminUserMapper;
import com.ibenben.util.RandomUtil;
import com.ibenben.util.UserUtil;

/**
 * @author sszheng
 *
 * Created on 2016年8月3日 上午10:43:24
 */
@Service
public class AdminSessionsService {
	@Autowired
	private AdminSessionsMapper adminSessionsMapper;
	@Autowired
	private AdminUserMapper adminUserMapper;
	
	@TargetDataSource(name = "weishop")
	public String insertAdminSession() {
		AdminSessions adminSessions = new AdminSessions();
		User user = UserUtil.getAdminUser();
		adminSessions.setAdminUserId(UserUtil.getAdminUser().getUserId());
		Date now = new Date();
		adminSessions.setCreateTime(now);
		adminSessions.setUpdateTime(now);
		adminSessions.setExpireTime(new Date(now.getTime() + 1 * 6 * 60 * 60 * 1000));
		String token = RandomUtil.generateToken(user.getUserId() + user.getPassword()+ now);
		adminSessions.setAccessToken(token);
//		adminSessionsMapper.insert(adminSessions);
		return token;
	}
	
}