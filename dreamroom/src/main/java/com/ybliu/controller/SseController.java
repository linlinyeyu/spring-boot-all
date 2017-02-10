package com.ybliu.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
*@author ybliu
*@version 创建时间 :2017年1月17日下午11:12:43
*
*/
@Controller
public class SseController {
	@RequestMapping(value="/push",produces="text/event-stream")
	public @ResponseBody String push(){
		Random r = new Random();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "data:Testing 1,2,3"+r.nextInt()+"\n\n";
	}
}
