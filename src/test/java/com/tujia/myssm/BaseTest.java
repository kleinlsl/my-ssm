package com.tujia.myssm;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author: songlinl
 * @create: 2021/08/01 18:03
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config.xml", "classpath:mybatis-config.xml" })
@WebAppConfiguration("web")
public class BaseTest {
    //        public static ApplicationContext application;
    //
    //        static {
    //                application = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
    //        }
}
