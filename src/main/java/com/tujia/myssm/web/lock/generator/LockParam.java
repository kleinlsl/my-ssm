package com.tujia.myssm.web.lock.generator;

import java.io.Serializable;

/**
 *
 * @author: songlinl
 * @create: 2022/01/28 17:27
 */
public class LockParam implements Serializable {

    private static final long serialVersionUID = 358757423888135213L;

    /**
     * key
     */
    private String key;

    /**
     * 过期时间
     */
    private long expiredMillis = 1000 * 2;

    /**
     * 重试次数
     */
    private int retryCount = 3;

    /**
     * 每次等待时间：10ms
     */
    private long timeOut = 10;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getExpiredMillis() {
        return expiredMillis;
    }

    public void setExpiredMillis(long expiredMillis) {
        this.expiredMillis = expiredMillis;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }
}
