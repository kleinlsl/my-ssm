package com.tujia.myssm.common.utils;

import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring 上下文获取
 *
 * @author: songlinl
 * @create: 2021/09/13 14:11
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext application;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setApplication(applicationContext);
    }

    public static <T> T getBean(String beanName) {
        return (T) application.getBean(beanName);
    }

    public static <T> T getBean(Class<T> t) {
        return application.getBean(t);
    }

    public static <T> T getBean(String beanName, Object... params) {
        return (T) application.getBean(beanName, params);
    }

    public static <T> Map<String, T> getBeanOfType(Class<T> classType) {
        return application.getBeansOfType(classType);
    }

    public synchronized static void setApplication(ApplicationContext applicationContext) {
        application = applicationContext;
    }
}
