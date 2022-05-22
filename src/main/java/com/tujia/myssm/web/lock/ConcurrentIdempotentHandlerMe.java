package com.tujia.myssm.web.lock;

import java.lang.reflect.Method;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import com.google.common.base.Preconditions;
import com.tujia.myssm.common.utils.Joiners;
import com.tujia.myssm.common.utils.JsonUtils;
import com.tujia.myssm.service.RedisUtilService;
import com.tujia.myssm.web.lock.generator.LockParam;

/**
 * 幂等、并发控制
 * @author: songlinl
 * @create: 2022/01/28 16:07
 */
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Aspect
public class ConcurrentIdempotentHandlerMe {
    private static final Logger log = LoggerFactory.getLogger(ConcurrentIdempotentHandlerMe.class);
    private static final SimpleKeyGenerator SIMPLE_KEY_GENERATOR = new SimpleKeyGenerator();
    private static final long DEF_DURATION = 1000 * 2;
    private static final int DEF_RETRY = 6;

    @Resource
    private BeanFactory beanFactory;
    @Resource
    private RedisUtilService redisUtilService;

    @Around(value = "@annotation(idempotent)")
    public Object handle(ProceedingJoinPoint pjp, Idempotent idempotent) {
        Object result = null;
        long startTime = System.currentTimeMillis();
        try {
            result = handleInternal(pjp, idempotent);
        } catch (Exception e) {
            log.error("idempotent handle error, invocation:{}", buildInvocationName(pjp), e);
        } finally {
            log.info("idempotent handle finally, invocation:{} cost:{}", buildInvocationName(pjp), System.currentTimeMillis() - startTime);
        }
        return result;
    }

    private Object handleInternal(ProceedingJoinPoint pjp, Idempotent idempotent) {
        final String keyGenerator = idempotent.keyGenerator();
        final long duration = Math.min(idempotent.duration(), DEF_DURATION);
        int retry = Math.min(idempotent.retry(), DEF_RETRY);
        Preconditions.checkState(duration > 0L && retry > 0);
        KeyGenerator generator = SIMPLE_KEY_GENERATOR;
        if (StringUtils.isNotBlank(keyGenerator)) {
            generator = beanFactory.getBean(keyGenerator, KeyGenerator.class);
        }
        Preconditions.checkNotNull(generator);
        LockParam key = buildKey(pjp, generator);
        // TODO: 2022/1/28 幂等操作
        // 限制并发
        if (LockSupport.grabLock(key)) {
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                log.error("handleInternal error");
            } finally {
                log.info("handleInternal releaseLock key:{}", JsonUtils.tryToJson(key));
                LockSupport.releaseLock(key.getKey());
            }
        }
        throw new IllegalStateException(String.format("%s can not proceed, retry %s times", getClass().getSimpleName(), retry));
    }

    private LockParam buildKey(ProceedingJoinPoint pjp, KeyGenerator generator) {
        LockParam lockParam = new LockParam();
        Object target = pjp.getTarget();
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Object keyObj = generator.generate(target, method, pjp.getArgs());
        Preconditions.checkNotNull(keyObj);
        if (keyObj instanceof LockParam) {
            return (LockParam) keyObj;
        }
        int key = keyObj.hashCode();
        key = 31 * key + target.getClass().getName().hashCode();
        key = 31 * key + method.hashCode();
        lockParam.setKey(target.getClass().getSimpleName() + method.getName() + key);
        return lockParam;
    }

    private String buildInvocationName(ProceedingJoinPoint pjp) {
        return Joiners.UNDERLINE_JOINER.join(pjp.getTarget().getClass().getSimpleName(), pjp.getSignature().getName());
    }

}
