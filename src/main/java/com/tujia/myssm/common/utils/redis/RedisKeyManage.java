package com.tujia.myssm.common.utils.redis;

/**
 *
 * @author: songlinl
 * @create: 2021/09/30 13:14
 */
public class RedisKeyManage {

    private static final String KEY_PREFIX = "my-ssm:";

    /**
     * 获取前缀
     * @return
     */
    public static String getKeyPrefix() {
        return KEY_PREFIX;
    }

}
