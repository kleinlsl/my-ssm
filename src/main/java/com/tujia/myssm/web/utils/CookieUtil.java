package com.tujia.myssm.web.utils;

import java.util.Arrays;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: songlinl
 * @create: 2021/09/02 15:27
 */
@Slf4j
public class CookieUtil {

    private static HttpServletRequest httpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 取Cookie
     *
     * @param name
     * @return
     */
    public static Optional<Cookie> get(String name) {
        Cookie[] cookies = httpServletRequest().getCookies();
        if (cookies == null) {
            return Optional.empty();
        }
        return Arrays.stream(cookies).filter(m -> m.getName().equalsIgnoreCase(name)).findAny();
    }

    /**
     * 取Cookie值
     *
     * @param name
     * @return
     */
    public static String getValue(String name) {
        Optional<Cookie> httpCookie = get(name);
        if (httpCookie.isPresent()) {
            return httpCookie.get().getValue();
        } else {
            return "";
        }
    }
}
