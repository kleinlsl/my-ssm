package com.tujia.myssm.common.redis;

/**
 *
 * @author: songlinl
 * @create: 2021/09/30 13:14
 */
public class RedisKeyManage {

    private static final String KEY_PREFIX = "my-ssm:";

    private static final String DELIMITER = ":";

    /**
     * 获取前缀
     * @return
     */
    public static String getKeyPrefix() {
        return KEY_PREFIX;
    }

    /**
     * 到期提醒 key
     * @param memberId
     * @return
     */
    public static String getDueReminderKey(Long memberId) {
        return KEY_PREFIX + "DueReminder" + DELIMITER + memberId;
    }

    /**
     * 到期提醒(Hash key)
     * @return
     */
    public static String getHashDueReminderKey() {
        return KEY_PREFIX + "DueReminder";
    }

}
