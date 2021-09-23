package com.tujia.myssm.web.context;

/**
 * 请求上下文
 * @author: songlinl
 * @create: 2021/09/23 10:57
 */
public class RequestContext {
    private static ThreadLocal threadLocal = new ThreadLocal() {
        @Override
        protected RequestContext initialValue() {
            return null;
        }
    };

    public static RequestContext getThreadLocal() {
        return (RequestContext) threadLocal.get();
    }

    public static void setThreadLocal(RequestContext context) {
        threadLocal.set(context);
    }

    public static void removeThreadLocal() {
        threadLocal.remove();
    }
}
