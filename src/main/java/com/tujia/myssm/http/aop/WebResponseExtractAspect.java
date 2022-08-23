package com.tujia.myssm.http.aop;

import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * web返回值 提取
 * @author leyuan.lv
 * @create 2022-08-16 18:32
 */
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Component
public class WebResponseExtractAspect {

    public static final String ORIGINAL_RESPONSE = "original_response";

    /**
     * 定义切点
     */
    @Pointcut("(@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController))")
    public void pc() {
    }

    @AfterReturning(returning = "ret", pointcut = "pc()")
    public void afterReturn(Object ret) {
        HttpServletRequest request = getCurrentThreadServletRequest();
        request.setAttribute(ORIGINAL_RESPONSE, ret);
    }

    private HttpServletRequest getCurrentThreadServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

}
