package com.tujia.myssm.web.aop;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import com.tujia.myssm.web.context.RequestContext;
import com.tujia.myssm.web.context.model.LoginInfo;
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
public class UserIdentifyAspect extends BaseAspect {

    @Pointcut("@annotation(com.tujia.myssm.web.aop.UserIdentify)")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        log.error("[UserIdentifyAspect.doBefore] ");
        try {
            httpRequestLog(joinPoint);
            log.info("UserIdentifyAspect.doBefore:{}.{}", getClassName(joinPoint), getMethodName(joinPoint));
            RequestContext context = RequestContext.getThreadLocal();
            UserIdentify userIdentify = getAnnotation(joinPoint);
            if (userIdentify == null) {
                return;
            }
            if (context == null) {
                context = new RequestContext();
                LoginInfo loginInfo = new LoginInfo();
                loginInfo.setCreateTime(LocalDateTime.now());
                context.setLoginInfo(loginInfo);
                RequestContext.setThreadLocal(context);
            }
            if (userIdentify.mustLogin()) {
                if (context.getLoginInfo() == null) {
                    log.warn("UserIdentifyAspect.doBefore 用户未登录");
                    throw new RuntimeException("");
                }
            }

        } catch (Exception e) {
            log.error("UserIdentifyAspect.doBefore:{}.{} 发生异常", getClassName(joinPoint), getMethodName(joinPoint), e);
            RequestContext.removeThreadLocal();
            throw new RuntimeException("未登录");
        }
    }

    private UserIdentify getAnnotation(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(UserIdentify.class);
        }
        return null;
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
