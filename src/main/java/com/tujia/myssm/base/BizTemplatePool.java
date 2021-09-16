package com.tujia.myssm.base;

import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;
import com.google.common.collect.Maps;
import com.tujia.myssm.base.monitor.MRegistry;

/**
 *
 * @author: songlinl
 * @create: 2021/09/15 22:43
 */
public class BizTemplatePool {
    public static final ConcurrentMap<MRegistry, BizTemplate> CACHE = Maps.newConcurrentMap();

    public static <T> BizTemplate<T> get(MRegistry key, Supplier<BizTemplate<T>> supplier) {
        if (CACHE.containsKey(key)) {
            return CACHE.get(key);
        }
        BizTemplate<T> bizTemplate = supplier.get();
        bizTemplate.mRegistry = key;
        CACHE.put(key, bizTemplate);
        return bizTemplate;
    }

}
