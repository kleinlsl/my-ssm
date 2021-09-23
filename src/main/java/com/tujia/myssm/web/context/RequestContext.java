package com.tujia.myssm.web.context;

import com.tujia.myssm.web.context.model.LoginInfo;

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

    private LoginInfo loginInfo;

    public static RequestContext getThreadLocal() {
        return (RequestContext) threadLocal.get();
    }

    public static void setThreadLocal(RequestContext context) {
        threadLocal.set(context);
    }

    public static void removeThreadLocal() {
        threadLocal.remove();
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
}
