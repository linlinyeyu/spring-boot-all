package com.ybliu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.ybliu.service.PushService;

/**
*@author ybliu
*@version 创建时间 :2017年1月24日下午2:25:50
*
*/
@Controller
public class AsyncController {
	@Autowired
	PushService pushService;
	
	@RequestMapping("defer")
	@ResponseBody
	public DeferredResult<String> deferredCall(){
		return pushService.getAsyncUpdate();
	}
}
