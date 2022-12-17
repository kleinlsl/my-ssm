package com.tujia.myssm.core.cache;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import org.slf4j.LoggerFactory;
import com.google.common.cache.AbstractLoadingCache;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Bytes;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.tujia.myssm.core.Tedis;

/**
 *
 * @author: songlinl
 * @create: 2022/12/17 20:28
 */
public class RedisLoadingCache<K, V> extends AbstractLoadingCache<K, V> implements LoadingCache<K, V> {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(RedisLoadingCache.class);

    private final Tedis tedis;

    private final Serializer<K> keySerializer;

    private final Serializer<V> valueSerializer;

    private final byte[] keyPrefix;

    private final int expirationInSec;

    private final CacheLoader<? super K, V> loader;

    public RedisLoadingCache(Tedis tedis, Serializer<K> keySerializer, Serializer<V> valueSerializer, byte[] keyPrefix, int expirationInSec) {
        this(tedis, keySerializer, valueSerializer, keyPrefix, expirationInSec, null);
    }

    public RedisLoadingCache(Tedis tedis, RedisCacheBuilder<? super K, ? super V> builder, CacheLoader<? super K, V> loader) {
        this(tedis, (Serializer<K>) builder.getKeySerializer(), (Serializer<V>) builder.getValueSerializer(),
             builder.getPrefix().getBytes(StandardCharsets.UTF_8), builder.getExpireAfterWriteInSec(), loader);
    }

    public RedisLoadingCache(Tedis tedis, Serializer<K> keySerializer, Serializer<V> valueSerializer, byte[] keyPrefix, int expirationInSec,
                             CacheLoader<? super K, V> loader) {
        this.tedis = tedis;
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
        this.keyPrefix = keyPrefix;
        this.expirationInSec = expirationInSec;
        this.loader = loader;
    }

    private static void convertAndThrow(Throwable t) throws ExecutionException {
        logger.error("raw redis err", t);
        if (t instanceof InterruptedException) {
            Thread.currentThread().interrupt();
            throw new ExecutionException(t);
        } else if (t instanceof RuntimeException) {
            throw new UncheckedExecutionException(t);
        } else if (t instanceof Exception) {
            throw new ExecutionException(t);
        } else {
            throw new ExecutionError((Error) t);
        }
    }

    @Override
    public V getIfPresent(Object o) {
        byte[] reply = tedis.get(Bytes.concat(keyPrefix, keySerializer.serialize((K) o)));
        if (reply == null) {
            return null;
        } else {
            return (V) valueSerializer.deserialize(reply);
        }
    }

    @Override
    public V get(K key, Callable<? extends V> valueLoader) throws ExecutionException {
        try {
            V value = this.getIfPresent(key);
            if (value == null) {
                logger.warn("redis reloading for {} ", new String(this.keyPrefix, StandardCharsets.UTF_8) + key);
                value = valueLoader.call();
                if (value == null) {
                    throw new CacheLoader.InvalidCacheLoadException("valueLoader must not return null, key=" + key);
                } else {
                    this.put(key, value);
                }
            }
            return value;
        } catch (Throwable e) {
            convertAndThrow(e);
            // never execute
            return null;
        }
    }

    @Override
    public ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
        List<byte[]> keyBytes = new ArrayList<>();
        for (Object key : keys) {
            keyBytes.add(Bytes.concat(keyPrefix, keySerializer.serialize((K) key)));
        }

        List<byte[]> rawList = tedis.mget(Iterables.toArray(keyBytes, byte[].class));

        Map<K, V> map = new LinkedHashMap<>();

        for (int i = 0; i < keyBytes.size(); i++) {
            if ("nil".equals(new String(rawList.get(i)))) {
                map.put((K) keySerializer.deserialize(keyBytes.get(i)), null);
            }
            map.put((K) keySerializer.deserialize(keyBytes.get(i)), (V) valueSerializer.deserialize(rawList.get(i)));
        }
        return ImmutableMap.copyOf(map);
    }

    @Override
    public void put(K key, V value) {
        byte[] keyBytes = Bytes.concat(keyPrefix, keySerializer.serialize(key));
        byte[] valueBytes = valueSerializer.serialize(value);

        if (expirationInSec > 0) {
            tedis.setex(keyBytes, expirationInSec, valueBytes);
        } else {
            tedis.set(keyBytes, valueBytes);
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        List<byte[]> keysvalues = new ArrayList<>();
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            keysvalues.add(Bytes.concat(keyPrefix, keySerializer.serialize(entry.getKey())));
            keysvalues.add(valueSerializer.serialize(entry.getValue()));
        }

        if (expirationInSec > 0) {
            tedis.mset(Iterables.toArray(keysvalues, byte[].class));
            for (int i = 0; i < keysvalues.size(); i += 2) {
                tedis.expire(keysvalues.get(i), expirationInSec);
            }
        } else {
            tedis.mset(Iterables.toArray(keysvalues, byte[].class));
        }
    }

    @Override
    public void invalidate(Object key) {
        tedis.del(Bytes.concat(keyPrefix, keySerializer.serialize((K) key)));
    }

    @Override
    public void invalidateAll(Iterable<?> keys) {
        Set<byte[]> keyBytes = new LinkedHashSet<>();
        for (Object key : keys) {
            keyBytes.add(Bytes.concat(keyPrefix, keySerializer.serialize((K) key)));
        }
        keyBytes.forEach(tedis::del);
    }

    @Override
    public V get(final K key) throws ExecutionException {
        return this.get(key, () -> loader.load(key));
    }

    @Override
    public ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {

        Map<K, V> result = Maps.newLinkedHashMap(this.getAllPresent(keys));

        Set<K> keysToLoad = Sets.newLinkedHashSet(keys);
        keysToLoad.removeAll(result.keySet());
        if (!keysToLoad.isEmpty()) {
            try {
                Map<? super K, V> newEntries = loader.loadAll(keysToLoad);

                if (newEntries == null) {
                    throw new CacheLoader.InvalidCacheLoadException(loader + " returned null map from loadAll");
                }
                for (Map.Entry<? super K, V> vEntry : newEntries.entrySet()) {
                    this.put((K) vEntry.getKey(), vEntry.getValue());
                }

                for (K key : keysToLoad) {
                    V value = newEntries.get(key);
                    if (value == null) {
                        throw new CacheLoader.InvalidCacheLoadException("loadAll failed to return a value for " + key);
                    }
                    result.put(key, value);
                }
            } catch (Throwable e) {
                convertAndThrow(e);
            }
        }

        return ImmutableMap.copyOf(result);
    }

    @Override
    public void refresh(K key) {
        try {
            V value = loader.load(key);
            this.put(key, value);
        } catch (Exception e) {
            logger.warn("Exception thrown during refresh", e);
        }
    }
}
