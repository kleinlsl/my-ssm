/*
 * Copyright (c) 2017 tujia.com. All Rights Reserved.
 */
package com.tujia.myssm.api.diff.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.tujia.myssm.api.diff.formatter.ValueFormatter;

/**
 * @author huachen created on 2/15/14 6:33 PM
 * @version $Id$
 */
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Diff {
    public String value();

    public Class<? extends ValueFormatter> valueFormatter() default ValueFormatter.DEFAULT.class;
}
