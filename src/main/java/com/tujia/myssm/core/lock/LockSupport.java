package com.tujia.myssm.core.lock;

import java.util.concurrent.TimeUnit;
import com.tujia.myssm.common.ApplicationContextUtil;
import com.tujia.myssm.service.RedisUtilService;
import com.tujia.myssm.utils.base.Joiners;
import com.tujia.myssm.web.lock.generator.LockParam;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2022/01/26 17:23
 */
@Slf4j
public class LockSupport {

    private static RedisUtilService redisUtilService = ApplicationContextUtil.getBean(RedisUtilService.class);

    public static final String LOCK_PREFIX = "MUTEX_V1_";
    public static final String LOCK_SUFFIX = "_LOCK";
    private final static long DEF_LOCK_EXPIRED_MS = 2 * 1000;

    @SameThread("releaseLock")
    public static boolean grabLock(LockParam key) {
        return grabLock(LockSupport.buildKey(key.getKey()), key.getExpiredMillis(), key.getRetryCount(),
                        key.getTimeOut());
    }

    @SameThread("releaseLock")
    public static boolean grabLock(String lockKey) {
        return grabLock(lockKey, defLockExpiredMillis());
    }

    @SameThread("releaseLock")
    public static boolean grabLock(String lockKey, long expireMillis) {
        try {
            String ret = redisUtilService.set(lockKey, threadId(), "NX", "PX", expireMillis);
            return "OK".equalsIgnoreCase(ret);
        } catch (Exception e) {
            log.error("grab lock error, lockKey:{}", lockKey, e);
            return false;
        }
    }

    @SameThread("releaseLock")
    public static boolean grabLock(String lockKey, long expireMillis, int retryCount) {
        return grabLock(lockKey, expireMillis, retryCount, 10);
    }

    @SameThread("releaseLock")
    public static boolean grabLock(String lockKey, long expireMillis, int retryCount, long timeOut) {
        boolean grabLock = grabLock(lockKey, expireMillis);
        while (!grabLock && retryCount-- > 0) {
            try {
                log.info("lock:{}, retryCount:{}", lockKey, retryCount);
                TimeUnit.MILLISECONDS.sleep(timeOut);
            } catch (InterruptedException e) {
                log.error("gra lock interrupted", e);
            }
            grabLock = grabLock(lockKey, expireMillis);
        }
        return grabLock;
    }

    @SameThread("grabLock")
    public static boolean releaseLock(String lockKey) {
        String script = "local token = redis.call('get',KEYS[1]) if token == ARGV[1] then return redis.call('del',KEYS[1]) elseif token == false then return 1 else return 0 end";
        script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        try {
            Long ret = (Long) redisUtilService.eval(script, 1, lockKey, threadId());
            return ret != null && ret > 0;
        } catch (Exception e) {
            log.error("release lock error, lockKey:{}", lockKey, e);
            return false;
        }
    }

    private static String threadId() {
        return String.valueOf(Thread.currentThread().getId());
    }

    public static long defLockExpiredMillis() {
        return DEF_LOCK_EXPIRED_MS;
    }

    public static String buildKey(Object keyObj) {
        return Joiners.UNDERLINE_JOINER.join(LOCK_PREFIX, keyObj.toString(), LOCK_SUFFIX);
    }
}
