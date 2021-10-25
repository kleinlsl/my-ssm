package com.tujia.myssm.common.utils.redis;

import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import lombok.experimental.Delegate;
import redis.clients.jedis.Jedis;

/**
 *
 * @author: songlinl
 * @create: 2021/09/29 21:52
 */
@Component
public class RedisUtils {

    @Resource
    @Delegate
    private Jedis jedis;

    /**
     * 删除给定的key
     * @param key
     * @return
     */
    public long delKey(String key) {
        return Optional.ofNullable(jedis.del(key)).orElse(0L);
    }

    public long delKey(String... keys) {
        return Optional.ofNullable(jedis.del(keys)).orElse(0L);
    }

}
