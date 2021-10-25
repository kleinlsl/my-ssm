package com.tujia.myssm.common.utils.redis;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import com.google.common.collect.Lists;
import com.tujia.myssm.BaseTest;
import com.tujia.myssm.api.model.PromoDueReminder;
import com.tujia.myssm.common.utils.JsonUtils;

/**
 *
 * @author: songlinl
 * @create: 2021/10/14 17:00
 */
public class RedisUtilsTest extends BaseTest {

    @Resource
    private RedisUtils redisUtils;

    //    @Before
    //    public void before(){
    //        redisUtils = application.getBean(RedisUtils.class);
    //    }

    @Test
    public void string() {
        final long memberId = 112L;
        final String key = RedisKeyManage.getDueReminderKey(memberId);

        redisUtils.set(key, JsonUtils.toJson(new PromoDueReminder()));
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
            redisUtils.hset(hashKey, key, String.valueOf(id));
            redisUtils.hincrBy(hashKey, key, 1);
            System.err.println("key=" + key + ";redisUtils = " + redisUtils.hget(hashKey, key));
        }

        System.out.println("redisUtils.hgetAll(hashKey) = " + redisUtils.hgetAll(hashKey));
    }

    @Test
    public void set() {

        redisUtils.sadd("key", (String[]) Lists.newArrayList("1", "2").toArray());
        //        redisUtils.srem("key","1");
        List<String> values = redisUtils.sentinelGetMasterAddrByName("key");
        System.out.println("values = " + values);
    }

}