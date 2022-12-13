package com.tujia.myssm.service.idworker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tujia.myssm.utils.crypto.Base62;
import com.tujia.myssm.web.utils.IpUtils;

/**
 * 雪花算法
 * @author: songlinl
 * @create: 2022/12/13 10:38
 */
public class SnowFlakeIdGenerator implements IdGenerator {
    private final Logger logger = LoggerFactory.getLogger(SnowFlakeIdGenerator.class);

    /**
     * 起始的时间戳
     */
    private final long twepoch = Date.from(LocalDate.of(2018, 1, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime();

    /**
     * 每一部分占用的位数
     */
    private final long sequenceBits = 12L;
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;

    /**
     * 每一部分的最大值
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 每一部分向左的位移
     */
    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    private long workerId;// 数据中心
    private long datacenterId;// 机器标识
    private long sequence = 0L;// 序列号
    private long lastTimestamp = -1L;// 上一次时间戳

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        String ip = IpUtils.getLocalIpByNetcard();
        if (StringUtils.isEmpty(ip))
            throw new RuntimeException("IdWorker get ip is empty");
        this.workerId = this.datacenterId = Math.abs(ip.hashCode() % 31);
        logger.info("IdWorkerService init, ip:{}, workerId:{}, datacenterId；{}", ip, workerId, datacenterId);
    }

    /**
     * Next id long.
     *
     * @return the long
     */
    @Override
    public synchronized Long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    /**
     * next code string
     */
    @Override
    public String nextCode() {
        return Base62.encode(nextId());
    }

    /**
     * Til next millis long.
     *
     * @param lastTimestamp the last timestamp
     * @return the long
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * Time gen long.
     *
     * @return the long
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
