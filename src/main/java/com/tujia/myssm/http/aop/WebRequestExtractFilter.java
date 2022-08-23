package com.tujia.myssm.http.aop;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * GET/POST 请求提取, 数据流可多次读取, 无需手动配置，自动配置！！！
 *  参考 org.springframework.web.filter.AbstractRequestLoggingFilter
 * @author leyuan.lv
 * @create 2022-08-15 16:43
 */
@WebFilter(filterName = "webRequestExtractFilter", urlPatterns = { "/*" }, asyncSupported = true)
public final class WebRequestExtractFilter extends OncePerRequestFilter {
    public static final String ORIGINAL_REQUEST = "original_request";

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        BufferingHttpServletRequestWrapper bufferingRequestWrapper = new BufferingHttpServletRequestWrapper(request);
        request.setAttribute(ORIGINAL_REQUEST, bufferingRequestWrapper.getMemoizedRequest().get());

        filterChain.doFilter(bufferingRequestWrapper, response);

    }
}