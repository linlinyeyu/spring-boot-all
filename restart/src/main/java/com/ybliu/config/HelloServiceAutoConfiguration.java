package com.ybliu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ybliu.domain.HelloService;
import com.ybliu.domain.HelloServiceProperties;

/**
*@author ybliu
*@version 创建时间 :2017年1月26日下午4:23:48
*
*/
@Configuration
@EnableConfigurationProperties(HelloServiceProperties.class)
@ConditionalOnClass(HelloService.class)
@ConditionalOnProperty(prefix="hello",value="enabled",matchIfMissing=true)
public class HelloServiceAutoConfiguration {
	@Autowired
	private HelloServiceProperties helloServiceProperties;
	
	@Bean
	@ConditionalOnMissingBean(HelloService.class)
	public HelloService helloService(){
		HelloService helloService = new HelloService();
		helloService.setMsg(helloServiceProperties.getMsg());
		return helloService;
	}
}
