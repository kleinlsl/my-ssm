package com.tujia.myssm.core.http;

import java.util.Map.Entry;

/**
 * Created by lizuju on 2017/9/21.
 */
public class SimpleEntry<K, V> implements Entry<K, V> {

    private final K key;
    private final V value;

    public SimpleEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(key).append("=").append(value).toString();
    }

    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }

}
