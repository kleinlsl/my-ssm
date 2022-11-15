package com.tujia.myssm.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用户身份认证
 * @author: songlinl
 * @create: 2021/09/22 18:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface UserIdentify {

    /**
     * 是否必须登录标记：true必须，false非必须
     * @return
     */
    boolean mustLogin() default false;
}
