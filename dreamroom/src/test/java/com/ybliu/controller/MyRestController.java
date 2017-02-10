package com.ybliu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ybliu.service.DemoService;

/**
*@author ybliu
*@version 创建时间 :2017年1月24日下午9:19:35
*
*/
@RestController
public class MyRestController {
	@Autowired
	DemoService demoService;
	
	@RequestMapping(value="/testRest",produces="text/plain;charset=UTF-8")
	public @ResponseBody String testRest(){
		return demoService.saySomething();
	}
}
