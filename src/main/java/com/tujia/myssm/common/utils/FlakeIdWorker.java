package com.tujia.myssm.common.utils;

import java.math.BigInteger;

/**
 * 总位数 ： 64
 *
 * 符号   ： 1
 * 时间   ： 41
 * 机器ID ： 10
 * 序列号 ： 12
 * @author: songlinl
 * @create: 2021/12/24 17:40
 */
public class FlakeIdWorker {

    /**
     * 机器位数
     */
    private final long hostIdBits = 0L;

    /**
     * 序列位数
     */
    private final long sequenceBits = 12L;

    /**
     * 时间位数
     */
    private final long timestampBits = 41;

    /**
     * 最后使用的时戳
     */
    private long lastTimestamp = -1L;

    private long sequence = 0;

    private long hostId = 0;

    public static void main(String[] args) {
        System.out.println("args = " + Long.toBinaryString(System.currentTimeMillis()).length());
        System.out.println("args = " + Long.toBinaryString(~(-1L << 12L)));
        System.out.println("args = " + Long.toBinaryString(1));

        FlakeIdWorker worker = new FlakeIdWorker();
        for (int i = 0; i < 100; i++) {
            Long id = worker.nextId();
            System.out.println("id = " + id);
            System.out.println("Long.toBinaryString(id) = " + Long.toBinaryString(id).length());
            System.out.println("Long.toBinaryString(id) = " + Long.toString(id, 16).toUpperCase());
            BigInteger b = new BigInteger("100000000000000000000");
        }
    }

    /**
     *
     * @return
     */
    public synchronized Long nextId() {
        Long timestamp = genTime();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("e");
        }
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & ~(-1L << sequenceBits);
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        return (lastTimestamp << (hostIdBits + sequenceBits)) | (hostId << (sequenceBits)) | (sequence);
    }

    private Long tilNextMillis(long lastTimestamp) {
        while (genTime() <= lastTimestamp) {
        }
        return genTime();
    }

    public Long genTime() {
        return System.currentTimeMillis();
    }

}
