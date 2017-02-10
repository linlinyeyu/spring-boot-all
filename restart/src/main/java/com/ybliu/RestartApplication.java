package com.ybliu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ybliu.domain.AuthorSettings;

/**
*@author ybliu
*@version 创建时间 :2017年1月25日下午10:28:59
*
*/
@RestController
@SpringBootApplication
public class RestartApplication {
	@Autowired
	private AuthorSettings authorSettings;
	
	@RequestMapping("/")
	String index(){
		return "author name is"+authorSettings.getName()+" and author age is"+authorSettings.getAge();
	}
	
	public static void main(String [] args) {
		SpringApplication.run(RestartApplication.class, args);
		
	}
}
