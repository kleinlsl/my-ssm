package com.tujia.myssm.service.impl;

import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.tujia.myssm.service.LocalCache;

/**
 *
 * @author: songlinl
 * @create: 2021/09/14 15:45
 */
@Service
public class LocalCacheImpl implements LocalCache {

    private LoadingCache<Integer, String> valueOfCache = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES).maximumSize(1000).build(new CacheLoader<Integer, String>() {
                @Override
                public String load(Integer key) throws Exception {
                    return String.valueOf(key);

                }
            });
}
