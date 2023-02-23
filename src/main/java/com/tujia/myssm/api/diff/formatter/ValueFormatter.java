/*
 * Copyright (c) 2017 tujia.com. All Rights Reserved.
 */
package com.tujia.myssm.api.diff.formatter;

/**
 * @author huachen created on 2/24/14 11:55 AM
 * @version $Id$
 */
public interface ValueFormatter<T> {
    String format(T value);

    final class DEFAULT implements ValueFormatter<Object> {
        @Override
        public String format(Object value) {
            return value.toString();
        }
    }
}
