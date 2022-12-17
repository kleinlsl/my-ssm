package com.tujia.myssm.common.log.appender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.rolling.TjRollingFileAppender;
import com.tujia.myssm.common.ApplicationContextUtil;
import com.tujia.myssm.core.executor.EM;
import com.tujia.myssm.service.CommonService;

/**
 *
 * @author: songlinl
 * @create: 2021/11/22 16:28
 */
public class MyAppender<E> extends TjRollingFileAppender<E> {

    @Override
    public void processMessage(E event) {
        CommonService commonService = ApplicationContextUtil.getBean(CommonService.class);
        commonService.sayHello();
        System.err.println("getLogMsg(event) = " + getLogMsg(event));
        if (event instanceof LoggingEvent) {
            LoggingEvent loggingEvent = (LoggingEvent) event;
            if (loggingEvent.getLevel().isGreaterOrEqual(Level.ERROR)) {
                EM.defaultExecutor.execute(() -> {
                    String msg = getLogMsg(event);
                    System.err.println("msg = " + msg);
                });
            }
        }
    }
}
