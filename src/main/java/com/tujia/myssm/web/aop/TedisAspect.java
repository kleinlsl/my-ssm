package com.tujia.myssm.web.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import com.google.common.base.Joiner;

/**
 *
 * @author: songlinl
 * @create: 2022/04/15 18:05
 */
@Component
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TedisAspect {
    private static final Logger LOG = LoggerFactory.getLogger(TedisAspect.class);
    private static final Joiner UNDERLINE_JOINER = Joiner.on("_").skipNulls();

    @Around(value = "bean(jedisPool)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String prefix = UNDERLINE_JOINER.join("RedisAspect", joinPoint.getTarget().getClass().getSimpleName(), methodSignature.getMethod().getName());
        long st = System.currentTimeMillis();
        try {
            Object res = joinPoint.proceed();
            long cost = System.currentTimeMillis() - st;
            if (LOG.isDebugEnabled()) {
                LOG.debug("invocation:{} proceed success, cost:{}", prefix, cost);
            }
            return res;
        } catch (Throwable t) {
            LOG.error(prefix + " proceed error!!", t);
            throw t;
        }
    }
}
