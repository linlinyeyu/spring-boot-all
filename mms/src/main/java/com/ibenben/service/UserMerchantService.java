package com.ibenben.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibenben.domain.Merchant;
import com.ibenben.domain.UserMerchantKey;
import com.ibenben.mapper.UserMerchantMapper;

@Service
public class UserMerchantService {

	@Autowired
	private UserMerchantMapper userMerchantMapper;

	public List<Merchant> getUserMerchantList(Integer userId) {
		return userMerchantMapper.getUserMerchantList(userId);
	}
	
	public int addUserMerchant(Integer userId,Integer merchantId){
		UserMerchantKey um = new UserMerchantKey();
		um.setUserId(userId);
		um.setMerchantId(merchantId);
		return userMerchantMapper.insert(um);
	}
}
