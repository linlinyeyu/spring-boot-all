package com.ybliu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ybliu.service.DemoService;

/**
*@author ybliu
*@version 创建时间 :2017年1月24日下午9:15:10
*
*/
@Controller
public class NormalController {
	@Autowired
	DemoService demoService;
	
	@RequestMapping("normal")
	public String testPage(Model model){
		model.addAttribute("msg",demoService.saySomething());
		return "page";
	}
}
