package com.tujia.myssm.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.tujia.myssm.core.cache.LocalCache;
import redis.clients.jedis.Jedis;

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

    @Test
    public void testGetJedis() {
        Jedis jedis = application.getBean(Jedis.class);
        System.out.println("jedis = " + jedis.get("name"));
    }

    @Test
    public void testJedis() {
        //设置ip地址

        Jedis jedis = new Jedis("192.168.204.135", 6379, 1000, 10000);

        //保存数据

        jedis.set("name", "imooc");

        String val = jedis.get("name");

        System.out.println(val);

        //释放资源

        jedis.close();
    }

}
