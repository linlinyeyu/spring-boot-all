/**
 * 
 */
package com.ibenben;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.ibenben.config.datasource.DynamicDataSourceRegister;

/**
 * @author sszheng
 *
 * Created on 2016年7月11日 下午7:53:37
 * 其中@SpringBootApplication申明让spring
 * boot自动给程序进行必要的配置，等价于以默认属性使用@Configuration，@EnableAutoConfiguration和@ComponentScan
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.ibenben") 
@Import({DynamicDataSourceRegister.class}) // 注册动态多数据源
@PropertySources({
    @PropertySource(value = "classpath:application.properties"),
    @PropertySource(value = "classpath:datasource.properties")
})
public class Application {
	private final static Logger logger = LoggerFactory.getLogger(Application.class);
	 
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
		logger.info("============= SpringBoot Start Success =============");
	}
	
}
