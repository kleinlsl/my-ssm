package com.tujia.myssm.web.lock;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.cache.interceptor.KeyGenerator;

/**
 *
 * @author: songlinl
 * @create: 2022/01/28 16:07
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Idempotent {
    /**
     * The bean name of the custom {@link KeyGenerator}
     * to use
     * @return
     */
    String keyGenerator() default "";

    /**
     * keep idempotent time
     * unit：ms
     * @return
     */
    long duration() default 1000L;

    /**
     * retry times to grab lock
     * @return
     */
    int retry() default 3;

}
