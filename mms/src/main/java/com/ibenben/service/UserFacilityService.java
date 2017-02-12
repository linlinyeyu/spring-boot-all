package com.ibenben.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibenben.domain.Facility;
import com.ibenben.mapper.UserFacilityMapper;

@Service
public class UserFacilityService {
	@Autowired
	private UserFacilityMapper userFacilityMapper;
	
	public List<Facility> getUserFacilityList(Integer userId){
		return userFacilityMapper.getUserFacilityList(userId);
	}
	
}
