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

}
