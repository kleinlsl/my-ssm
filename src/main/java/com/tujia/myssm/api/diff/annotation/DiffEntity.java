package com.tujia.myssm.api.diff.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *     用于标识集合对象中的对象ID.解决集合对象中的对象只能被标记为添加和删除的 bad case
 * </pre>
 * @author jianhong.li Date: 2019-10-17 Time: 3:18 PM
 * @version $Id$
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DiffEntity {

}
