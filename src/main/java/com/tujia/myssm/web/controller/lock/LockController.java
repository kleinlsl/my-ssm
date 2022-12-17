package com.tujia.myssm.web.controller.lock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tujia.framework.api.APIResponse;
import com.tujia.myssm.core.lock.LockSupport;
import com.tujia.myssm.utils.base.JsonUtils;
import com.tujia.myssm.web.controller.BaseController;
import com.tujia.myssm.web.lock.Idempotent;
import com.tujia.myssm.web.lock.generator.LockParam;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2022/01/26 17:52
 */
@RestController
@RequestMapping("/test/lock/")
@Slf4j
public class LockController extends BaseController {

    @GetMapping("/timeOut/lock")
    public APIResponse<Boolean> timeOutLock(@RequestParam(name = "retryCount", defaultValue = "10") int retryCount,
                                            @RequestParam(name = "expireMillis", defaultValue = "1000") int expireMillis,
                                            @RequestParam(name = "timeOut", defaultValue = "10") long timeOut) {
        String key = buildKey();
        try {
            if (!LockSupport.grabLock(key, expireMillis, retryCount, timeOut)) {
                log.error("get lock is fail, key:{}", key);
                return APIResponse.returnFail("", Boolean.FALSE);
            }
            TimeUnit.MILLISECONDS.sleep(expireMillis / 3);
            return APIResponse.returnSuccess(Boolean.TRUE);
        } catch (Exception e) {
            log.error("Exception:");
        } finally {
            log.info("finally releaseLock,key:{}", key);
            LockSupport.releaseLock(key);
        }
        return APIResponse.returnFail("", Boolean.FALSE);
    }

    @GetMapping("/idempotent/lock")
    @Idempotent(duration = 3000, keyGenerator = "lockKeyGenerator")
    public APIResponse<Boolean> idempotent(@RequestParam(name = "key", defaultValue = "lock_key") String key) {
        try {
            TimeUnit.MILLISECONDS.sleep(1000L);
            return APIResponse.returnSuccess();
        } catch (InterruptedException ignored) {
        }
        return APIResponse.returnFail("", Boolean.FALSE);
    }

    @PostMapping("/lockKey/lock")
    @Idempotent(duration = 3000, keyGenerator = "lockKeyGenerator")
    public APIResponse<Boolean> lockKey(@RequestBody LockParam lockParam) {
        try {
            log.info("req:{}", JsonUtils.tryToJson(lockParam));
            TimeUnit.MILLISECONDS.sleep(1000L);
            return APIResponse.returnSuccess();
        } catch (InterruptedException ignored) {
        }
        return APIResponse.returnFail("", Boolean.FALSE);
    }

    private String buildKey() {
        return "Lock_retryCount_" + new Random().nextInt(10);
    }

}
