package com.tujia.myssm.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.tujia.myssm.cache.LocalCache;

/**
 *
 * @author: songlinl
 * @create: 2021/09/29 15:36
 */
public class testApplicationContext {

    private static ApplicationContext application;

    static {
        application = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
    }

    @Test
    public void testGetBean() {
        LocalCache localCache = null;
        localCache = (LocalCache) application.getBean("localCache");
        System.out.println("localCache = " + localCache);
        localCache = (LocalCache) application.getBean(LocalCache.class);
        System.out.println("localCache = " + localCache);
    }
}
