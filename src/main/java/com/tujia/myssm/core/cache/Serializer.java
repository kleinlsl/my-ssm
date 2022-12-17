package com.tujia.myssm.core.cache;

/**
 * @author jief 2018/10/9 18:47
 * @Description
 */
public interface Serializer<T> {

    T deserialize(final byte[] objectData);

    byte[] serialize(final T obj);
}
