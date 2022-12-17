package com.tujia.myssm.common;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author: songlinl
 * @create: 2021/12/23 20:21
 */
public class IDWorker {
    private AtomicInteger sequence = new AtomicInteger(1000);

    private String lastPrefix = "";

    private long lastTimestamp = -1L;

    public static void main(String[] args) {
        IDWorker idWorker = new IDWorker();
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.println("genPromoCode = " + idWorker.genPromoCode(String.valueOf(i) + "_"));
            }
        }
    }

    public synchronized String genPromoCode(String prefix) {
        if (prefix.equals(lastPrefix)) {
            int value = sequence.get();
            if (value != 0) {
                prefix += value;
                sequence.decrementAndGet();
                return prefix;
            }
            prefix += UUID.randomUUID().toString().toUpperCase();
            return prefix;
        }
        sequence.set(1000);
        lastPrefix = prefix;
        return prefix + sequence.getAndDecrement();
    }

    public String nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("e");
        }
        if (timestamp == lastTimestamp) {
            int value = sequence.getAndDecrement();
            return timestamp + "";
        }
        lastTimestamp = timestamp;
        return "";
    }

    public synchronized long timeGen() {
        return System.currentTimeMillis();
    }
}
