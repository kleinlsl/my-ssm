package com.tujia.myssm.core;

import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author: songlinl
 * @create: 2022/12/17 19:44
 */
@Slf4j
public class Tedis {

    private Jedis jedis;
    private JedisPool jedisPool;

    public Tedis(final Jedis jedis, final JedisPool jedisPool) {
        this.jedis = jedis;
        this.jedisPool = jedisPool;
    }

    @Delegate
    private Jedis getJedis() {
        try {
            return jedisPool.getResource();
        } catch (Exception e) {
            log.error("RedisUtilService get jedis error");
        }
        return jedis;
    }
}
