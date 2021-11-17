package com.tujia.myssm.web.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: songlinl
 * @create: 2021/09/02 15:27
 */
@Slf4j
public class RequestUtil {

    public static HttpServletRequest httpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String serverDomain() {
        try {
            String urlHost = httpServletRequest().getServerName().toLowerCase();
            log.info("[RequestUtil.serverDomain] Request serverDomain:{}", urlHost);
            return urlHost;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getRequestUri(HttpServletRequest request) {
        String uri = (String) request.getAttribute(WebUtils.INCLUDE_REQUEST_URI_ATTRIBUTE);
        if (uri == null) {
            uri = request.getRequestURI();
        }
        return uri;
    }

    public static String logRequest(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String params = request.getParameterMap().entrySet().stream().map(
                entry -> entry.getKey() + ":" + Arrays.toString(entry.getValue())).collect(Collectors.joining(", "));
        String queryString = request.getQueryString();
        String queryClause = (StringUtils.hasLength(queryString) ? "?" + queryString : "");
        String dispatchType = (!request.getDispatcherType().equals(DispatcherType.REQUEST) ?
                "\"" + request.getDispatcherType().name() + "\" dispatch for " :
                "");
        String message = (dispatchType + request.getMethod() + " \"" + getRequestUri(request) + queryClause +
                "\", parameters={" + params + "}");

        List<String> values = Collections.list(request.getHeaderNames());
        String headers = values.stream().map(name -> name + ":" + Collections.list(request.getHeaders(name))).collect(
                Collectors.joining(", "));
        return message + ", headers={" + headers + "} ";
    }

}
