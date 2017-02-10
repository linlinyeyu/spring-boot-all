package com.ybliu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ybliu.domain.DemoObj;

@Controller
public class AdviceController {
	@RequestMapping(value="/advice")
	public String getSomething(@ModelAttribute("msg")String msg,DemoObj obj){
		throw new IllegalArgumentException("非常抱歉，参数有误/"+"来自@ModelAttribute:"+msg);
	}
}
