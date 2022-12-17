package com.tujia.myssm.service.impl;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.tujia.myssm.service.RedisUtilService;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author: songlinl
 * @create: 2021/09/29 21:52
 */
@Slf4j
@Service
@Deprecated
public class RedisUtilServiceImpl implements RedisUtilService {

    @Resource
    private Jedis jedis;
    @Resource
    public JedisPool jedisPool;

    private Jedis getJedis() {
        try {
            return jedisPool.getResource();
        } catch (Exception e) {
            log.error("RedisUtilService get jedis error");
        }
        return jedis;
    }

    @Override
    public String set(String key, String value) {
        try {
            return getJedis().set(key, value);
        } catch (Exception e) {
            log.error("run redis command error", e);
            return null;
        }
    }

    @Override
    public Long hset(String key, String field, String value) {
        try {
            return getJedis().hset(key, field, value);
        } catch (Exception e) {
            log.error("run redis command error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long hincrBy(String key, String field, long value) {
        try {
            return getJedis().hincrBy(key, field, value);
        } catch (Exception e) {
            log.error("run redis command error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String hget(String key, String field) {
        try {
            return getJedis().hget(key, field);
        } catch (Exception e) {
            log.error("run redis command error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        try {
            return getJedis().hgetAll(key);
        } catch (Exception e) {
            log.error("run redis command error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long sadd(String key, String[] members) {
        try {
            return getJedis().sadd(key, members);
        } catch (Exception e) {
            log.error("run redis command error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String get(String key) {
        try {
            return getJedis().get(key);
        } catch (Exception e) {
            log.error("run redis command error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String set(String key, String value, String nxxx, String expx, long time) {
        try {
            return getJedis().set(key, value, nxxx, expx, time);
        } catch (Exception e) {
            log.error("run redis command error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object eval(String script, int keyCount, String key, String arg) {
        try {
            return getJedis().eval(script, keyCount, key, arg);
        } catch (Exception e) {
            log.error("run redis command error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public long incr(String key) {
        return getJedis().incr(key);
    }

    @Override
    public long incrAndJudge(String key, long expx) {
        return 0;
    }
}
