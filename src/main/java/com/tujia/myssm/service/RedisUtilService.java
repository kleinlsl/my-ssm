package com.tujia.myssm.service;

import java.util.Map;

/**
 *
 * @author: songlinl
 * @create: 2022/01/26 16:49
 */
public interface RedisUtilService {

    String set(String key, String value);

    Long hset(String key, String field, String value);

    Long hincrBy(String key, String field, long value);

    String hget(String key, String field);

    Map<String, String> hgetAll(String key);

    Long sadd(String key, String[] toArray);

    String get(String key);

    String set(String key, String value, String nxxx, String expx, long time);

    Object eval(String script, int keyCount, String key, String arg);
}
