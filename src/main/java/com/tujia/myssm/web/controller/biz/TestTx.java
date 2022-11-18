package com.tujia.myssm.web.controller.biz;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.tujia.myssm.api.model.OpLog;
import com.tujia.myssm.common.utils.JsonUtils;
import com.tujia.myssm.dao.master.OpLogMapper;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2022/11/16 17:42
 */
@Slf4j
@Component
public class TestTx {

    @Resource
    private OpLogMapper opLogMapper;

    @Transactional(rollbackFor = Exception.class)
    public void batchUpdate(List<OpLog> opLogs) {
        opLogs.forEach(o -> {
            retryAddUpdate(o);
            log.info("{}", JsonUtils.tryToJson(o));
        });
    }

    public void retryAddUpdate(OpLog opLog) {
        int times = 3;
        Throwable lastErr = null;
        while (times-- > 0) {

            try {
                if (times == 2 && opLog.getOperator().equals("Deadlock1")) {
                    throw new DeadlockLoserDataAccessException("死锁", new RuntimeException("死锁"));
                } else {
                    opLogMapper.insert(opLog);
                    return;
                }
            } catch (Exception e) {
                lastErr = e;
                if (e instanceof DeadlockLoserDataAccessException) {
                    log.warn("failed on time {}, retry", times, e);
                } else {
                    log.warn("un-catch err, stop run", e);
                    RuntimeException.class.isAssignableFrom(e.getClass());
                    throw (RuntimeException) e;
                }
            }

        }

        if (lastErr != null) {
            log.error("failed on all tries" + times, lastErr);
            throw new RuntimeException("process failed after all retries", lastErr);
        } else {
            log.error("failed on all tries" + times);
            throw new RuntimeException("process failed after all retries");
        }

    }
}
