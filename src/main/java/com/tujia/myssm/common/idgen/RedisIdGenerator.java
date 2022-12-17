package com.tujia.myssm.common.idgen;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import com.tujia.myssm.service.RedisUtilService;

/**
 *
 * @author: songlinl
 * @create: 2022/12/13 10:48
 */
public class RedisIdGenerator implements IdGenerator {
    /**
     * 开始时间戳
     */
    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.of("+8");
    private static final LocalDateTime BEGIN_TIME = LocalDateTime.parse("2000-01-01T00:00:");
    private static final long BEGIN_TIMESTAMP = BEGIN_TIME.toEpochSecond(ZONE_OFFSET);
    /**
     * 序列号的位数
     */
    private static final int SEQUENCE_BITS = 32;

    private final String keyPrefix;
    private final DateTimeFormatter dateTimeFormatter;
    private final RedisUtilService redisTemplate;

    public RedisIdGenerator(RedisUtilService redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.keyPrefix = "default";
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(TimeZone.getTimeZone("GMT+8").toZoneId());
    }

    public RedisIdGenerator(RedisUtilService redisTemplate, String keyPrefix, DateTimeFormatter formatter) {
        this.redisTemplate = redisTemplate;
        this.keyPrefix = keyPrefix;
        this.dateTimeFormatter = formatter;
    }

    @Override
    public Long nextId() {
        // 1. 生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZONE_OFFSET);
        long timestamp = nowSecond - BEGIN_TIMESTAMP;
        // 2. 生成序列号
        // 2.1 获取当前日期，精确到天
        String date = now.format(dateTimeFormatter);
        // 2.2 获取redis自增长值
        long sequence = redisTemplate.incr("RedisId:" + keyPrefix + date);
        // 3. 拼接并返回
        return sequence << SEQUENCE_BITS | timestamp;
    }

    @Override
    public String nextCode() {
        return null;
    }
}
