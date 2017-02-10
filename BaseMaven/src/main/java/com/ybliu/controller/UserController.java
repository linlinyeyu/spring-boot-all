package com.ybliu.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiOperation;
import com.ybliu.domain.User;
import com.ybliu.service.UserService;

@RestController
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	/**
	 * 获取user列表
	 */
	@ApiOperation(value="获取user列表",notes="参数:user_id")
	@ApiImplicitParam(name="user_id",value="用户id",paramType="query",dataType="int")
	@RequestMapping(value="/get",method=RequestMethod.GET)
	public Map<String,Object> getUserById(@RequestParam Byte user_id){
		Map<String,Object> map = new HashMap<String,Object>();
		User u = userService.getUserById(user_id);
		
		map.put("user", u);
		return map;
	}
}
