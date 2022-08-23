package com.tujia.myssm.http.aop;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.google.common.collect.Lists;
import com.tujia.myssm.common.utils.JsonUtils;
import static com.tujia.myssm.http.aop.GlobalControllerAdvice.ORIGINAL_REQUEST_EXCEPTION;
import com.tujia.myssm.web.utils.IPUtils;

/**
 * 记录Web请求监控和日志, 无需手动配置，自动配置！！！
 * @author leyuan.lv
 * @create 2022-08-16 19:36
 */
public class WebLogInterceptor implements HandlerInterceptor {
    public static final String ORIGINAL_REQUEST_START = "original_request_start";
    private static final Logger LOG = LoggerFactory.getLogger(WebLogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(ORIGINAL_REQUEST_START, System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String requestURI = StringUtils.removeEnd(request.getRequestURI(), "/");
        String monitorKey = "WebRequest" + StringUtils.replace(requestURI, "/", "_");

        try {
            long st = System.currentTimeMillis();
            long timeCost = st - NumberUtils.toLong(String.valueOf(request.getAttribute(ORIGINAL_REQUEST_START)), st);
            ex = (Exception) request.getAttribute(ORIGINAL_REQUEST_EXCEPTION);
            boolean success = ex == null;
            monitor(monitorKey, timeCost, success);

            String contentType = request.getContentType();
            String requestStr = (String) request.getAttribute(WebRequestExtractFilter.ORIGINAL_REQUEST);

            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("web request uri:{}, success:{}, timeCost:{}ms, remoteIP:{}, method:{}, contentType:{}, request:{}");

            ArrayList<Object> logParams = Lists.newArrayList(requestURI, success, timeCost, IPUtils.getRemoteIp(request), request.getMethod(),
                                                             contentType, requestStr);

            boolean needLogResp = needLogResp(request);
            if (needLogResp) {
                logBuilder.append(", response:{}");
                String responseStr = JsonUtils.toJson(request.getAttribute(WebResponseExtractAspect.ORIGINAL_RESPONSE));
                logParams.add(responseStr);
            }

            if (!success) {
                logParams.add(ex);
                LOG.error(logBuilder.toString(), logParams.toArray());
            } else {
                LOG.info(logBuilder.toString(), logParams.toArray());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void monitor(String monitorKey, long timeCost, boolean success) {

    }

    protected boolean needLogResp(HttpServletRequest request) {
        return true;
    }
}