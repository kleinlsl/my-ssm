package com.tujia.myssm.core.lock;

import java.util.UUID;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.google.common.base.Preconditions;
import com.tujia.myssm.core.TMonitor;
import com.tujia.myssm.core.Tedis;

/**
 *
 * @author: songlinl
 * @create: 2022/12/17 19:40
 */
@Component
public class SimpleRedisLock {
    private static Logger logger = LoggerFactory.getLogger(SimpleRedisLock.class);
    @Resource
    private Tedis tedis;
    private static final String setOk = "OK";

    public static class SimpleRedisLocker implements AutoCloseable {
        private String key;
        private String value;
        private Tedis tedis;

        SimpleRedisLocker(String key, String value, Tedis tedis) {
            this.key = key;
            this.value = value;
            this.tedis = tedis;

        }

        @Override
        public void close() throws Exception {
            logger.info("unlocking {}, {}", key, value);
            final Object eval = tedis.eval("if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end", 1,
                                           key, value);
            logger.info("unlocked {}, {}", key, eval);
        }
    }

    public SimpleRedisLocker lock(String key, int seconds) throws LockOccupiedException {
        final String uuid = UUID.randomUUID().toString();
        lock(key, uuid, seconds);
        return new SimpleRedisLocker(key, uuid, tedis);
    }

    public void lock(String key, String uuid, int seconds) throws LockOccupiedException {
        Preconditions.checkArgument(seconds > 0);
        final String set = tedis.set(key, uuid, "NX", "EX", seconds);
        if (!setOk.equalsIgnoreCase(set)) {
            logger.warn("can't lock {}, res {}", key, set);
            TMonitor.recordOne("SimpleRedisLock_lock_failed");
            throw new LockOccupiedException();
        }
        logger.info("locked {} for {}s", key, seconds);
    }

    public void unlock(String key, String uuid) throws Exception {
        try (SimpleRedisLocker simpleRedisLocker = new SimpleRedisLocker(key, uuid, tedis)) {
        }
    }
}
