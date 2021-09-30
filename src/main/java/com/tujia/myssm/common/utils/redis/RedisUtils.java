package com.tujia.myssm.common.utils.redis;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 *
 * @author: songlinl
 * @create: 2021/09/29 21:52
 */
@Component
public class RedisUtils {

    @Resource
    private Jedis jedis;

    public String get(String key) {
        return jedis.get(key);
    }

    public void set(String key, String value) {
        jedis.set(key, value);
    }
}
