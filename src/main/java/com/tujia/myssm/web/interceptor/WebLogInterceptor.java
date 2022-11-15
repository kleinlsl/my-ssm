package com.tujia.myssm.web.interceptor;

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
import com.tujia.myssm.web.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 记录Web请求监控和日志, 无需手动配置，自动配置！！！
 * @author leyuan.lv
 * @create 2022-08-16 19:36
 */
@Slf4j
public class WebLogInterceptor implements HandlerInterceptor {
    public static final String ORIGINAL_REQUEST = "original_request";
    public static final String ORIGINAL_RESPONSE = "original_response";
    public static final String ORIGINAL_REQUEST_EXCEPTION = "original_request_exception";
    public static final String ORIGINAL_WEB_LOG_LEVEL = "original_web_log_level";
    private static final int PRINT_BASE_INFO_CODE = 1;
    public static final String ORIGINAL_REQUEST_START = "original_request_start";
    private static final int PRINT_REQUEST_CODE = 2;
    private static final int PRINT_RESPONSE_CODE = 4;

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

        int logLevel = NumberUtils.toInt(String.valueOf(request.getAttribute(ORIGINAL_WEB_LOG_LEVEL)), -1);
        if (logLevel <= 0) {
            log.info("webRequest_logLevel_ignore, uri:{}", requestURI);
            //            TMonitor.recordOne("WebRequest_logLevel_"+ TmConstant.IGNORE);
            return;
        }
        String monitorKey = "WebRequest" + requestURI.replaceAll("[.|#|:|/|{|}]", "_");

        try {
            long st = System.currentTimeMillis();
            long timeCost = st - NumberUtils.toLong(String.valueOf(request.getAttribute(ORIGINAL_REQUEST_START)), st);
            Exception exc = (Exception) request.getAttribute(ORIGINAL_REQUEST_EXCEPTION);
            boolean success = ex == null;
            monitor(monitorKey, timeCost, success);

            String contentType = request.getContentType();
            String requestStr = (String) request.getAttribute(ORIGINAL_REQUEST);

            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("web request uri:{}, success:{}, timeCost:{}ms, remoteIP:{}, method:{}, contentType:{}, request:{}");
            ArrayList<Object> logParams = Lists.newArrayList(requestURI, success, timeCost, IPUtils.getRemoteIp(request), request.getMethod(),
                                                             contentType, requestStr);

            boolean needLogResp = needLogResp(request);
            if (needLogResp) {
                logBuilder.append(", response:{}");
                String responseStr = JsonUtils.tryToJson(request.getAttribute(ORIGINAL_RESPONSE));
                logParams.add(responseStr);
            }

            if (!success) {
                logParams.add(ex);
                LOG.error(logBuilder.toString(), logParams.toArray());
            } else {
                LOG.info(logBuilder.toString(), logParams.toArray());
            }
        } catch (Exception e) {
            log.error("[{}] handle exp", monitorKey);
            //            TMonitor.recordOne(monitorKey + "_Handle_Exp");
            //            throw e;
        }
    }

    private void monitor(String monitorKey, long timeCost, boolean success) {
        if (!success) {
            //            TMonitor.recordOne(monitorKey + "_Error");
        }
        //        TMonitor.recordOne(monitorKey + "_Execute", timeCost);
    }

    protected boolean needLogResp(HttpServletRequest request) {
        String requestURI = StringUtils.removeEnd(request.getRequestURI(), "/");

        //        CommonConfig config = ApplicationContextUtil.getBean(CommonConfig.class);
        //        return config.needLogRespSwitchOn(requestURI);
        return true;
    }
}