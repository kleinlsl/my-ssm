package com.tujia.myssm.web.lock;

import javax.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import com.tujia.myssm.service.RedisUtilService;

/**
 * 幂等, 并发控制
 *
 * @author leyuan.lv
 * @create 2021-02-26 11:01
 */
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Aspect
public class ConcurrentIdempotentHandler {
    private static final Logger log = LoggerFactory.getLogger(ConcurrentIdempotentHandler.class);
    private static final SimpleKeyGenerator SIMPLE_KEY_GENERATOR = new SimpleKeyGenerator();
    public static final int DEF_MAX_TRY_TIMES = 10;
    public static final int DEF_IDEMPOTENT_VAL_EXPIRED_SECONDS = 2 * 60; // 2 minute
    public static final long DEF_MAX_DURATION_MILLIS = 60 * 1000L; // 1 minute

    @Resource
    private BeanFactory beanFactory;
    @Resource
    private RedisUtilService redisUtilService;

    /**
     * @param pjp
     * @param idempotent
     */
    @Around("@annotation(idempotent)")
    public Object handle(ProceedingJoinPoint pjp, Idempotent idempotent) {
        Object result = null;
        long startTime = System.currentTimeMillis();
        try {
            result = handleInternal(pjp, idempotent);
            log.info("idempotent_handle,cost:{}", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            log.error(buildInvocationName(pjp) + " idempotent handle error", e);
        }
        return result;
    }

    private Object handleInternal(ProceedingJoinPoint pjp, Idempotent idempotent) {
        //        final String keyGenerator = idempotent.keyGenerator();
        //        final long duration = Math.min(idempotent.duration(), getMaxDurationMillis());
        //        int retry = Math.min(idempotent.retry(), getMaxRetry());
        //        Preconditions.checkState(duration > 0L && retry >= 0);
        //        KeyGenerator generator = SIMPLE_KEY_GENERATOR;
        //        if (StringUtils.isNotEmpty(keyGenerator)) {
        //            generator = beanFactory.getBean(keyGenerator, KeyGenerator.class);
        //        }
        //        Preconditions.checkNotNull(generator);
        //        String key = buildKey(pjp, generator);
        //
        //        // 先查一次, 如果幂等, 返回幂等结果
        //        StampedReference sr = getStampedReference(redisUtilService, key);
        //
        //        if (isIdempotent(sr, duration)) {
        //            log.info("idempotent request!");
        //            return Optional.ofNullable(sr).map(StampedReference::getObj).orElse(null);
        //        }
        //
        //        // 不在幂等周期内, 限制并发
        //        String lockKey = LockSupport.buildKey(key);
        //        // 默认走一次
        //        while (retry-- >= 0) {
        //            // 限制并发
        //            if (grabLock(redisProvider, lockKey)) {
        //                // double check
        //                long startTime = System.currentTimeMillis();
        //                try {
        //                    sr = getStampedReference(redisProvider, key);
        //                    if (!isIdempotent(sr, duration)) {
        //                        try {
        //                            Serializable obj = (Serializable) pjp.proceed();
        //                            redisProvider.psetex(SafeEncoder.encode(key), getRedisValExpiredSeconds(), new StampedReference(obj));
        //                            log.info("not_idempotent_request");
        //                            return obj;
        //                        } catch (Throwable t) {
        //                            log.error(getClass().getSimpleName() + " proceed error", t);
        //                            throw new IllegalStateException(t);
        //                        }
        //                    } else {
        //                        log.info("concurrent idempotent request!");
        //                        return Optional.ofNullable(sr).map(StampedReference::getObj).orElse(null);
        //                    }
        //                } finally {
        //                    if (System.currentTimeMillis() - startTime >= getLockExpiredMillis()) {
        //                        log.warn("NOTICE!!! invocation: {} process time more than lock expired time!!", buildInvocationName(pjp));
        //                    }
        //                    releaseLock(redisProvider, lockKey);
        //                }
        //            }
        //            try {
        //                TimeUnit.MILLISECONDS.sleep(10);
        //            } catch (InterruptedException e) {
        //                log.error("grabLock interrupted", e);
        //            }
        //        }
        //        // 重试结束
        //        log.info("request_can_not_proceed");
        //        throw new IllegalStateException(String.format("%s can not proceed, retry %s times", getClass().getSimpleName(), retry));
        return null;
    }

    private int getMaxRetry() {
        return DEF_MAX_TRY_TIMES;
    }

    private long getMaxDurationMillis() {
        return DEF_MAX_DURATION_MILLIS;
    }

    private int getRedisValExpiredSeconds() {
        return DEF_IDEMPOTENT_VAL_EXPIRED_SECONDS;
    }

    private String buildInvocationName(ProceedingJoinPoint pjp) {
        return pjp.getTarget().getClass().getSimpleName() + "_" + pjp.getSignature().getName();
    }

    //    /**
    //     * 受本地时钟限制
    //     *
    //     * @param sr
    //     * @param duration
    //     * @return
    //     */
    //    private boolean isIdempotent(StampedReference sr, long duration) {
    //        if (sr != null && System.currentTimeMillis() - sr.getStamp() <= duration) {
    //            return true;
    //        }
    //        return false;
    //    }
    //
    //    private StampedReference getStampedReference(RedisUtilService redisProvider, String key) {
    //        StampedReference sr = null;
    //        try {
    //            sr = redisProvider.pget(SafeEncoder.encode(key));
    //            return sr;
    //        } catch (Exception e) {
    //            log.error("get idempotent result from redis error, key:" + key, e);
    //        }
    //        return sr;
    //    }
    //
    //    private String buildKey(ProceedingJoinPoint pjp, KeyGenerator generator) {
    //        Object target = pjp.getTarget();
    //        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
    //        Object keyObj = generator.generate(target, method, pjp.getArgs());
    //        Preconditions.checkNotNull(keyObj);
    //        int key = keyObj.hashCode();
    //        key = 31 * key + target.getClass().getName().hashCode();
    //        key = 31 * key + method.hashCode();
    //        return target.getClass().getSimpleName() + method.getName() + key;
    //    }
}
