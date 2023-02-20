package com.tujia.myssm.common.expression.operation;

/**
 *
 * @author: songlinl
 * @create: 2023/01/16 11:11
 */
public interface Operation<T> {
    boolean validate(String input);

    String toAviator(String input);

    T toObject(String input);

    int compare(T base, T input);

    int compare(T base, String input);
}
