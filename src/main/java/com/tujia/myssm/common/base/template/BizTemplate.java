package com.tujia.myssm.common.base.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Strings;
import com.tujia.myssm.common.base.exception.BizException;
import com.tujia.myssm.common.base.monitor.MRegistry;

/**
 *
 */

public abstract class BizTemplate<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected MRegistry mRegistry;

    public BizTemplate() {

    }

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
            logger.error("biz execute, key = {} ,desc={}", mRegistry.key(), mRegistry.desc());
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

}