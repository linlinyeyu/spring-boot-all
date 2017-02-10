package com.ybliu;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {
	
	@RequestMapping("/")
	String home(){
		return "hello world";
	}
	
	@RequestMapping("/now")
	String hehe(){
		return "现在时间"+(new Date().toString());
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
