package com.ybliu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ybliu.domain.DemoObj;

/**
*@author ybliu
*@version 创建时间 :2016年12月16日下午8:52:02
*自定义媒体类型
*/

@Controller
public class ConverterController {
	@RequestMapping(value="/convert",produces="application/x-wisely")
	public @ResponseBody DemoObj convert(@RequestBody DemoObj demoObj){
		return demoObj;
	}
}
