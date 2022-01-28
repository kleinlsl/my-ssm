package com.tujia.myssm.web.controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tujia.framework.api.APIResponse;
import com.tujia.myssm.web.lock.LockSupport;
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

    private String buildKey() {
        return "Lock_retryCount_" + new Random().nextInt(10);
    }

}
