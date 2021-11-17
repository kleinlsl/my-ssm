package com.tujia.myssm.web.aop;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import com.tujia.myssm.web.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2021/11/17 15:34
 */
@Slf4j
public class BaseAspect {

    protected void httpRequestLog(JoinPoint joinPoint) throws IOException {
        HttpServletRequest request = RequestUtil.httpServletRequest();
        log.info("[BaseAspect.doBefore] {}.{} : {}", getClassName(joinPoint), getMethodName(joinPoint),
                 RequestUtil.logRequest(request));
    }

    /**
     * 获取切入点方法名
     *
     * @param joinPoint
     * @return
     */
    protected String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

    /**
     * 获取切入点类名
     *
     * @param joinpoint
     * @return
     */
    protected String getClassName(JoinPoint joinpoint) {
        return joinpoint.getTarget().getClass().getSimpleName();
    }

}
