package com.tujia.myssm.core.cache;

import java.util.concurrent.TimeUnit;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.tujia.myssm.core.RedisProvider;

/**
 * @author jief 2018/10/9 19:12
 * @Description
 */
public final class RedisCacheBuilder<K, V> {

    private int expireAfterWriteInSec;
    private Serializer<? extends K> keySerializer;
    private Serializer<? extends V> valueSerializer;
    private String prefix;

    public static RedisCacheBuilder<Object, Object> newBuilder() {
        return new RedisCacheBuilder<>();
    }

    public int getExpireAfterWriteInSec() {
        return expireAfterWriteInSec;
    }

    public void setExpireAfterWriteInSec(int expireAfterWriteInSec) {
        this.expireAfterWriteInSec = expireAfterWriteInSec;
    }

    public Serializer<? extends K> getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(Serializer<K> keySerializer) {
        this.keySerializer = keySerializer;
    }

    public Serializer<? extends V> getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(Serializer<V> valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public RedisCacheBuilder<K, V> withPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public RedisCacheBuilder<K, V> maximumSize(int maxSize) {
        return this;
    }

    public RedisCacheBuilder<K, V> serializeKey(Serializer<? extends K> keySerializer) {
        this.keySerializer = keySerializer;
        return this;
    }

    public RedisCacheBuilder<K, V> serializeValue(Serializer<? extends V> valueSerializer) {
        this.valueSerializer = valueSerializer;
        return this;
    }

    public RedisCacheBuilder<K, V> expireAfterWrite(long duration, TimeUnit unit) {
        this.expireAfterWriteInSec = toSec(duration, unit);
        return this;
    }

    public RedisCacheBuilder<K, V> refreshAfterWrite(long duration, TimeUnit unit) {
        this.expireAfterWriteInSec = toSec(duration, unit);
        return this;
    }

    public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(CacheLoader<? super K1, V1> loader) {
        return new RedisLoadingCache<>(RedisProvider.get(), this, loader);
    }

    private int toSec(long duration, TimeUnit unit) {
        switch (unit) {
            case DAYS:
                return (int) duration * 24 * 3600;
            case HOURS:
                return (int) duration * 3600;
            case MINUTES:
                return (int) duration * 60;
            case SECONDS:
                return (int) duration;
            default:
                return 1;
        }
    }
}
