package com.tujia.myssm.web.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户身份判定切面
 * @author: songlinl
 * @create: 2021/09/23 10:18
 */
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Component
@Slf4j
public class UserIdentifyAspect {

    @Pointcut("@annotation(com.tujia.myssm.web.aop.UserIdentify)")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        log.error("[UserIdentifyAspect.doBefore] ");
    }

    @After("controllerAspect()")
    public void doAfter(JoinPoint joinPoint) {
        log.error("[UserIdentifyAspect.doAfter] ");
    }

    @AfterReturning("controllerAspect()")
    public void doAfterReturning(JoinPoint joinPoint) {
        log.error("[UserIdentifyAspect.doAfterReturning] ");
    }

    @AfterThrowing("controllerAspect()")
    public void doAfterThrowing(JoinPoint joinPoint) {
        log.error("[UserIdentifyAspect.doAfterThrowing] ");
    }

    @Around("controllerAspect()")
    public void doAround(ProceedingJoinPoint jp) throws Throwable {
        log.error("[UserIdentifyAspect.doAround] before");
        Object o = jp.proceed();
        log.error("[UserIdentifyAspect.doAround] after");
    }

}
