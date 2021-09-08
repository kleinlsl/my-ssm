package com.tujia.myssm.base;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.tujia.myssm.base.exception.BizException;
import com.tujia.myssm.base.monitor.MRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author bowen.ma created on 16/9/21.
 * @version $Id$
 */

public abstract class BizTemplate<T> {
    // TODO: 2021/9/8 高并发下会有冲突吗？
    private static final Map<String, BizTemplate> factoryMap = Maps.newHashMap();

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected MRegistry mRegistry;

    public BizTemplate(MRegistry mRegistry) {
        this.mRegistry = mRegistry;
    }

    protected abstract void checkParams() throws BizException;

    protected abstract T process() throws Exception;

    protected void afterProcess() {
    }

    protected void onSuccess() {
    }

    protected void onError(Throwable e) {
    }

    public T execute() throws BizException {
        long start = System.currentTimeMillis();
        try {
            checkParams();
            return process();
        } catch (BizException | IllegalArgumentException e) {
            onError(e);
            logger.error("biz error while execute, key = {} ,desc={}", mRegistry.key(), mRegistry.desc());
            throw e;
        } catch (Exception e) {
            onError(e);
            logger.error("error while execute, key = {} ,desc={}", mRegistry.key(), mRegistry.desc(), e);
            throw new BizException(e.getMessage(), e);
        } finally {
            if (!Strings.isNullOrEmpty(mRegistry.key())) {
            }
        }
    }

    /**
     * 将匿名对象放入本地缓存中
     *
     * @return
     */
    public BizTemplate<T> intern() {
        BizTemplate bizTemplate = this;
        if (factoryMap.containsKey(mRegistry.key())) {
            bizTemplate = factoryMap.get(mRegistry.key());
        } else {
            factoryMap.put(mRegistry.key(), this);
        }
        logger.info("[BizTemplate.intern] 处理了，{},{}", this, System.identityHashCode(this));
        return bizTemplate;
    }
}