package com.ybliu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
* @author 作者 ybliu:
* @version 创建时间：2017年2月9日 下午7:20:15
* 类说明
*/
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addViewController("/ws").setViewName("/ws");
		registry.addViewController("/login").setViewName("/login");
		registry.addViewController("/chat").setViewName("/chat");
		registry.addViewController("/angular").setViewName("/angular");
	}
}
