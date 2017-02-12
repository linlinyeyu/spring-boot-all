/**
 * 
 */
package com.ibenben.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ibenben.config.interceptors.DataSourceInterceptor;

/**
 * @author sszheng
 *
 * Created on 2016年8月24日 上午11:54:34
 */
@Configuration
public class MmsWebAppConfigurer extends WebMvcConfigurerAdapter{
	 @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        // 多个拦截器组成一个拦截器链
	        // addPathPatterns 用于添加拦截规则
	        // excludePathPatterns 用户排除拦截
	        registry.addInterceptor(new DataSourceInterceptor()).addPathPatterns("/**");
	        super.addInterceptors(registry);
	    }
}
