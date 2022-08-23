package com.tujia.myssm.web.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Lists;
import com.tujia.myssm.web.utils.RequestUtil;

/**
 *
 * @author: songlinl
 * @create: 2022/01/19 11:44
 */
public class WebFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger("web-filter-log");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        recordLog(request);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private void recordLog(ServletRequest request) {
        if (CollectionUtils.isEmpty(Collections.singleton(request.getParameterMap()))) {
            return;
        }

        final List<String> list = Lists.newArrayList();
        list.add("traceId");
        List<String> res = list.stream().filter(l -> request.getParameterMap().containsKey(l)).collect(
                Collectors.toList());
        if (CollectionUtils.isEmpty(res)) {
            return;
        }
        log.info("[WebFilter] log:{}", RequestUtil.logRequest((HttpServletRequest) request));
    }
}
