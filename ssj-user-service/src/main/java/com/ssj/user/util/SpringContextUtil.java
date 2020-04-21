package com.ssj.user.util;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	/**
	 * 实现ApplicationContextAware接口的注入函数，applicationContext保存到静态变量
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	
	/**
	 * 从静态变量applicationContext取bean，并转成对应类型
	 * @param beanName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName){
		checkApplicationContext();
		return (T) applicationContext.getBean(beanName);
	}
	
	/**
	 * 从静态变量applicationContext取bean，并转成对应类型
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public static <T> Map<String, T> getBean(Class<T> clazz){
		checkApplicationContext();
		return applicationContext.getBeansOfType(clazz);
	}
	
	/**
	 * 清除静态变量applicationContext
	 */
	public static void cleanApplicationContext(){
		applicationContext = null;
	}
	
	private static void checkApplicationContext(){
		if(null == applicationContext){
			throw new IllegalStateException("applicationContext未注入");
		}
	}
}
