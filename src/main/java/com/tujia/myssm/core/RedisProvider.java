package com.tujia.myssm.core;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 *
 * @author: songlinl
 * @create: 2022/12/17 20:33
 */
@Component()
public class RedisProvider {

    private static Tedis tedisOne;
    @Resource
    private Tedis tedis;

    public static Tedis get() {
        return tedisOne;
    }

    @PostConstruct
    public void afterInit() {
        tedisOne = tedis;
    }

}
