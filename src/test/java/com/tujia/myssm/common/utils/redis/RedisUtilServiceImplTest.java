package com.tujia.myssm.common.utils.redis;

import java.util.Arrays;
import javax.annotation.Resource;
import org.junit.Test;
import com.tujia.myssm.BaseTest;
import com.tujia.myssm.api.model.PromoDueReminder;
import com.tujia.myssm.common.utils.JsonUtils;
import com.tujia.myssm.service.impl.RedisUtilServiceImpl;

/**
 *
 * @author: songlinl
 * @create: 2021/10/14 17:00
 */
public class RedisUtilServiceImplTest extends BaseTest {

    @Resource
    private RedisUtilServiceImpl redisUtilServiceImpl;

    //    @Before
    //    public void before(){
    //        redisUtils = application.getBean(RedisUtils.class);
    //    }

    @Test
    public void string() {
        final long memberId = 112L;
        final String key = RedisKeyManage.getDueReminderKey(memberId);

        redisUtilServiceImpl.set(key, JsonUtils.toJson(new PromoDueReminder()));
        //        redisUtils.incr(key);
        //        System.err.println("redisKeyUtils = " + redisUtils.get(key));
        //
        //        redisUtils.decr(key);
        //        System.err.println("redisKeyUtils = " + redisUtils.get(key));

        //        redisUtils.append(key,"2");
        //        System.out.println("JsonUtils.readValue(redisUtils.get(key)) = " + JsonUtils
        //                .readValue(redisUtils.get(key), PromoDueReminder.class));
    }

    @Test
    public void hash() {
        final long memberId = 112L;
        final String hashKey = RedisKeyManage.getHashDueReminderKey();

        for (long id = 1L; id < memberId; id++) {
            String key = RedisKeyManage.getDueReminderKey(id);
            redisUtilServiceImpl.hset(hashKey, key, String.valueOf(id));
            redisUtilServiceImpl.hincrBy(hashKey, key, 1);
            System.err.println("key=" + key + ";redisUtils = " + redisUtilServiceImpl.hget(hashKey, key));
        }

        System.out.println("redisUtils.hgetAll(hashKey) = " + redisUtilServiceImpl.hgetAll(hashKey));
    }

    @Test
    public void set() {
        redisUtilServiceImpl.sadd("key", (String[]) Arrays.asList("1", "2").toArray());
        //        redisUtilServiceImpl.srem("key","1");
    }

}