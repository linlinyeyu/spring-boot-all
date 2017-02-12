package com.ibenben.util;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class ApplicationUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	private final static Logger logger = LoggerFactory.getLogger(ApplicationUtil.class);

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	@SuppressWarnings("hiding")
	public static <T> T getBean(Class<T> clazz) {
		if (applicationContext == null) {
			logger.info("applicationContext is null");
		}
		return applicationContext.getBean(clazz);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (ApplicationUtil.applicationContext == null) {
			ApplicationUtil.applicationContext = applicationContext;
		}
	}
}
