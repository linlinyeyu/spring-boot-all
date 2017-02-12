package com.ibenben.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibenben.domain.UserToken;
import com.ibenben.exception.MmsException;
import com.ibenben.mapper.UserTokenMapper;

@Service
public class UserTokenService {
	@Autowired
	private UserTokenMapper utMapper;
	
	@Transactional(rollbackFor={MmsException.class})
	public int updateUserTokenOutValidByType(Integer id,String type) throws MmsException{
		return utMapper.updateUserTokenOutValidByType(id,type);
	}
	
	public Date getUserLastValidTokenTimeByType(Integer id,String type){
		return utMapper.getUserLastValidTokenTimeByType(id,type);
	}
	
	@Transactional(rollbackFor={MmsException.class})
	public int addUserToken(Integer id,String token,Date time,String type) throws MmsException{
		return utMapper.addUserToken(id,token,time,type);
	}
	
	@Transactional(rollbackFor={MmsException.class})
	public int updateOutTimeToken() throws MmsException{
		return utMapper.updateOutTimeToken();
	}
	
	public UserToken getToken(Map<String,Object> param){
		return utMapper.getToken(param);
	}
	
	@Transactional(rollbackFor={MmsException.class})
	public int updateOutValidToken(Map<String, Object> param) throws MmsException{
		return utMapper.updateOutValidToken(param);
	}
}
