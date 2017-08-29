package com.auto.test.context;

import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContext implements ApplicationContextAware {
	
	private static ApplicationContext context;

	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		context = arg0;
	}
	
	public static <T> Map<String,T> getBeanByClass(Class<T> clazz ){
		Map<String,T> result = context.getBeansOfType(clazz);
		return result;
	}
	
	public static Object getBean(String beanName){
		return context.getBean(beanName);
	}
	
}
