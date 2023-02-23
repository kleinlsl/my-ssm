/*
 * Copyright (c) 2017 tujia.com. All Rights Reserved.
 */
package com.tujia.myssm.api.diff.utils;

import java.lang.reflect.Field;

/**
 * @author huachen created on 2/28/14 10:22 PM
 * @version $Id$
 */
public class ReflectionUtils {
    public static Field findField(Class clazz, String fieldName) {
        for (Field field : clazz.getDeclaredFields()) {
            if (fieldName.equals(field.getName())) {
                return field;
            }
        }
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            return findField(clazz.getSuperclass(), fieldName);
        }
        return null;
    }
}
