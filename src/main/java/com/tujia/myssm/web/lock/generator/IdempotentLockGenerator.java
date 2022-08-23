package com.tujia.myssm.web.lock.generator;

import java.lang.reflect.Method;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

/**
 *
 * @author: songlinl
 * @create: 2022/01/28 17:00
 */
@Component("lockKeyGenerator")
public class IdempotentLockGenerator implements KeyGenerator {

    @Override
    public LockParam generate(Object target, Method method, Object... params) {
        return buildLock(target, method, params);
    }

    private LockParam buildLock(Object target, Method method, Object[] params) {
        LockParam lockParam = new LockParam();
        lockParam.setKey(buildKey(target, method, params));
        lockParam.setExpiredMillis(buildExpiredMillis(target, method, params));
        return lockParam;
    }

    private String buildKey(Object target, Method method, Object[] params) {
        String key = method.getName();
        if (params.length == 0) {
            return key;
        }
        switch (method.getName()) {
            case "idempotent":
                key = method.getName() + "_" + params[0];
                break;
            case "lockKey":
                key = ((LockParam) params[0]).getKey();
                break;
            default:
                key = method.getName();
        }
        return key;
    }

    public long buildExpiredMillis(Object target, Method method, Object... params) {
        return 1000 * 3;
    }

    public enum MethodKey {
        idempotent("idempotent", (target, method, params) -> {return new LockParam();}),
        lockKey("lockKey", (target, method, params) -> new LockParam());
        private String key;

        private KeyGenerator generator;

        MethodKey(String key, KeyGenerator generator) {
            this.key = key;
        }

        public KeyGenerator getGenerator() {
            return generator;
        }

        public void setGenerator(KeyGenerator generator) {
            this.generator = generator;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
