package com.tujia.myssm.core.cache.manage;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.tujia.myssm.core.cache.RedisCacheBuilder;

/**
 *
 * @author: songlinl
 * @create: 2021/09/15 22:14
 */
@Service
public class CacheManage {

    public LoadingCache<Integer, String> valueOfCache = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).maximumSize(1000).build(
            new CacheLoader<Integer, String>() {
                @Override
                public String load(Integer key) throws Exception {
                    return String.valueOf(key);

                }
            });
    public LoadingCache<Integer, String> redisCache = RedisCacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).maximumSize(1000).build(
            new CacheLoader<Integer, String>() {
                @Override
                public String load(Integer key) throws Exception {
                    return String.valueOf(key);
                }
            });

    public static void main(String[] args) throws ExecutionException {
        CacheManage localCache = new CacheManage();
        String res = localCache.valueOfCache.get(1);
        System.out.println(res);
    }
}
